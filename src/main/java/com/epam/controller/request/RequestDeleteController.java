package com.epam.controller.request;

import com.epam.factory.ServiceFactory;
import com.epam.factory.ServiceFactoryCreator;
import com.epam.factory.ServiceFactoryException;
import com.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/request-delete")
public class RequestDeleteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = new Locale(req.getSession().getAttribute("locale").toString());
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        try (ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String requestIdString = req.getParameter("requestId");
            Long requestId = Long.valueOf(requestIdString);
            factory.getRequestService().delete(requestId);
            LogManager.getLogger().info(bundle.getString("log4j.info.request.delete"));
            resp.sendRedirect("request-view");
        } catch (ServiceFactoryException | ServiceException e) {
            LogManager.getLogger().error(bundle.getString("log4j.error.request.delete"));
        }
    }
}
