package com.rbkmoney.cm.handler.event;

import com.rbkmoney.cm.service.ClaimCommitterService;
import com.rbkmoney.damsel.claim_management.ClaimStatusChanged;
import com.rbkmoney.damsel.claim_management.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimStatusChangeEventHandler implements ClaimEventHandler {

    private final ClaimCommitterService claimCommitterService;

    @Transactional
    public void handle(Event event) {
        ClaimStatusChanged claimStatusChanged = event.getChange().getStatusChanged();
        if (claimStatusChanged.getStatus().isSetPendingAcceptance()) {
            log.info("Commit claim in 'pending_acceptance' status, event='{}'", event);
            claimCommitterService.doCommitClaim(
                    claimStatusChanged.getPartyId(),
                    claimStatusChanged.getId(),
                    claimStatusChanged.getRevision()
            );
        }
    }

    @Override
    public boolean isHandle(Event event) {
        return event.getChange().isSetStatusChanged();
    }

}
