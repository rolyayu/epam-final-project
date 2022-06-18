package com.epam.controller.lodger;

import com.epam.entity.Lodger;
import com.epam.entity.WorkScale;
import com.epam.entity.WorkType;
import com.epam.ioc.ServiceFactory;
import com.epam.ioc.ServiceFactoryCreator;
import com.epam.ioc.ServiceFactoryException;
import com.epam.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/lodger-send-request")
public class LodgerSendController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServiceFactory factory = ServiceFactoryCreator.newInstance()) {
            HttpSession session = req.getSession();
            String senderName = session.getAttribute("currentLodger").toString();
            Lodger sender = factory.getLodgerService().findAll()
                    .stream()
                    .filter(lodger -> lodger.getName().equals(senderName))
                    .findFirst()
                    .orElse(null);
            WorkScale requestScale = WorkScale.valueOf(req.getParameter("scale"));
            WorkType requestWorkType = WorkType.valueOf(req.getParameter("workType"));
            int timeToDo = Integer.parseInt(req.getParameter("time"));
            if (factory.getLodgerService().sendRequest(requestScale, timeToDo, requestWorkType, sender) != 0) {
                resp.sendRedirect("request-view");
            }

        } catch (ServiceFactoryException | ServiceException e) {
            e.printStackTrace();
        }
    }
}
