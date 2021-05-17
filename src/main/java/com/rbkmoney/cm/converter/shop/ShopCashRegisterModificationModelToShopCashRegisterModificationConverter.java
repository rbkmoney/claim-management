package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCashRegisterCreationModificationModel;
import com.rbkmoney.cm.model.shop.ShopCashRegisterModificationModel;
import com.rbkmoney.damsel.claim_management.CashRegisterModification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationModelToShopCashRegisterModificationConverter
        implements ClaimConverter<CashRegisterModification, ShopCashRegisterModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCashRegisterModificationModel convert(CashRegisterModification cashRegisterModification) {
        switch (cashRegisterModification.getSetField()) {
            case CREATION:
                return conversionService.convert(cashRegisterModification.getCreation(),
                        ShopCashRegisterCreationModificationModel.class);
            default:
                throw new IllegalArgumentException(
                        "Unknown cashRegisterModification type: " + cashRegisterModification.getSetField());
        }
    }

}
