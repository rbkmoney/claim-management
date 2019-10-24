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
@DiscriminatorValue("russian_legal_entity")
public class RussianLegalEntityModel extends LegalEntityModel {

    private String registeredName;

    private String registeredNumber;

    private String inn;

    private String actualAddress;

    private String postAddress;

    private String representativePosition;

    private String representativeFullName;

    private String representativeDocument;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "russian_bank_account_id", referencedColumnName = "id")
    private RussianBankAccountModel russianBankAccount;

}
