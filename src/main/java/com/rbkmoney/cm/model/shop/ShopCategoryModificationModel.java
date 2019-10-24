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
public class ShopCategoryModificationModel extends ShopModificationModel {

    @NotNull
    @Column(nullable = false)
    private int shopCategoryId;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopCategoryModificationModel
                && super.canEqual(that);
    }

}
