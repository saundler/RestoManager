package com.example.RestoManager.exceptions;

public class DishException extends Exception {
    public DishException() {
        super();
    }

    public DishException(String message) {
        super(message);
    }

    public DishException(Throwable cause) {
        super(cause);
    }

    public DishException(String message, Throwable cause) {
        super(message, cause);
    }

    public DishException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
