package com.rbkmoney.cm.service;

import com.rbkmoney.cm.exception.InvalidChangesetException;
import com.rbkmoney.cm.exception.InvalidClaimStatusException;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.MetadataModel;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.damsel.msgpack.Value;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ConversionWrapperService {

    private final ConversionService conversionService;

    public List<ModificationModel> convertModifications(List<Modification> changeset) {
        List<ModificationModel> modificationModels = changeset.stream()
                .map(change -> conversionService.convert(change, ModificationModel.class))
                .collect(Collectors.toList());

        checkEquals(modificationModels, modificationModels);

        return modificationModels;
    }

    public List<Modification> convertModificationModels(List<ModificationModel> modifications) {
        return modifications.stream()
                .map(modification -> conversionService.convert(modification, Modification.class))
                .collect(Collectors.toList());
    }

    public Claim convertClaim(ClaimModel claimModel) {
        return conversionService.convert(claimModel, Claim.class);
    }

    public ClaimStatus convertClaimStatus(InvalidClaimStatusException ex) {
        return conversionService.convert(ex.getClaimStatusModel(), ClaimStatus.class);
    }

    public Value convertMsgpackValue(MetadataModel metadataModel) {
        return conversionService.convert(metadataModel, Value.class);
    }

    public MetadataModel convertMetadataModel(String key, Value value) {
        return conversionService.convert(Map.entry(key, value), MetadataModel.class);
    }

    private void checkEquals(List<ModificationModel> oldModifications, List<ModificationModel> newModifications) {
        List<ModificationModel> conflictModifications = new ArrayList<>();

        for (int i = 0; i < newModifications.size() - 1; i++) {
            for (int j = i + 1; j < oldModifications.size(); j++) {
                if (newModifications.get(i).canEqual(oldModifications.get(j))) {
                    conflictModifications.add(oldModifications.get(j));
                }
            }
        }

        if (!conflictModifications.isEmpty()) {
            throw new InvalidChangesetException(
                    String.format("ModificationModels contains doubles, count=%s", conflictModifications.size()),
                    conflictModifications
            );
        }
    }
}
