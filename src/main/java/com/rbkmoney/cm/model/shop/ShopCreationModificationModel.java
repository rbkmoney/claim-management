package com.rbkmoney.cm.model.shop;

import com.rbkmoney.cm.model.ShopParamsModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ShopCreationModificationModel extends ShopModificationModel {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "shop_params_id", referencedColumnName = "id")
    private ShopParamsModel shopParams;

}
