package com.epam.controller.workplan;

import com.epam.entity.WorkPlan;
import com.epam.entity.Worker;
import com.epam.ioc.ServiceFactory;
import com.epam.ioc.ServiceFactoryCreator;
import com.epam.ioc.ServiceFactoryException;
import com.epam.service.WorkPlanService;
import com.epam.service.WorkerService;
import com.epam.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/workplan-processed")
public class WorkPlanProcessedController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String idString = req.getParameter("id");
            if(idString!=null) {
                Long id = Long.valueOf(idString);
                WorkPlanService workPlanService = factory.getWorkPlanService();
                WorkPlan toProcessed = workPlanService.readById(id);
                factory.getDispatcher().completeWorkPlan(toProcessed);
                resp.sendRedirect("workplan-view");
            }
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
