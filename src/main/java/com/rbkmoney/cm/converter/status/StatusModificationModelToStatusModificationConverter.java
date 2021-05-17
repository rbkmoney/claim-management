package com.rbkmoney.cm.converter.status;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.status.StatusModificationModel;
import com.rbkmoney.damsel.claim_management.StatusChanged;
import com.rbkmoney.damsel.claim_management.StatusModification;
import org.springframework.stereotype.Component;

@Component
public class StatusModificationModelToStatusModificationConverter
        implements ClaimConverter<StatusModificationModel, StatusModification> {

    @Override
    public StatusModification convert(StatusModificationModel statusModificationModel) {
        switch (statusModificationModel.getStatusModificationType()) {
            case change:
                return StatusModification.change(new StatusChanged());
            default:
                throw new IllegalArgumentException(
                        String.format("Unknown type '%s'", statusModificationModel.getStatusModificationType()));
        }
    }

}
