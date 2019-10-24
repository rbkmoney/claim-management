package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("international_legal_entity")
public class InternationalLegalEntityModel extends LegalEntityModel {

    @NotNull
    private String legalName;

    private String tradingName;

    @NotNull
    private String registeredAddress;

    private String actualAddress;

    private String registeredNumber;

}
