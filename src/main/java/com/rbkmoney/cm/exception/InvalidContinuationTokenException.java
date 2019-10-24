package com.rbkmoney.cm.exception;

public class InvalidContinuationTokenException extends RuntimeException {

    public InvalidContinuationTokenException() {
    }

    public InvalidContinuationTokenException(String message) {
        super(message);
    }

    public InvalidContinuationTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidContinuationTokenException(Throwable cause) {
        super(cause);
    }

    public InvalidContinuationTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
