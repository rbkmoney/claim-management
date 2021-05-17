package com.rbkmoney.cm.config;

import com.rbkmoney.cm.serde.ClaimManagementEventDeserializer;
import com.rbkmoney.damsel.claim_management.Event;
import com.rbkmoney.kafka.common.exception.handler.SeekToCurrentWithSleepErrorHandler;
import com.rbkmoney.kafka.common.serialization.ThriftSerializer;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.thrift.TBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ErrorHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private static final String PKCS_12 = "PKCS12";

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;
    @Value("${kafka.ssl.server-password}")
    private String serverStorePassword;
    @Value("${kafka.ssl.server-keystore-location}")
    private String serverStoreCertPath;
    @Value("${kafka.ssl.keystore-password}")
    private String keyStorePassword;
    @Value("${kafka.ssl.key-password}")
    private String keyPassword;
    @Value("${kafka.ssl.keystore-location}")
    private String clientStoreCertPath;
    @Value("${kafka.ssl.enable}")
    private boolean kafkaSslEnable;

    @Value("${kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.client-id}")
    private String clientId;
    @Value("${kafka.consumer.max-poll-records}")
    private int maxPollRecords;

    @Value("${kafka.consumer.concurrency}")
    private int concurrency;
    @Value("${kafka.error-handler.sleep-time-seconds}")
    private int errorHandlerSleepTimeSeconds;

    @Value("${kafka.error-handler.maxAttempts}")
    private int errorHandlerMaxAttempts;

    @Bean
    public ProducerFactory<String, TBase> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ThriftSerializer.class);
        sslConfigure(configProps);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String, Event> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ClaimManagementEventDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        sslConfigure(props);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    @SuppressWarnings("LineLength")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Event>> kafkaListenerContainerFactory(
            ConsumerFactory<String, Event> consumerFactory,
            ErrorHandler errorHandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckOnError(false);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setErrorHandler(errorHandler);
        factory.setConcurrency(concurrency);
        return factory;
    }

    @Bean
    public ErrorHandler kafkaErrorHandler() {
        return new SeekToCurrentWithSleepErrorHandler(errorHandlerSleepTimeSeconds, errorHandlerMaxAttempts);
    }

    private void sslConfigure(Map<String, Object> configProps) {
        if (kafkaSslEnable) {
            configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
            configProps.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, new File(serverStoreCertPath).getAbsolutePath());
            configProps.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, serverStorePassword);
            configProps.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, PKCS_12);
            configProps.put(SslConfigs.SSL_TRUSTSTORE_TYPE_CONFIG, PKCS_12);
            configProps.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, new File(clientStoreCertPath).getAbsolutePath());
            configProps.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keyStorePassword);
            configProps.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
        }
    }

    @Bean
    public KafkaTemplate<String, TBase> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
