package com.epam.controller.lodger;

import com.epam.entity.Entity;
import com.epam.entity.Request;
import com.epam.entity.WorkPlan;
import com.epam.ioc.ServiceFactory;
import com.epam.ioc.ServiceFactoryCreator;
import com.epam.ioc.ServiceFactoryException;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.TransactionException;

import javax.servlet.ServletException;
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
import java.util.stream.Collectors;

@WebServlet("/lodger-delete")
public class LodgerDeleteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Locale locale = new Locale(req.getSession().getAttribute("locale").toString());
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        try (ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            String lodgerIdString = req.getParameter("lodgerId");
            String message = null;
            if (lodgerIdString != null) {
                try {
                    Long lodgerId = Long.valueOf(lodgerIdString);

                    List<Request> lodgersRequest = factory.getRequestService().getLodgerRequests(lodgerId);
                    List<Request> sentRequests = lodgersRequest.stream()
                            .filter(Request::isInProcess)
                            .toList();

                    List<Long> requestsId = lodgersRequest.stream()
                            .map(Entity::getId)
                            .toList();
                    List<WorkPlan> plans = factory.getWorkPlanService().readAll()
                            .stream()
                            .filter(plan -> requestsId.contains(plan.getRequest().getId()))
                            .toList();
                    List<WorkPlan> processedPlans = plans.stream()
                            .filter(WorkPlan::isDone)
                            .toList();
//                    if (lodgersRequest.isEmpty()) {
//                        factory.getLodgerService().delete(lodgerId);
//                    } else
                    if (sentRequests.isEmpty() || lodgersRequest.isEmpty()) {
                        for (Request notSentRequest : lodgersRequest) {
                            factory.getRequestService().delete(notSentRequest.getId());
                        }
                        factory.getLodgerService().delete(lodgerId);
                    } else if (!sentRequests.isEmpty() && !plans.equals(processedPlans)) {
                        throw new TransactionException();
                    } else if (plans.equals(processedPlans)) {
                        for (WorkPlan processedPlan : processedPlans) {
                            factory.getWorkPlanService().delete(processedPlan);
                        }
                        for (Request sentRequest : sentRequests) {
                            factory.getRequestService().delete(sentRequest.getId());
                        }
                        factory.getLodgerService().delete(lodgerId);
                    }
                } catch (TransactionException e) {
                    message = bundle.getString("error.lodger.delete.request.sent");
                }
            }
            if (message != null) {
                System.out.println(message);
                resp.sendRedirect("lodger-view?message=" + URLDecoder.decode(message, StandardCharsets.UTF_8));
            } else {
                resp.sendRedirect("lodger-view");
            }
        } catch (ServiceException | ServiceFactoryException e) {
            e.printStackTrace();
        }
    }
}
