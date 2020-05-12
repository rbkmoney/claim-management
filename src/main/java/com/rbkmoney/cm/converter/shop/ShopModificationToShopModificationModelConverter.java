package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.*;
import com.rbkmoney.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopModificationToShopModificationModelConverter implements ClaimConverter<ShopModification, ShopModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModificationModel convert(ShopModification shopModification) {
        switch (shopModification.getSetField()) {
            case CREATION:
                return conversionService.convert(shopModification.getCreation(), ShopCreationModificationModel.class);
            case CONTRACT_MODIFICATION:
                return conversionService.convert(shopModification.getContractModification(), ShopContractModificationModel.class);
            case DETAILS_MODIFICATION:
                return conversionService.convert(shopModification.getDetailsModification(), ShopDetailsModificationModel.class);
            case CATEGORY_MODIFICATION:
                return conversionService.convert(shopModification.getCategoryModification(), ShopCategoryModificationModel.class);
            case LOCATION_MODIFICATION:
                return conversionService.convert(shopModification.getLocationModification(), ShopLocationModificationModel.class);
            case PAYOUT_TOOL_MODIFICATION:
                return conversionService.convert(shopModification.getPayoutToolModification(), ShopPayoutToolModificationModel.class);
            case PAYOUT_SCHEDULE_MODIFICATION:
                return conversionService.convert(shopModification.getPayoutScheduleModification(), ShopPayoutScheduleModificationModel.class);
            case SHOP_ACCOUNT_CREATION:
                return conversionService.convert(shopModification.getShopAccountCreation(), ShopAccountCreationModificationModel.class);
            case CASH_REGISTER_MODIFICATION_UNIT:
                return conversionService.convert(shopModification.getCashRegisterModificationUnit(), ShopCashRegisterModificationModel.class);
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", shopModification.getSetField()));
        }
    }
}
