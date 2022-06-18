package com.epam.service;

import com.epam.service.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void start() throws TransactionException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    public void commit() throws TransactionException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    public void rollback() throws TransactionException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }

    public void close() throws TransactionException {
        try {
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException e) {
            throw new TransactionException(e);
        }
    }
}
