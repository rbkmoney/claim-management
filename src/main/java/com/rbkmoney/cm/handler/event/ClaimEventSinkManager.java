package com.rbkmoney.cm.handler.event;

import com.rbkmoney.damsel.claim_management.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimEventSinkManager {

    private final List<ClaimEventHandler> claimEventHandlerList;

    @Transactional
    public void handleEvent(Event event, Acknowledgment ack) {
        try {
            for (ClaimEventHandler claimEventHandler : claimEventHandlerList) {
                if (claimEventHandler.isHandle(event)) {
                    claimEventHandler.handle(event);
                }
            }
            ack.acknowledge();
        } catch (Exception ex) {
            log.error("Failed to process event, event='{}'", event, ex);
            throw ex;
        }
    }

}
