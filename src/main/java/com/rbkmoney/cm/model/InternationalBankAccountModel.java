package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class InternationalBankAccountModel {

    @Id
    @GeneratedValue
    private long id;

    private String accountHolder;

    private String iban;

    private String number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "international_bank_details_id", referencedColumnName = "id")
    private InternationalBankDetailsModel internationalBankDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "correspondent_account_id", referencedColumnName = "id")
    private InternationalBankAccountModel correspondentAccount;

}
