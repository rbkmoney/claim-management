package com.rbkmoney.cm.config;

import com.rbkmoney.cm.listener.ClaimEventSinkListener;
import com.rbkmoney.cm.service.ClaimCommitterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class KafkaConsumerBeanEnableConfig {

    @Bean
    @ConditionalOnProperty(value = "kafka.topics.claim-event-sink.enabled", havingValue = "true")
    public ClaimEventSinkListener paymentEventsKafkaListener(ClaimCommitterService claimCommitterService) {
        return new ClaimEventSinkListener(claimCommitterService);
    }

}
