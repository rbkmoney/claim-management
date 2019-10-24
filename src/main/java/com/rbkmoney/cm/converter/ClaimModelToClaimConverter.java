package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.damsel.claim_management.ModificationUnit;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClaimModelToClaimConverter implements ClaimConverter<ClaimModel, Claim> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public Claim convert(ClaimModel claimModel) {
        ClaimStatus claimStatus = conversionService.convert(claimModel.getClaimStatus(), ClaimStatus.class);
        return new Claim()
                .setId(claimModel.getId())
                .setCreatedAt(TypeUtil.temporalToString(claimModel.getCreatedAt()))
                .setUpdatedAt(
                        Optional.ofNullable(claimModel.getUpdatedAt())
                                .map(TypeUtil::temporalToString)
                                .orElse(null)
                )
                .setStatus(claimStatus)
                .setRevision(claimModel.getRevision())
                .setChangeset(
                        claimModel.getModifications().stream()
                                .map(modificationModel -> conversionService.convert(modificationModel, ModificationUnit.class))
                                .collect(Collectors.toList())
                ).setMetadata(
                        Optional.ofNullable(claimModel.getMetadata())
                                .filter(metadataModels -> !metadataModels.isEmpty())
                                .map(
                                        metadata -> metadata.stream()
                                                .map(
                                                        metadataModel -> new AbstractMap.SimpleEntry<>(
                                                                metadataModel.getKey(),
                                                                conversionService.convert(metadataModel, Value.class)
                                                        )
                                                )
                                                .collect(
                                                        Collectors.toMap(
                                                                Map.Entry::getKey,
                                                                Map.Entry::getValue
                                                        )
                                                )
                                )
                                .orElse(null)
                );
    }
}
