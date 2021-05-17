package com.rbkmoney.cm.exception;

public class MetadataKeyNotFoundException extends RuntimeException {

    public MetadataKeyNotFoundException() {
    }

    public MetadataKeyNotFoundException(String message) {
        super(message);
    }

    public MetadataKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataKeyNotFoundException(Throwable cause) {
        super(cause);
    }

    public MetadataKeyNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
