package com.epam.controller.request;

import com.epam.entity.Lodger;
import com.epam.entity.WorkScale;
import com.epam.entity.WorkType;
import com.epam.factory.ServiceFactory;
import com.epam.factory.ServiceFactoryCreator;
import com.epam.factory.ServiceFactoryException;
import com.epam.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/request-send")
public class RequestSendController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Locale locale = new Locale(req.getSession().getAttribute("locale").toString());
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

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
                LogManager.getLogger().info(bundle.getString("log4j.info.lodger.send"));
                resp.sendRedirect("request-view");
            }

        } catch (ServiceFactoryException | ServiceException e) {
            LogManager.getLogger().error(bundle.getString("log4j.error.lodger.send"),e);
            e.printStackTrace();
        }
    }
}
