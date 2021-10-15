package com.rbkmoney.cm.listener;

import com.rbkmoney.cm.handler.event.ClaimEventSinkManager;
import com.rbkmoney.damsel.claim_management.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

@Slf4j
@RequiredArgsConstructor
public class ClaimEventSinkListener {

    private final ClaimEventSinkManager claimEventSinkManager;

    @KafkaListener(topics = "${kafka.topics.claim-event-sink.id}", containerFactory = "kafkaListenerContainerFactory")
    public void handle(
            Event event,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offsets,
            Acknowledgment ack
    ) {
        log.info("Handle event={}; partition={}; offsets={}", event, partition, offsets);
        claimEventSinkManager.handleEvent(event, ack);
        log.info("Event have been processed, event={}; partition={}; offsets={}", event, partition, offsets);
    }

}
