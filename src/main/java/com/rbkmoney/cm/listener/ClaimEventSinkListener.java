package com.rbkmoney.cm.listener;

import com.rbkmoney.cm.service.ClaimCommitterService;
import com.rbkmoney.damsel.claim_management.ClaimStatusChanged;
import com.rbkmoney.damsel.claim_management.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@Slf4j
@RequiredArgsConstructor
public class ClaimEventSinkListener {

    private final ClaimCommitterService claimCommitterService;

    @KafkaListener(topics = "${kafka.topics.claim-event-sink.id}", containerFactory = "kafkaListenerContainerFactory")
    public void handle(Event event, Acknowledgment ack) {
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
                    log.info("Event have been processed, event='{}'", event);
                } catch (RuntimeException ex) {
                    log.error("Failed to process event, event='{}'", event, ex);
                }
            }
        }
        ack.acknowledge();
    }

}
