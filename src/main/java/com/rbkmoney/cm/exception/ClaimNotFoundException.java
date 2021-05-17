package com.rbkmoney.cm.exception;

public class ClaimNotFoundException extends RuntimeException {

    public ClaimNotFoundException() {
    }

    public ClaimNotFoundException(String message) {
        super(message);
    }

    public ClaimNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClaimNotFoundException(Throwable cause) {
        super(cause);
    }

    public ClaimNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
