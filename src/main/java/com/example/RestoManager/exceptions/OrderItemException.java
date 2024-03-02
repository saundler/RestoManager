package com.example.RestoManager.exceptions;

public class OrderItemException extends Exception {
    public OrderItemException() {
        super();
    }

    public OrderItemException(String message) {
        super(message);
    }

    public OrderItemException(Throwable cause) {
        super(cause);
    }

    public OrderItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderItemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
