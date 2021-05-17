package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopDetailsModificationModel;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.domain.ShopDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsModificationModelToShopModificationConverter
        implements ClaimConverter<ShopDetailsModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopDetailsModificationModel shopDetailsModificationModel) {
        return ShopModification.details_modification(
                conversionService.convert(shopDetailsModificationModel.getDetails(), ShopDetails.class)
        );
    }
}
