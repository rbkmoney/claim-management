package com.rbkmoney.cm.exception;

import lombok.Getter;

@Getter
public class ChangesetConflictException extends RuntimeException {

    private final long conflictedId;

    public ChangesetConflictException(String message, long conflictedId) {
        super(message);
        this.conflictedId = conflictedId;
    }
}
