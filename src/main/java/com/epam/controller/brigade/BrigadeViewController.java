package com.epam.controller.brigade;

import com.epam.entity.Brigade;
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

@WebServlet("/brigade-view")
public class BrigadeViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            List<Brigade> brigadeList = factory.getBrigadeService().readAll();
            req.setAttribute("brigades",brigadeList);
            int availableWorkers = factory.getWorkerService().readAllAvailable().size();
            req.setAttribute("availableWorkers",availableWorkers);
            req.getRequestDispatcher("/WEB-INF/jsp/brigade/brigade-view.jsp").forward(req,resp);
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
