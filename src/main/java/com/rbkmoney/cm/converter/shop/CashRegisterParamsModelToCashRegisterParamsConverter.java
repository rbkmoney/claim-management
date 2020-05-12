package com.rbkmoney.cm.converter.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.shop.CashRegisterParamsModel;
import com.rbkmoney.damsel.claim_management.CashRegisterParams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CashRegisterParamsModelToCashRegisterParamsConverter implements ClaimConverter<CashRegisterParamsModel, CashRegisterParams> {

    @Override
    public CashRegisterParams convert(CashRegisterParamsModel cashRegisterParamsModel) {
        try {
            Map<String, String> providerParamsMap = new ObjectMapper().readValue(cashRegisterParamsModel.getProviderParams(),
                    new TypeReference<Map<String, String>>() {});

            return new CashRegisterParams()
                    .setCashRegisterProviderId(cashRegisterParamsModel.getProviderId())
                    .setCashRegisterProviderParams(providerParamsMap);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to convert cashRegisterParamsModel", e);
        }
    }
}
