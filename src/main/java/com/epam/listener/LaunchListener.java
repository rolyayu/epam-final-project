package com.epam.listener;

import com.epam.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ResourceBundle;

public class LaunchListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages");
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
            LogManager.getLogger().info(bundle.getString("log4j.info.pool.init"));
        } catch (ClassNotFoundException e) {
            LogManager.getLogger().info(bundle.getString("log4j.error.pool.init"));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.destroy();
    }
}
