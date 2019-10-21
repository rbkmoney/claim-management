package com.rbkmoney.cm.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Embeddable
public class ClaimStatusModel {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimStatusEnum claimStatus;

    private String claimStatusReason;

}
