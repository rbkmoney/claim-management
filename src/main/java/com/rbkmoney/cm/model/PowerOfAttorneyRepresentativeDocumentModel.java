package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("power_of_attorney")
public class PowerOfAttorneyRepresentativeDocumentModel extends RepresentativeDocumentModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "legal_agreement_id", referencedColumnName = "id")
    private LegalAgreementModel legalAgreement;

}
