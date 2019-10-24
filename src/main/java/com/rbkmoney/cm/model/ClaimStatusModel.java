package com.rbkmoney.cm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ClaimStatusModel {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status", nullable = false)
    private ClaimStatusEnum claimStatusEnum;

    private String claimStatusReason;

}
