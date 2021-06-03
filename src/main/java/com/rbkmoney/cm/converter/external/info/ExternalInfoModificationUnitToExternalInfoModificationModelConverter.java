package com.rbkmoney.cm.converter.external.info;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.external.info.ExternalInfoModificationModel;
import com.rbkmoney.damsel.claim_management.ExternalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class ExternalInfoModificationUnitToExternalInfoModificationModelConverter
        implements ClaimConverter<ExternalInfoModificationUnit, ExternalInfoModificationModel> {

    @Override
    public ExternalInfoModificationModel convert(ExternalInfoModificationUnit externalInfoModificationUnit) {
        ExternalInfoModificationModel model = new ExternalInfoModificationModel();
        model.setDocumentId(externalInfoModificationUnit.getDocumentId());
        model.setRoistatId(externalInfoModificationUnit.getRoistatId());
        return model;
    }
}
