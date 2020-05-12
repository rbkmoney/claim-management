package com.rbkmoney.cm.converter.shop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.CashRegisterParamsModel;
import com.rbkmoney.damsel.claim_management.CashRegisterParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashRegisterParamsToCashRegisterParamsModelConverter implements ClaimConverter<CashRegisterParams, CashRegisterParamsModel> {

    @Override
    public CashRegisterParamsModel convert(CashRegisterParams cashRegisterParams) {
        try {
            byte[] providerParams = new ObjectMapper().writeValueAsBytes(cashRegisterParams.getCashRegisterProviderParams());

            CashRegisterParamsModel cashRegisterParamsModel = new CashRegisterParamsModel();
            cashRegisterParamsModel.setProviderId(cashRegisterParams.getCashRegisterProviderId());
            cashRegisterParamsModel.setProviderParams(providerParams);

            return cashRegisterParamsModel;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert cashRegisterParams");
        }
    }

}
