package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianPrivateEntityModel;
import com.rbkmoney.damsel.domain.ContactInfo;
import com.rbkmoney.damsel.domain.RussianPrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityModelToRussianPrivateEntityConverter implements ClaimConverter<RussianPrivateEntityModel, RussianPrivateEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RussianPrivateEntity convert(RussianPrivateEntityModel russianPrivateEntityModel) {
        return new RussianPrivateEntity()
                .setSecondName(russianPrivateEntityModel.getSecondName())
                .setFirstName(russianPrivateEntityModel.getFirstName())
                .setMiddleName(russianPrivateEntityModel.getMiddleName())
                .setContactInfo(conversionService.convert(russianPrivateEntityModel.getContactInfo(), ContactInfo.class));
    }
}
