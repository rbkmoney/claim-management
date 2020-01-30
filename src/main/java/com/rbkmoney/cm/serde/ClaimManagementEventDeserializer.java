package com.rbkmoney.cm.serde;

import com.rbkmoney.damsel.claim_management.Event;
import com.rbkmoney.kafka.common.serialization.AbstractThriftDeserializer;

public class ClaimManagementEventDeserializer extends AbstractThriftDeserializer<Event> {

    @Override
    public Event deserialize(String topic, byte[] data) {
        return this.deserialize(data, new Event());
    }

}
