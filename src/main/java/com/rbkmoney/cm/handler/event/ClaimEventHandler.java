package com.rbkmoney.cm.handler.event;

import com.rbkmoney.damsel.claim_management.Event;

public interface ClaimEventHandler {

    void handle(Event event);

    boolean isHandle(Event event);

}
