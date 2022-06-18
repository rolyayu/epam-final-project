package com.epam.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HtmlDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        int slashPosition = path.lastIndexOf("/");
        String folder = path.split("-")[0];
        if (slashPosition != -1) {
            folder = folder.substring(slashPosition+1);
        }
        System.out.println(folder);
        if (folder.equals("title") || folder.equals("/title")) {
            req.getRequestDispatcher("/WEB-INF/jsp/title-page.jsp").forward(req, resp);
        } else if (folder.equals("login") || folder.equals("/login")) {
            req.getRequestDispatcher("/WEB-INF/jsp/login-page.jsp").forward(req, resp);
        } else {
            String jspFileName = path.substring(slashPosition, path.indexOf(".html")) + ".jsp";
            System.out.println(folder + jspFileName);
            req.getRequestDispatcher("/WEB-INF/jsp" + folder + jspFileName).forward(req, resp);
        }
    }
}
