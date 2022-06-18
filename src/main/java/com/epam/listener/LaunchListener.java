package com.epam.listener;

import com.epam.pool.ConnectionPool;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LaunchListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String jdbcDriver = context.getInitParameter("jdbc-driver");
        String jdbcUrl = context.getInitParameter("jdbc-url");
        String jdbcUser = context.getInitParameter("jdbc-user");
        String jdbcPassword = context.getInitParameter("jdbc-password");
        int validationTime = Integer.parseInt(context.getInitParameter("connection-valid-time"));
        int maxConnections = Integer.parseInt(context.getInitParameter("max-connection"));
        try {
            ConnectionPool.init(jdbcDriver,jdbcUrl,jdbcUser,
                    jdbcPassword,validationTime,maxConnections);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.destroy();
    }
}
