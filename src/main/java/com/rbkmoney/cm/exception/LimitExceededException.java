package com.rbkmoney.cm.exception;

public class LimitExceededException extends RuntimeException {

    public LimitExceededException(String message) {
        super(message);
    }

}
