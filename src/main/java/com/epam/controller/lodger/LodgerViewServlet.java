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
import java.util.List;

public class LodgerViewServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServiceFactory serviceFactory = ServiceFactoryCreator.newInstance()) {
            List<Lodger> lodgers = serviceFactory.getLodgerService().findAll();
            req.setAttribute("lodgers", lodgers);
            req.getRequestDispatcher("/WEB-INF/jsp/lodger/lodger-view.jsp").forward(req, resp);
        } catch (ServletException | ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
