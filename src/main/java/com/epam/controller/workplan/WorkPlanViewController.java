package com.epam.controller.workplan;

import com.epam.entity.WorkPlan;
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

@WebServlet("/workplan-view")
public class WorkPlanViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            List<WorkPlan> workPlans = factory.getWorkPlanService().readAll();
            req.setAttribute("workplans",workPlans);
            req.getRequestDispatcher("WEB-INF/jsp/workplan/workplan-view.jsp").forward(req,resp);
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
