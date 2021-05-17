package com.rbkmoney.cm.exception;

public class LimitExceededException extends RuntimeException {
    public LimitExceededException() {
    }

    public LimitExceededException(String message) {
        super(message);
    }

    public LimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitExceededException(Throwable cause) {
        super(cause);
    }

    public LimitExceededException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
