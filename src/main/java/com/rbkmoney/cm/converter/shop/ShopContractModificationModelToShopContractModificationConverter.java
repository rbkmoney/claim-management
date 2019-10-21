package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopContractModificationModel;
import com.rbkmoney.damsel.claim_management.ShopContractModification;
import org.springframework.stereotype.Component;

@Component
public class ShopContractModificationModelToShopContractModificationConverter implements ClaimConverter<ShopContractModificationModel, ShopContractModification> {
    @Override
    public ShopContractModification convert(ShopContractModificationModel shopContractModificationModel) {
        return new ShopContractModification()
                .setContractId(shopContractModificationModel.getContractId())
                .setPayoutToolId(shopContractModificationModel.getPayoutToolId());
    }
}
