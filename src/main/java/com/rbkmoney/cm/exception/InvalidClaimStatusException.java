package com.rbkmoney.cm.exception;

import com.rbkmoney.cm.model.ClaimStatusModel;
import lombok.Getter;

@Getter
public class InvalidClaimStatusException extends RuntimeException {

    private final ClaimStatusModel claimStatusModel;

    public InvalidClaimStatusException(ClaimStatusModel claimStatusModel) {
        this.claimStatusModel = claimStatusModel;
    }

    public InvalidClaimStatusException(String message, ClaimStatusModel claimStatusModel) {
        super(message);
        this.claimStatusModel = claimStatusModel;
    }
}
