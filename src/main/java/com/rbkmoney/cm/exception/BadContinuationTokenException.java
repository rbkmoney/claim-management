package com.rbkmoney.cm.exception;

public class BadContinuationTokenException extends RuntimeException {

    public BadContinuationTokenException() {
    }

    public BadContinuationTokenException(String message) {
        super(message);
    }

    public BadContinuationTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadContinuationTokenException(Throwable cause) {
        super(cause);
    }

    public BadContinuationTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
