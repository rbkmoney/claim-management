package com.rbkmoney.cm.model.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopPayoutToolModificationModel extends ShopModificationModel {

    @NotNull
    @Column(nullable = false)
    private String payoutToolId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopPayoutToolModificationModel
                && super.canEqual(that);
    }

}
