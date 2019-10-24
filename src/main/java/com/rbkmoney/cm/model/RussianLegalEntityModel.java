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
@DiscriminatorValue("russian_legal_entity")
public class RussianLegalEntityModel extends LegalEntityModel {

    @NotNull
    private String registeredName;

    @NotNull
    private String registeredNumber;

    @NotNull
    private String inn;

    @NotNull
    private String actualAddress;

    @NotNull
    private String postAddress;

    @NotNull
    private String representativePosition;

    @NotNull
    private String representativeFullName;

    @NotNull
    private String representativeDocument;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "russian_bank_account_id", referencedColumnName = "id")
    private RussianBankAccountModel russianBankAccount;

}
