package com.epam.controller.request;

import com.epam.entity.Request;
import com.epam.factory.ServiceFactory;
import com.epam.factory.ServiceFactoryCreator;
import com.epam.factory.ServiceFactoryException;
import com.epam.service.exception.NotEnoughWorkersException;
import com.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/request-process")
public class RequestProcessController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = new Locale(req.getSession().getAttribute("locale").toString());
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        try (ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String requestIdString = req.getParameter("requestId");
            Long requestId = Long.valueOf(requestIdString);
            Request toProcess = factory.getRequestService().readById(requestId);

            try {
                factory.getDispatcher().formatWorkPlan(toProcess);
                factory.getRequestService().save(toProcess);
                LogManager.getLogger().info(bundle.getString("log4j.info.request.process"));
            } catch (NotEnoughWorkersException e) {
                LogManager.getLogger().error(bundle.getString("log4j.error.request.not.enough"));
            } catch (ServiceException e) {
                LogManager.getLogger().error(bundle.getString("log4j.error.request.inprocess"));
            }
            resp.sendRedirect("request-view");
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
