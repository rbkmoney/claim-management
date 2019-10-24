package com.rbkmoney.cm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.handler.ClaimManagementHandler;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ContinuationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

@Configuration
public class AppConfig {

    @Bean
    public ContinuationTokenService continuationTokenService(@Value("${continuation-secret}") String secret, ObjectMapper objectMapper) {
        return new ContinuationTokenService(secret, objectMapper);
    }

    @Bean
    public ClaimManagementService claimManagementService(ClaimRepository claimRepository, ContinuationTokenService continuationTokenService) {
        return new ClaimManagementService(claimRepository, continuationTokenService);
    }

    @Bean
    public ClaimManagementHandler claimManagementHandler(ClaimManagementService claimManagementService, ConversionService conversionService) {
        return new ClaimManagementHandler(claimManagementService, conversionService);
    }

}
