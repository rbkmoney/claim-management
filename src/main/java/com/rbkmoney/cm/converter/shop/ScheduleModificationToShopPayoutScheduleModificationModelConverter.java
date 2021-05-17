package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopPayoutScheduleModificationModel;
import com.rbkmoney.damsel.claim_management.ScheduleModification;
import org.springframework.stereotype.Component;

@Component
public class ScheduleModificationToShopPayoutScheduleModificationModelConverter
        implements ClaimConverter<ScheduleModification, ShopPayoutScheduleModificationModel> {
    @Override
    public ShopPayoutScheduleModificationModel convert(ScheduleModification scheduleModification) {
        ShopPayoutScheduleModificationModel shopPayoutScheduleModificationModel =
                new ShopPayoutScheduleModificationModel();
        if (scheduleModification.isSetSchedule()) {
            shopPayoutScheduleModificationModel.setBusinessScheduleId(scheduleModification.getSchedule().getId());
        }
        return shopPayoutScheduleModificationModel;
    }
}
