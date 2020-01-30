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
                String partyId = claimStatusChanged.getPartyId();
                long claimId = claimStatusChanged.getId();
                int revision = claimStatusChanged.getRevision();
                claimCommitterService.doCommitClaim(partyId, claimId, revision);
            }
        }
        ack.acknowledge();
    }

}
