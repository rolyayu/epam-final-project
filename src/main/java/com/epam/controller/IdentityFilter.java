package com.epam.controller;

import com.epam.entity.Lodger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IdentityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if(session!=null) {
            if(session.getAttribute("currentLodger") != null) {
                chain.doFilter(req,resp);
            } else {
                resp.sendRedirect("login-page.jsp");
            }
        } else {
            resp.sendRedirect("login-page.jsp");
        }
    }
}
