package com.epam.service.exception;

import java.sql.SQLException;

public class TransactionException extends ServiceException {
    public TransactionException() {
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String message) {
        super(message);
    }
}
