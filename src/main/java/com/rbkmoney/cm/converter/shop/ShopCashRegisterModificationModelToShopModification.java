package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCashRegisterModificationModel;
import com.rbkmoney.damsel.claim_management.CashRegisterModificationUnit;
import com.rbkmoney.damsel.claim_management.ShopModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationModelToShopModification
        implements ClaimConverter<ShopCashRegisterModificationModel, ShopModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopModification convert(ShopCashRegisterModificationModel cashRegisterModificationModel) {
        return ShopModification.cash_register_modification_unit(
                conversionService.convert(cashRegisterModificationModel, CashRegisterModificationUnit.class));
    }

}
