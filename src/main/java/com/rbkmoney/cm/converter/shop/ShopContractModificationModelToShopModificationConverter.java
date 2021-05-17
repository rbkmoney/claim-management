package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopContractModificationModel;
import com.rbkmoney.damsel.claim_management.ShopContractModification;
import com.rbkmoney.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopContractModificationModelToShopModificationConverter
        implements ClaimConverter<ShopContractModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopContractModificationModel shopContractModificationModel) {
        return ShopModification.contract_modification(
                conversionService.convert(shopContractModificationModel, ShopContractModification.class)
        );
    }
}
