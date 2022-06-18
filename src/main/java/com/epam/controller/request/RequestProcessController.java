package com.epam.controller.request;

import com.epam.entity.Request;
import com.epam.ioc.ServiceFactory;
import com.epam.ioc.ServiceFactoryCreator;
import com.epam.ioc.ServiceFactoryException;
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
            boolean success = false;
            String message;
            if (toProcess.isInProcess()) {
                message = bundle.getString("request.table.inprocess.true");
            } else {
                try {
                    Long processedRequestId = factory.getDispatcher().formatWorkPlan(toProcess);
                    success = true;
                    toProcess.setInProcess(true);
                    factory.getRequestService().save(toProcess);
                    message = bundle.getString("request.ready");
                } catch (NotEnoughWorkersException e) {
                    message = bundle.getString("error.brigade.not.enough.workers");
                } catch (ServiceException e) {
                    message = bundle.getString("request.table.inprocess.true");
                }
                if (!success) {
                    LogManager.getLogger().error("request not processed");
                }
            }
            resp.sendRedirect("request-view?message=" + URLDecoder.decode(message, StandardCharsets.UTF_8));
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
