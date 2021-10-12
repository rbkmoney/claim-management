package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopCashRegisterModificationModel;
import com.rbkmoney.damsel.claim_management.CashRegisterModification;
import com.rbkmoney.damsel.claim_management.CashRegisterModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ShopCashRegisterModificationUnitToShopCashRegisterModificationModelConverter
        implements ClaimConverter<CashRegisterModificationUnit, ShopCashRegisterModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ShopCashRegisterModificationModel convert(CashRegisterModificationUnit cashRegisterModificationUnit) {
        CashRegisterModification modification = cashRegisterModificationUnit.getModification();
        ShopCashRegisterModificationModel cashRegisterModificationModel =
                conversionService.convert(modification, ShopCashRegisterModificationModel.class);
        if (cashRegisterModificationModel == null) {
            throw new IllegalStateException("ShopCashRegisterModificationModel can't be null");
        }
        cashRegisterModificationModel.setRegisterId(cashRegisterModificationUnit.getId());
        return cashRegisterModificationModel;
    }

}
