package com.rbkmoney.cm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClaimStatusModel {

    @Column(name = "claim_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClaimStatusEnum claimStatusEnum;

    private String claimStatusReason;

}
