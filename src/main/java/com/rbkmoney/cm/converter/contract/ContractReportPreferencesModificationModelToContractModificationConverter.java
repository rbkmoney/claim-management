package com.rbkmoney.cm.converter.contract;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.contract.ContractReportPreferencesModificationModel;
import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.domain.ReportPreferences;
import com.rbkmoney.damsel.domain.ServiceAcceptanceActPreferences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractReportPreferencesModificationModelToContractModificationConverter implements ClaimConverter<ContractReportPreferencesModificationModel, ContractModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ContractModification convert(ContractReportPreferencesModificationModel contractReportPreferencesModificationModel) {
        return ContractModification.report_preferences_modification(
                new ReportPreferences()
                        .setServiceAcceptanceActPreferences(
                                Optional.ofNullable(contractReportPreferencesModificationModel.getServiceAcceptanceActPreferences())
                                        .map(serviceAcceptanceActPreferencesModel -> conversionService.convert(
                                                serviceAcceptanceActPreferencesModel,
                                                ServiceAcceptanceActPreferences.class
                                                )
                                        ).orElse(null)
                        )
        );
    }
}
