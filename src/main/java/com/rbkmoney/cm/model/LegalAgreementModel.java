package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import java.time.Instant;

@Data
@Entity
public class LegalAgreementModel {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(nullable = false)
    private Instant signedAt;

    @NotNull
    @Column(nullable = false)
    private String legalAgreementId;

    private Instant validUntil;

}
