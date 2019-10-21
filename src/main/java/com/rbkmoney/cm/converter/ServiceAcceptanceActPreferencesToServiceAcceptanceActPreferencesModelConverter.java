package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.RepresentativeModel;
import com.rbkmoney.cm.model.ServiceAcceptanceActPreferencesModel;
import com.rbkmoney.damsel.domain.ServiceAcceptanceActPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ServiceAcceptanceActPreferencesToServiceAcceptanceActPreferencesModelConverter implements ClaimConverter<ServiceAcceptanceActPreferences, ServiceAcceptanceActPreferencesModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ServiceAcceptanceActPreferencesModel convert(ServiceAcceptanceActPreferences serviceAcceptanceActPreferences) {
        ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferencesModel = new ServiceAcceptanceActPreferencesModel();
        serviceAcceptanceActPreferencesModel.setSchedulerId(serviceAcceptanceActPreferences.getSchedule().getId());
        serviceAcceptanceActPreferencesModel.setSigner(conversionService.convert(serviceAcceptanceActPreferences.getSigner(), RepresentativeModel.class));
        return serviceAcceptanceActPreferencesModel;
    }
}
