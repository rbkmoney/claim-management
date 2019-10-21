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
@DiscriminatorValue("international_bank_account")
public class PayoutToolInfoInternationalBankAccountModel extends PayoutToolInfoModel {

    @JoinColumn(/*nullable = false, */name = "international_bank_account_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private InternationalBankAccountModel internationalBankAccountModel;

}
