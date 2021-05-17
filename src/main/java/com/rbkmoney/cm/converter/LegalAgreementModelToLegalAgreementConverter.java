package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.LegalAgreementModel;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LegalAgreementModelToLegalAgreementConverter
        implements ClaimConverter<LegalAgreementModel, LegalAgreement> {
    @Override
    public LegalAgreement convert(LegalAgreementModel legalAgreementModel) {
        return new LegalAgreement()
                .setLegalAgreementId(legalAgreementModel.getLegalAgreementId())
                .setSignedAt(TypeUtil.temporalToString(legalAgreementModel.getSignedAt()))
                .setValidUntil(
                        Optional.ofNullable(legalAgreementModel.getValidUntil())
                                .map(TypeUtil::temporalToString)
                                .orElse(null)
                );
    }
}
