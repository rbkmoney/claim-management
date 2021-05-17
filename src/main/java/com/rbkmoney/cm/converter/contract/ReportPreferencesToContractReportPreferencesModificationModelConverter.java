package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.ServiceAcceptanceActPreferencesModel;
import com.rbkmoney.cm.model.contract.ContractReportPreferencesModificationModel;
import com.rbkmoney.damsel.domain.ReportPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ReportPreferencesToContractReportPreferencesModificationModelConverter
        implements ClaimConverter<ReportPreferences, ContractReportPreferencesModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractReportPreferencesModificationModel convert(ReportPreferences reportPreferences) {
        ContractReportPreferencesModificationModel contractReportPreferencesModificationModel =
                new ContractReportPreferencesModificationModel();
        if (reportPreferences.isSetServiceAcceptanceActPreferences()) {
            ServiceAcceptanceActPreferencesModel serviceAcceptanceActPreferencesModel = conversionService.convert(
                    reportPreferences.getServiceAcceptanceActPreferences(),
                    ServiceAcceptanceActPreferencesModel.class
            );
            contractReportPreferencesModificationModel
                    .setServiceAcceptanceActPreferences(serviceAcceptanceActPreferencesModel);
        }
        return contractReportPreferencesModificationModel;
    }
}
