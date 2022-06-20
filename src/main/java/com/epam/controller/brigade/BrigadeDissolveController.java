package com.epam.controller.brigade;

import com.epam.entity.Brigade;
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

@WebServlet("/brigade-dissolve")
public class BrigadeDissolveController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String brigadeIdString = req.getParameter("brigadeId");
            Long brigadeId = Long.valueOf(brigadeIdString);
            Brigade toDissolve = factory.getBrigadeService().getById(brigadeId);
            factory.getBrigadeService().dissolve(toDissolve);
            LogManager.getLogger().info(ResourceBundle.getBundle("messages").getString("log4j.info.brigade.dissolve"));
            resp.sendRedirect("brigade-view");
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
