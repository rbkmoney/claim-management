package com.rbkmoney.cm.converter.external.info;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.external.info.ExternalInfoModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.ExternalInfoModificationUnit;
import org.springframework.stereotype.Component;

@Component
public class ExternalInfoModificationModelToClaimModificationConverter
        implements ClaimConverter<ExternalInfoModificationModel, ClaimModification> {

    @Override
    public ClaimModification convert(ExternalInfoModificationModel externalInfoModificationModel) {
        return ClaimModification.external_info_modification(
                new ExternalInfoModificationUnit()
                        .setDocumentId(externalInfoModificationModel.getDocumentId())
                        .setRoistatId(externalInfoModificationModel.getRoistatId())
        );
    }
}
