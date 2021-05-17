package com.rbkmoney.cm.converter.shop;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.ShopPayoutScheduleModificationModel;
import com.rbkmoney.damsel.claim_management.ScheduleModification;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.domain.BusinessScheduleRef;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShopPayoutScheduleModificationModelToShopModificationConverter
        implements ClaimConverter<ShopPayoutScheduleModificationModel, ShopModification> {
    @Override
    public ShopModification convert(ShopPayoutScheduleModificationModel shopPayoutScheduleModificationModel) {
        return ShopModification.payout_schedule_modification(
                new ScheduleModification()
                        .setSchedule(
                                Optional.ofNullable(shopPayoutScheduleModificationModel.getBusinessScheduleId())
                                        .map(BusinessScheduleRef::new)
                                        .orElse(null)
                        )
        );
    }
}
