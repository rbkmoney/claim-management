package com.rbkmoney.cm.model.shop;

import com.rbkmoney.cm.model.ShopAccountParamsModel;
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
public class ShopAccountCreationModificationModel extends ShopModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_account_params_id", referencedColumnName = "id")
    private ShopAccountParamsModel shopAccountParams;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopAccountCreationModificationModel
                && super.canEqual(that);
    }

}
