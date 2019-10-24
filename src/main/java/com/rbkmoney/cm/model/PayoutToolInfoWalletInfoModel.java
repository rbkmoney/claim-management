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
@DiscriminatorValue("wallet_info")
@EqualsAndHashCode(callSuper = true)
public class PayoutToolInfoWalletInfoModel extends PayoutToolInfoModel {

    @NotNull
    String walletId;

}
