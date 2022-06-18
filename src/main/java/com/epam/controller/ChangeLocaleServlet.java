package com.epam.controller;

import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/change-locale")
public class ChangeLocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String locale = req.getParameter("locale");
        HttpSession session = req.getSession();
        session.setAttribute("locale", locale);
        LogManager.getLogger().info("locale changed to " + locale);
        resp.sendRedirect("title-page.jsp");
    }
}
