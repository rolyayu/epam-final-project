package com.epam.controller.request;

import com.epam.ioc.ServiceFactory;
import com.epam.ioc.ServiceFactoryCreator;
import com.epam.ioc.ServiceFactoryException;
import com.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/request-delete")
public class RequestDeleteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String requestIdString = req.getParameter("requestId");
            Long requestId = Long.valueOf(requestIdString);
            factory.getRequestService().delete(requestId);
            resp.sendRedirect("request-view");
        } catch (ServiceFactoryException | ServiceException e) {
            LogManager.getLogger().error("cant delete request");
        }
    }
}
