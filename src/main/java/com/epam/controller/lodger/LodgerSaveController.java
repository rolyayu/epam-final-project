package com.epam.controller.lodger;

import com.epam.entity.Lodger;
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
import java.util.ResourceBundle;

@WebServlet("/lodger-save")
public class LodgerSaveController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServiceFactory serviceFactory = ServiceFactoryCreator.newInstance()) {
            String lodgerName = req.getParameter("name");
            Lodger lodger = new Lodger();
            lodger.setName(lodgerName);
            serviceFactory.getLodgerService().save(lodger);
            LogManager.getLogger().info(ResourceBundle.getBundle("messages").getString("log4j.info.lodger.add"));
            resp.sendRedirect("lodger-view");
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
