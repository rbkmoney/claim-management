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
@DiscriminatorValue("international_bank_account")
public class PayoutToolInfoInternationalBankAccountModel extends PayoutToolInfoModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "international_bank_account_id", referencedColumnName = "id")
    private InternationalBankAccountModel internationalBankAccountModel;

}
