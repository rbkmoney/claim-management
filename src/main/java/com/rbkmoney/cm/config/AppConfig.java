package com.rbkmoney.cm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.cm.handler.ClaimManagementHandler;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.service.ClaimCommitterService;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ContinuationTokenService;
import com.rbkmoney.cm.service.ConversionWrapperService;
import com.rbkmoney.cm.service.impl.ClaimManagementServiceImpl;
import com.rbkmoney.cm.util.ClaimEventFactory;
import org.apache.thrift.TBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class AppConfig {

    @Bean
    public ConversionWrapperService conversionWrapperService(ConversionService conversionService) {
        return new ConversionWrapperService(conversionService);
    }

    @Bean
    public ContinuationTokenService continuationTokenService(@Value("${claim-management.continuation-secret}") String secret,
                                                             ObjectMapper objectMapper) {
        return new ContinuationTokenService(secret, objectMapper);
    }

    @Bean
    public ClaimManagementService claimManagementService(ContinuationTokenService continuationTokenService,
                                                         ConversionWrapperService conversionWrapperService,
                                                         ClaimRepository claimRepository,
                                                         ClaimEventFactory claimEventFactory,
                                                         KafkaTemplate<String, TBase> kafkaTemplate,
                                                         RetryTemplate retryTemplate,
                                                         @Value("${kafka.topics.claim-event-sink.id}") String eventSinkTopic) {
        return new ClaimManagementServiceImpl(continuationTokenService, conversionWrapperService, claimRepository, claimEventFactory, kafkaTemplate, retryTemplate, eventSinkTopic);
    }

    @Bean
    public ClaimManagementHandler claimManagementHandler(ClaimManagementService claimManagementService,
                                                         ConversionWrapperService conversionService,
                                                         @Value("${claim-management.limit}") long limit) {
        return new ClaimManagementHandler(claimManagementService, conversionService, limit);
    }

    @Bean
    public ClaimCommitterService claimCommitterService(ClaimManagementService claimManagementService,
                                                       ConversionWrapperService conversionWrapperService,
                                                       CommitterConfig committerConfig) {
        return new ClaimCommitterService(claimManagementService, conversionWrapperService, committerConfig.getCommitters());
    }

}
