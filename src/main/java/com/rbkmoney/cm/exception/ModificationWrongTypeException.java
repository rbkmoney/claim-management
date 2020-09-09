package com.rbkmoney.cm.exception;

public class ModificationWrongTypeException extends RuntimeException {

    public ModificationWrongTypeException() {
        super();
    }

    public ModificationWrongTypeException(String message) {
        super(message);
    }

    public ModificationWrongTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModificationWrongTypeException(Throwable cause) {
        super(cause);
    }
}
