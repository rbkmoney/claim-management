package com.rbkmoney.cm.model.shop;

import com.rbkmoney.cm.model.ShopLocationModel;
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
public class ShopLocationModificationModel extends ShopModificationModel {

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_location_id", referencedColumnName = "id")
    private ShopLocationModel location;

    @Override
    public boolean canEqual(final Object that) {
        return that instanceof ShopLocationModificationModel
                && super.canEqual(that);
    }

}
