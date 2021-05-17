package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.LegalAgreementModel;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.stereotype.Component;

@Component
public class LegalAgreementToLegalAgreementModelConverter
        implements ClaimConverter<LegalAgreement, LegalAgreementModel> {
    @Override
    public LegalAgreementModel convert(LegalAgreement legalAgreement) {
        LegalAgreementModel legalAgreementModel = new LegalAgreementModel();
        legalAgreementModel.setLegalAgreementId(legalAgreement.getLegalAgreementId());
        legalAgreementModel.setSignedAt(TypeUtil.stringToInstant(legalAgreement.getSignedAt()));
        if (legalAgreement.isSetValidUntil()) {
            legalAgreementModel.setValidUntil(TypeUtil.stringToInstant(legalAgreement.getValidUntil()));
        }
        return legalAgreementModel;
    }
}
