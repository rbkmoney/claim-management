package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ShopDetailsModel;
import com.rbkmoney.cm.model.shop.ShopDetailsModificationModel;
import com.rbkmoney.damsel.domain.ShopDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsToShopDetailsModificationModelConverter
        implements ClaimConverter<ShopDetails, ShopDetailsModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopDetailsModificationModel convert(ShopDetails shopDetails) {
        ShopDetailsModificationModel shopDetailsModificationModel = new ShopDetailsModificationModel();
        shopDetailsModificationModel.setDetails(conversionService.convert(shopDetails, ShopDetailsModel.class));
        return shopDetailsModificationModel;
    }
}
