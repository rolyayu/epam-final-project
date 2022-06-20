package com.epam.controller.lodger;

import com.epam.entity.Entity;
import com.epam.entity.Request;
import com.epam.entity.WorkPlan;
import com.epam.factory.ServiceFactory;
import com.epam.factory.ServiceFactoryCreator;
import com.epam.factory.ServiceFactoryException;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.TransactionException;
import org.apache.logging.log4j.LogManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/lodger-delete")
public class LodgerDeleteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Locale locale = new Locale(req.getSession().getAttribute("locale").toString());
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        try (ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String lodgerIdString = req.getParameter("lodgerId");
            if (lodgerIdString != null) {
                try {
                    Long lodgerId = Long.valueOf(lodgerIdString);
                    List<Request> lodgersRequest = factory.getRequestService().getLodgerRequests(lodgerId);

                    List<Request> processedRequests = lodgersRequest.stream()
                            .filter(Request::isInProcess)
                            .toList();

                    List<WorkPlan> plans = factory.getWorkPlanService().getPlansByRequests(lodgersRequest);

                    List<WorkPlan> processedPlans = plans.stream()
                            .filter(WorkPlan::isDone)
                            .toList();

                    if (processedRequests.isEmpty() || lodgersRequest.isEmpty() || plans.equals(processedPlans)) {
                        for (WorkPlan processedPlan : processedPlans) {
                            factory.getWorkPlanService().delete(processedPlan);
                        }
                        for (Request sentRequest : lodgersRequest) {
                            factory.getRequestService().delete(sentRequest.getId());
                        }
                        factory.getLodgerService().delete(lodgerId);
                        LogManager.getLogger().info(bundle.getString("log4j.info.lodger.delete"));
                        if (!req.getSession().getAttribute("currentLodger").toString().equals("epam_reviewer")) {
                            req.getSession().setAttribute("currentLodger", null);
                        }
                    } else {
                        throw new TransactionException();
                    }
                } catch (TransactionException e) {
                    LogManager.getLogger().error(bundle.getString("log4j.error.lodger.delete"));
                }
            }
            resp.sendRedirect("lodger-view");
        } catch (ServiceException | ServiceFactoryException e) {
            e.printStackTrace();
        }
    }
}
