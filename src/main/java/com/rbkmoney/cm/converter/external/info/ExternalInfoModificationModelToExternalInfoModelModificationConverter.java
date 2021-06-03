package com.rbkmoney.cm.converter.external.info;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.external.info.ExternalInfoModificationModel;
import com.rbkmoney.damsel.claim_management.ExternalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class ExternalInfoModificationModelToExternalInfoModelModificationConverter
        implements ClaimConverter<ExternalInfoModificationModel, ExternalInfoModificationUnit> {

    @Override
    public ExternalInfoModificationUnit convert(ExternalInfoModificationModel externalInfoModificationModel) {
        return new ExternalInfoModificationUnit()
                .setDocumentId(externalInfoModificationModel.getDocumentId())
                .setRoistatId(externalInfoModificationModel.getRoistatId());
    }

}
