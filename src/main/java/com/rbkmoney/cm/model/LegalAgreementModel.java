package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class LegalAgreementModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private LocalDateTime signedAt;

    @Column(nullable = false)
    private String legalAgreementId;

    private LocalDateTime validUntil;

}
