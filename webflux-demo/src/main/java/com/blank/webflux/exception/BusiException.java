package com.blank.webflux.exception;

public class BusiException extends RuntimeException{
    public BusiException() {
        super();
    }

    public BusiException(String message) {
        super(message);
    }

    public BusiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusiException(Throwable cause) {
        super(cause);
    }

    protected BusiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
