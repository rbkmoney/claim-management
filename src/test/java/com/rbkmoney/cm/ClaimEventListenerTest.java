package com.rbkmoney.cm;

import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.cm.model.ClaimStatusEnum;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.service.ClaimManagementService;
import com.rbkmoney.cm.service.ConversionWrapperService;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.rbkmoney.cm.util.ServiceUtils.createClaim;
import static com.rbkmoney.cm.util.ServiceUtils.runService;

public class ClaimEventListenerTest extends AbstractKafkaIntegrationTest {

    @Autowired
    private ClaimManagementService claimManagementService;

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ConversionWrapperService conversionWrapperService;

    private ClaimManagementSrv.Iface client;

    @Before
    public void setUp() throws Exception {
        client = new THSpawnClientBuilder()
                .withAddress(new URI("http://localhost:" + port + "/v1/cm"))
                .withNetworkTimeout(-1)
                .withMetaExtensions(
                        List.of(
                                UserIdentityIdExtensionKit.INSTANCE,
                                UserIdentityUsernameExtensionKit.INSTANCE,
                                UserIdentityEmailExtensionKit.INSTANCE,
                                UserIdentityRealmExtensionKit.INSTANCE
                        )
                )
                .build(ClaimManagementSrv.Iface.class);
        claimRepository.deleteAll();
    }

    @Test
    public void testClaimStatusChangeHandler() throws InterruptedException, ExecutionException {
        // Given
        final String partyId = UUID.randomUUID().toString();
        Event event = new Event();
        event.setOccuredAt(TypeUtil.temporalToString(LocalDateTime.now()));
        Change change = new Change();
        event.setChange(change);
        ClaimStatusChanged claimStatusChanged = new ClaimStatusChanged();
        change.setStatusChanged(claimStatusChanged);
        claimStatusChanged.setId(1);
        claimStatusChanged.setPartyId(partyId);
        claimStatusChanged.setStatus(ClaimStatus.pending_acceptance(new ClaimPendingAcceptance()));
        claimStatusChanged.setRevision(0);
        claimStatusChanged.setUpdatedAt(TypeUtil.temporalToString(LocalDateTime.now()));

        // When
        Claim pendingClaim = createClaim(client, conversionWrapperService, partyId, 5);
        runService(() -> client.acceptClaim(partyId, pendingClaim.getId(), 0));
        Producer<String, Event> producer = createProducer();
        ProducerRecord<String, Event> producerRecord = new ProducerRecord<>(eventSinkTopic, partyId, event);
        producer.send(producerRecord).get();

        // Then
        Awaitility.await().atMost(60, TimeUnit.SECONDS).pollDelay(Durations.ONE_SECOND).until(() -> {
            ClaimModel claim = claimManagementService.getClaim(partyId, 1);
            return claim != null && claim.getClaimStatus().getClaimStatusEnum() == ClaimStatusEnum.accepted;
        });
    }

}
