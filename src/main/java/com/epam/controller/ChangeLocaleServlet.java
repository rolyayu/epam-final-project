package com.epam.controller;

import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

@WebServlet("/change-locale")
public class ChangeLocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locale = req.getParameter("locale");
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);
        if(locale.equals("ru")) {
            LogManager.getLogger().info(bundle.getString("log4j.info.change.locale.ru"));
        } else {
            LogManager.getLogger().info(bundle.getString("log4j.info.change.locale.en"));
        }
        resp.sendRedirect("title-page.jsp");
    }
}
