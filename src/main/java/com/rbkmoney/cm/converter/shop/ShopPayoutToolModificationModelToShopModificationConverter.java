package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopPayoutToolModificationModel;
import com.rbkmoney.damsel.claim_management.ShopModification;
import org.springframework.stereotype.Component;

@Component
public class ShopPayoutToolModificationModelToShopModificationConverter implements ClaimConverter<ShopPayoutToolModificationModel, ShopModification> {
    @Override
    public ShopModification convert(ShopPayoutToolModificationModel shopPayoutToolModificationModel) {
        return ShopModification.payout_tool_modification(shopPayoutToolModificationModel.getPayoutToolId());
    }
}
