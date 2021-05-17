package com.rbkmoney.cm.exception;

import com.rbkmoney.cm.model.ModificationModel;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidChangesetException extends RuntimeException {

    private final List<ModificationModel> modifications;

    public InvalidChangesetException(String message, List<ModificationModel> modifications) {
        super(message);
        this.modifications = modifications;
    }

}
