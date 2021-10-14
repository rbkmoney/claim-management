package com.rbkmoney.cm.listener;

import com.rbkmoney.cm.service.ClaimCommitterService;
import com.rbkmoney.damsel.claim_management.ClaimStatusChanged;
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

    private final ClaimCommitterService claimCommitterService;

    @KafkaListener(topics = "${kafka.topics.claim-event-sink.id}", containerFactory = "kafkaListenerContainerFactory")
    public void handle(Event event,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header(KafkaHeaders.OFFSET) int offsets,
                       Acknowledgment ack) {
        if (event.getChange().isSetStatusChanged()) {
            ClaimStatusChanged claimStatusChanged = event.getChange().getStatusChanged();
            if (claimStatusChanged.getStatus().isSetPendingAcceptance()) {
                log.info("Found event in 'pending_acceptance' status, event='{}'", event);
                try {
                    claimCommitterService.doCommitClaim(
                            claimStatusChanged.getPartyId(),
                            claimStatusChanged.getId(),
                            claimStatusChanged.getRevision()
                    );
                    ack.acknowledge();
                    log.info("Event have been processed, event='{}'", event);
                } catch (Exception ex) {
                    log.error("Failed to process event, event='{}'", event, ex);
                    throw ex;
                }
            }
        }
    }

}
