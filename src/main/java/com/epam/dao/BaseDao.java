package com.epam.dao;

import java.sql.Connection;

abstract public class BaseDao {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
