package com.rbkmoney.cm.model.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopCashRegisterCreationModificationModel extends ShopCashRegisterModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "cash_register_params_id", referencedColumnName = "id")
    private CashRegisterParamsModel cashRegisterParamsModel;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopCashRegisterCreationModificationModel
                && super.canEqual(that);
    }

}
