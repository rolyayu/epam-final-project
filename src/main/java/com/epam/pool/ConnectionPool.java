package com.epam.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConnectionPool {
    private static String jdbcUrl;
    private static String jdbcUser;
    private static String jdbcPassword;

    private static int validationTime;

    private static int size;

    private static final Queue<Connection> freeConnections = new ConcurrentLinkedDeque<>();
    private static final Queue<Connection> usedConnections = new ConcurrentLinkedDeque<>();


    public static void init(String driverClass, String jdbcUrl1, String jdbcUser1,
                            String jdbcPassword1, int validationTime1, int size1) throws ClassNotFoundException {
        Class.forName(driverClass);
        jdbcUrl = jdbcUrl1;
        jdbcUser = jdbcUser1;
        jdbcPassword= jdbcPassword1;
        validationTime = validationTime1;
        size = size1;

        for (int i = 0; i < size; i++) {
            freeConnections.add(newConnection());
        }
    }

    public static Connection getConnection() throws ConnectionPoolException {
        Connection connection;
        try {
            connection = freeConnections.poll();
            if (connection != null) {
                if (connection.isValid(validationTime))
                    return new PsqlConnection(connection);
            } else if (usedConnections.size() == size) {
                connection = newConnection();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
        usedConnections.add(connection);
        return new PsqlConnection(connection);
    }

    public static void freeConnection(Connection connection) {
        try {
            connection.clearWarnings();
            usedConnections.remove(connection);
            freeConnections.add(connection);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }

    private static Connection newConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void destroy() {

        usedConnections.addAll(freeConnections);
        freeConnections.clear();

        for (Connection connection : usedConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        usedConnections.clear();
    }

}
