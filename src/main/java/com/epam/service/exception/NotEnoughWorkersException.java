package com.epam.service.exception;

public class NotEnoughWorkersException extends ServiceException{
    public NotEnoughWorkersException(String message) {
        super(message);
    }

    public NotEnoughWorkersException() {
    }

    public NotEnoughWorkersException(Throwable cause) {
        super(cause);
    }
}
