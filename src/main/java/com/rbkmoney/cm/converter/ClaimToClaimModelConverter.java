package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusModel;
import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClaimToClaimModelConverter implements ClaimConverter<Claim, ClaimModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModel convert(Claim claim) {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setId(claim.getId());
        claimModel.setPartyId(claim.getPartyId());
        claimModel.setCreatedAt(TypeUtil.stringToLocalDateTime(claim.getCreatedAt()));
        claimModel.setRevision(claim.getRevision());
        claimModel.setClaimStatus(conversionService.convert(claim.getStatus(), ClaimStatusModel.class));
        claimModel.setModifications(
                claim.getChangeset().stream()
                        .map(modificationUnit -> conversionService.convert(modificationUnit, ModificationModel.class))
                        .collect(Collectors.toList())
        );

        if (claim.isSetUpdatedAt()) {
            claimModel.setUpdatedAt(TypeUtil.stringToLocalDateTime(claim.getUpdatedAt()));
        }
        if (claim.isSetMetadata()) {
            claimModel.setMetadata(
                    claim.getMetadata().entrySet().stream()
                            .map(entry -> conversionService.convert(entry, MetadataModel.class))
                            .collect(Collectors.toList())
            );
        }
        return claimModel;
    }
}
