package com.rbkmoney.cm.model.shop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopPayoutScheduleModificationModel extends ShopModificationModel {

    private Integer businessScheduleId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopPayoutScheduleModificationModel
                && super.canEqual(that);
    }

}
