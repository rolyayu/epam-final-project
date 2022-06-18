package com.epam.pool;

import java.sql.SQLException;

public class ConnectionPoolException extends SQLException {
    public ConnectionPoolException(String reason) {
        super(reason);
    }

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
