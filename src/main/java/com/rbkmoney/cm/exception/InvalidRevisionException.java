package com.rbkmoney.cm.exception;

public class InvalidRevisionException extends RuntimeException {

    public InvalidRevisionException() {
    }

    public InvalidRevisionException(String message) {
        super(message);
    }

    public InvalidRevisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRevisionException(Throwable cause) {
        super(cause);
    }

    public InvalidRevisionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
