package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.AbstractKafkaIntegrationTest;
import com.rbkmoney.cm.util.MockUtil;
import com.rbkmoney.cm.util.ServiceUtils;
import com.rbkmoney.cm.util.WebHookDeserializer;
import com.rbkmoney.damsel.claim_management.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ClaimHandlerEventSinkDecoratorTest extends AbstractKafkaIntegrationTest {

    public static final String PARTY_ID_2 = "party_id_2";
    @Autowired
    ClaimManagementSrv.Iface iface;

    @Test
    public void testCreateClaimAndGet() throws InterruptedException {
        Claim claim = ServiceUtils.createClaim(iface, PARTY_ID_2, 5);

        Consumer<String, Event> consumer = createConsumer(WebHookDeserializer.class);
        consumer.subscribe(List.of(Initializer.CLAIM_EVENT_SINK));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(String.valueOf(claim.getId()), event.key());
                    assertEquals(claim.getId(), value.getChange().getCreated().getId());
                    assertEquals(5, value.getChange().getCreated().getChangesetSize());
                });

        ServiceUtils.runService(() -> iface.updateClaim(PARTY_ID_2, claim.getId(), 0, MockUtil.generateTBaseList(Modification.claim_modification(new ClaimModification()), 5)));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(String.valueOf(claim.getId()), event.key());
                    assertEquals(claim.getId(), value.getChange().getUpdated().getId());
                    assertEquals(5, value.getChange().getUpdated().getChangesetSize());
                });

        ServiceUtils.runService(() -> iface.requestClaimReview(PARTY_ID_2, claim.getId(), 1));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(String.valueOf(claim.getId()), event.key());
                    assertEquals(claim.getId(), value.getChange().getStatusChanged().getId());
                    assertTrue(value.getChange().getStatusChanged().getStatus().isSetReview());
                });

        ServiceUtils.runService(() -> iface.requestClaimChanges(PARTY_ID_2, claim.getId(), 2));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(String.valueOf(claim.getId()), event.key());
                    assertEquals(claim.getId(), value.getChange().getStatusChanged().getId());
                    assertTrue(value.getChange().getStatusChanged().getStatus().isSetPending());
                });

        consumer.close();
    }

}