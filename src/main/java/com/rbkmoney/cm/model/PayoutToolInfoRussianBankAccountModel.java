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
@DiscriminatorValue("russian_bank_account")
public class PayoutToolInfoRussianBankAccountModel extends PayoutToolInfoModel {

    @NotNull
    @JoinColumn(name = "russian_bank_account_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private RussianBankAccountModel russianBankAccount;

}
