package com.epam.controller.lodger;

import com.epam.entity.Lodger;
import com.epam.ioc.ServiceFactory;
import com.epam.ioc.ServiceFactoryCreator;
import com.epam.ioc.ServiceFactoryException;
import com.epam.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LodgerSaveController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServiceFactory serviceFactory = ServiceFactoryCreator.newInstance()) {
            String lodgerName = req.getParameter("name");
            Lodger lodger = new Lodger();
            lodger.setName(lodgerName);
            serviceFactory.getLodgerService().save(lodger);
            resp.sendRedirect("lodger-view");
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
