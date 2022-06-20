package com.epam.controller.workplan;

import com.epam.entity.WorkPlan;
import com.epam.factory.ServiceFactory;
import com.epam.factory.ServiceFactoryCreator;
import com.epam.factory.ServiceFactoryException;
import com.epam.service.WorkPlanService;
import com.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;


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
                LogManager.getLogger().info(ResourceBundle.getBundle("messages").getString("log4j.info.workplan.process"));
                resp.sendRedirect("workplan-view");
            }
        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
