package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.InternationalLegalEntityModel;
import com.rbkmoney.damsel.domain.InternationalLegalEntity;
import org.springframework.stereotype.Component;

@Component
public class InternationalLegalEntityModelToInternationalLegalEntityConverter implements ClaimConverter<InternationalLegalEntityModel, InternationalLegalEntity> {
    @Override
    public InternationalLegalEntity convert(InternationalLegalEntityModel internationalLegalEntityModel) {
        return new InternationalLegalEntity()
                .setLegalName(internationalLegalEntityModel.getLegalName())
                .setTradingName(internationalLegalEntityModel.getTradingName())
                .setActualAddress(internationalLegalEntityModel.getActualAddress())
                .setRegisteredNumber(internationalLegalEntityModel.getRegisteredNumber())
                .setRegisteredAddress(internationalLegalEntityModel.getRegisteredAddress());
    }
}
