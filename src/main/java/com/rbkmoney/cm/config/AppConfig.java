package com.rbkmoney.cm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.handler.ClaimManagementHandler;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ContinuationTokenService;
import com.rbkmoney.cm.service.ConversionWrapperService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
public class AppConfig {

    @Bean
    public ConversionWrapperService conversionWrapperService(ConversionService conversionService) {
        return new ConversionWrapperService(conversionService);
    }

    @Bean
    public ContinuationTokenService continuationTokenService(@Value("${claim-managment.continuation-secret}") String secret, ObjectMapper objectMapper) {
        return new ContinuationTokenService(secret, objectMapper);
    }

    @Bean
    public ClaimManagementService claimManagementService(ClaimRepository claimRepository, ContinuationTokenService continuationTokenService) {
        return new ClaimManagementService(claimRepository, continuationTokenService);
    }

    @Bean
    public ClaimManagementHandler claimManagementHandler(@Value("${claim-managment.limit}") long limit, ClaimManagementService claimManagementService, ConversionWrapperService conversionService) {
        return new ClaimManagementHandler(limit, claimManagementService, conversionService);
    }
}
