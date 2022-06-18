package com.epam.service.exception;

public class ServiceException extends Exception{
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException() {
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
