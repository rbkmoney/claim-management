package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ContactInfoModel;
import com.rbkmoney.cm.model.RussianPrivateEntityModel;
import com.rbkmoney.damsel.domain.RussianPrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityToRussianPrivateEntityModelConverter implements ClaimConverter<RussianPrivateEntity, RussianPrivateEntityModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public RussianPrivateEntityModel convert(RussianPrivateEntity russianPrivateEntity) {
        RussianPrivateEntityModel russianPrivateEntityModel = new RussianPrivateEntityModel();
        russianPrivateEntityModel.setFirstName(russianPrivateEntity.getFirstName());
        russianPrivateEntityModel.setSecondName(russianPrivateEntity.getSecondName());
        russianPrivateEntityModel.setMiddleName(russianPrivateEntity.getMiddleName());
        russianPrivateEntityModel.setContactInfo(conversionService.convert(russianPrivateEntity.getContactInfo(), ContactInfoModel.class));

        return russianPrivateEntityModel;
    }
}
