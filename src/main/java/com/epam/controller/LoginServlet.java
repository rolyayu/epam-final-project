package com.epam.controller;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/lodger-login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = new Locale(req.getSession().getAttribute("locale").toString());
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        try (ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            HttpSession session = req.getSession();
            String lodgerName = req.getParameter("lodgerName");
            List<String> lodgersNames = factory.getLodgerService()
                    .findAll()
                    .stream()
                    .map(Lodger::getName)
                    .toList();
            String message = null;
            if (lodgerName.isBlank()) {
                message = bundle.getString("error.login.empty");
            } else if (!lodgerName.equals("epam_reviewer") && !lodgersNames.contains(lodgerName)) {
                message = bundle.getString("error.login.not.exists");
            }
            if (message != null) {
                req.getRequestDispatcher("/login-page.jsp?message=" + URLDecoder.decode(message, StandardCharsets.UTF_8)).forward(req, resp);
            } else {
                session.setAttribute("currentLodger", lodgerName);
                if (lodgerName.equals("epam_reviewer")) {
                    LogManager.getLogger().info(bundle.getString("log4j.info.login.dispatcher"));
                } else {
                    LogManager.getLogger().info(bundle.getString("log4j.info.login.lodger") + lodgerName.toLowerCase());
                }
                resp.sendRedirect("title-page.jsp");
            }
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
