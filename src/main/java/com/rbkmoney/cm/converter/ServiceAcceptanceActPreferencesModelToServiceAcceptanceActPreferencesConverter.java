package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ServiceAcceptanceActPreferencesModel;
import com.rbkmoney.damsel.domain.BusinessScheduleRef;
import com.rbkmoney.damsel.domain.Representative;
import com.rbkmoney.damsel.domain.ServiceAcceptanceActPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ServiceAcceptanceActPreferencesModelToServiceAcceptanceActPreferencesConverter
        implements ClaimConverter<ServiceAcceptanceActPreferencesModel, ServiceAcceptanceActPreferences> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ServiceAcceptanceActPreferences convert(
            ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferencesModel) {
        return new ServiceAcceptanceActPreferences()
                .setSchedule(new BusinessScheduleRef(serviceAcceptanceActPreferencesModel.getSchedulerId()))
                .setSigner(conversionService
                        .convert(serviceAcceptanceActPreferencesModel.getSigner(), Representative.class));
    }
}
