package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RussianPrivateEntityModel;
import com.rbkmoney.damsel.domain.PrivateEntity;
import com.rbkmoney.damsel.domain.RussianPrivateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityModelToPrivateEntityConverter implements ClaimConverter<RussianPrivateEntityModel, PrivateEntity> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public PrivateEntity convert(RussianPrivateEntityModel russianPrivateEntityModel) {
        return PrivateEntity.russian_private_entity(
                conversionService.convert(russianPrivateEntityModel, RussianPrivateEntity.class)
        );
    }
}
