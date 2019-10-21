package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianBankAccountModel;
import com.rbkmoney.cm.model.RussianLegalEntityModel;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianLegalEntityToRussianLegalEntityModelConverter implements ClaimConverter<RussianLegalEntity, RussianLegalEntityModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RussianLegalEntityModel convert(RussianLegalEntity russianLegalEntity) {
        RussianLegalEntityModel russianLegalEntityModel = new RussianLegalEntityModel();
        russianLegalEntityModel.setActualAddress(russianLegalEntity.getActualAddress());
        russianLegalEntityModel.setPostAddress(russianLegalEntity.getPostAddress());
        russianLegalEntityModel.setInn(russianLegalEntity.getInn());
        russianLegalEntityModel.setRegisteredName(russianLegalEntity.getRegisteredName());
        russianLegalEntityModel.setRegisteredNumber(russianLegalEntity.getRegisteredNumber());
        russianLegalEntityModel.setRepresentativeDocument(russianLegalEntity.getRepresentativeDocument());
        russianLegalEntityModel.setRepresentativeFullName(russianLegalEntity.getRepresentativeFullName());
        russianLegalEntityModel.setRepresentativePosition(russianLegalEntity.getRepresentativePosition());
        russianLegalEntityModel.setRussianBankAccount(conversionService.convert(russianLegalEntity.getRussianBankAccount(), RussianBankAccountModel.class));
        return russianLegalEntityModel;
    }
}
