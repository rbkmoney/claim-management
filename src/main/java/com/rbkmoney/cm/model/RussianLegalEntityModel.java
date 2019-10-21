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

    //    @Column(nullable = false)
    private String registeredName;

    //    @Column(nullable = false)
    private String registeredNumber;

    //    @Column(nullable = false)
    private String inn;

    //    @Column(nullable = false)
    private String actualAddress;

    //    @Column(nullable = false)
    private String postAddress;

    //    @Column(nullable = false)
    private String representativePosition;

    //    @Column(nullable = false)
    private String representativeFullName;

    //    @Column(nullable = false)
    private String representativeDocument;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(/*nullable = false, */name = "russian_bank_account_id", referencedColumnName = "id")
    private RussianBankAccountModel russianBankAccount;

}
