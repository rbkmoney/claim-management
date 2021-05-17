package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopContractModificationModel;
import com.rbkmoney.damsel.claim_management.ShopContractModification;
import org.springframework.stereotype.Component;

@Component
public class ShopContractModificationToShopContractModificationModelConverter
        implements ClaimConverter<ShopContractModification, ShopContractModificationModel> {
    @Override
    public ShopContractModificationModel convert(ShopContractModification shopContractModification) {
        ShopContractModificationModel shopContractModificationModel = new ShopContractModificationModel();
        shopContractModificationModel.setContractId(shopContractModification.getContractId());
        shopContractModificationModel.setPayoutToolId(shopContractModification.getPayoutToolId());
        return shopContractModificationModel;
    }
}
