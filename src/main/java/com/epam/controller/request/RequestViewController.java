package com.epam.controller.request;

import com.epam.entity.Request;
import com.epam.factory.ServiceFactory;
import com.epam.factory.ServiceFactoryCreator;
import com.epam.factory.ServiceFactoryException;
import com.epam.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/request-view")
public class RequestViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            List<Request> requests = factory.getRequestService().readAll();

            req.setAttribute("requests",requests);
            req.getRequestDispatcher("WEB-INF/jsp/request/request-view.jsp").forward(req,resp);
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
