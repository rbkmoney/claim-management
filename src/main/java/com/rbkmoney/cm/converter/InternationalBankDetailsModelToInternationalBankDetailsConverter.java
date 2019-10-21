package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.InternationalBankDetailsModel;
import com.rbkmoney.damsel.domain.InternationalBankDetails;
import com.rbkmoney.damsel.domain.Residence;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InternationalBankDetailsModelToInternationalBankDetailsConverter implements ClaimConverter<InternationalBankDetailsModel, InternationalBankDetails> {

    @Override
    public InternationalBankDetails convert(InternationalBankDetailsModel internationalBankDetailsModel) {
        return new InternationalBankDetails()
                .setBic(internationalBankDetailsModel.getBic())
                .setAbaRtn(internationalBankDetailsModel.getAbaRtn())
                .setAddress(internationalBankDetailsModel.getAddress())
                .setName(internationalBankDetailsModel.getName())
                .setCountry(
                        Optional.ofNullable(internationalBankDetailsModel.getCountryCode())
                                .map(Residence::findByValue)
                                .orElse(null)
                );
    }
}
