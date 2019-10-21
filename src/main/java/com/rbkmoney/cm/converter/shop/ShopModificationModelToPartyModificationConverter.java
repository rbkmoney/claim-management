package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopModificationModel;
import com.rbkmoney.damsel.claim_management.PartyModification;
import com.rbkmoney.damsel.claim_management.ShopModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopModificationModelToPartyModificationConverter implements ClaimConverter<ShopModificationModel, PartyModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PartyModification convert(ShopModificationModel shopModificationModel) {
        return PartyModification.shop_modification(
                conversionService.convert(shopModificationModel, ShopModificationUnit.class)
        );
    }
}
