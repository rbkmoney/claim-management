package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianLegalEntityModel;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianLegalEntityModelToRussianLegalEntityConverter implements ClaimConverter<RussianLegalEntityModel, RussianLegalEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RussianLegalEntity convert(RussianLegalEntityModel russianLegalEntityModel) {
        return new RussianLegalEntity()
                .setInn(russianLegalEntityModel.getInn())
                .setPostAddress(russianLegalEntityModel.getPostAddress())
                .setRegisteredName(russianLegalEntityModel.getRegisteredName())
                .setRegisteredNumber(russianLegalEntityModel.getRegisteredNumber())
                .setActualAddress(russianLegalEntityModel.getActualAddress())
                .setRepresentativeFullName(russianLegalEntityModel.getRepresentativeFullName())
                .setRepresentativePosition(russianLegalEntityModel.getRepresentativePosition())
                .setRepresentativeDocument(russianLegalEntityModel.getRepresentativeDocument())
                .setRussianBankAccount(conversionService.convert(russianLegalEntityModel.getRussianBankAccount(), RussianBankAccount.class));
    }
}
