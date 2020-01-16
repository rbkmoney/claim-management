package com.rbkmoney.cm.exception;

import com.rbkmoney.cm.model.ModificationModel;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidChangesetException extends RuntimeException {

    private final List<ModificationModel> modifications;

    public InvalidChangesetException(List<ModificationModel> modifications) {
        this.modifications = modifications;
    }

    public InvalidChangesetException(String message, List<ModificationModel> modifications) {
        super(message);
        this.modifications = modifications;
    }

    public InvalidChangesetException(String message, Throwable cause, List<ModificationModel> modifications) {
        super(message, cause);
        this.modifications = modifications;
    }

    public InvalidChangesetException(Throwable cause, List<ModificationModel> modifications) {
        super(cause);
        this.modifications = modifications;
    }

    public InvalidChangesetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<ModificationModel> modifications) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.modifications = modifications;
    }
}
