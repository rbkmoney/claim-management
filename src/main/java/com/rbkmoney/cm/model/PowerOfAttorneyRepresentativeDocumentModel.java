package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("power_of_attorney")
public class PowerOfAttorneyRepresentativeDocumentModel extends RepresentativeDocumentModel {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(/*nullable = false, */name = "legal_agreement_id", referencedColumnName = "id")
    private LegalAgreementModel legalAgreement;

}
