package com.rbkmoney.cm.service;

import com.rbkmoney.cm.AbstractWithCommittersIntegrationTest;
import com.rbkmoney.cm.exception.InvalidRevisionException;
import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.model.ClaimModel;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.apache.thrift.TBase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.AsyncResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.rbkmoney.cm.model.ClaimStatusEnum.accepted;
import static com.rbkmoney.cm.model.ClaimStatusEnum.pending;
import static com.rbkmoney.cm.util.ServiceUtils.createClaim;
import static com.rbkmoney.cm.util.ServiceUtils.runService;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class ClaimCommitterServiceTest extends AbstractWithCommittersIntegrationTest {

    @Autowired
    private ClaimCommitterService claimCommitterService;

    @Autowired
    private ConversionWrapperService conversionWrapperService;

    @Autowired
    private ClaimManagementService claimManagementService;

    private ClaimManagementSrv.Iface client;

    @MockBean
    private KafkaTemplate<String, TBase> kafkaTemplate;

    @Before
    public void setUp() throws URISyntaxException {
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

        Mockito.when(kafkaTemplate.send(any(), any(), any())).thenReturn(new AsyncResult<>(null));
    }

    @Test
    public void testCommitPlan() {
        startSimpleCommitter();
        ClaimModel claimModel = createClaimWithPendingAcceptance("party_id");

        claimCommitterService.doCommitClaim(claimModel.getPartyId(), claimModel.getId(), claimModel.getRevision());
        ClaimModel newClaimModel = claimManagementService.getClaim(claimModel.getPartyId(), claimModel.getId());
        assertEquals(accepted, newClaimModel.getClaimStatus().getClaimStatusEnum());
        assertNotEquals(claimModel.getRevision(), newClaimModel.getRevision());
    }

    @Test
    public void testCommitPlanWhenRevisionIsInvalid() {
        startSimpleCommitter();

        ClaimModel claimModel = createClaimWithPendingAcceptance("party_id");
        try {
            claimCommitterService
                    .doCommitClaim(claimModel.getPartyId(), claimModel.getId(), claimModel.getRevision() + 1);
            fail();
        } catch (InvalidRevisionException ex) {
            //do nothing
        }
        ClaimModel newClaimModel = claimManagementService.getClaim(claimModel.getPartyId(), claimModel.getId());
        assertEquals(claimModel.getClaimStatus(), newClaimModel.getClaimStatus());
        assertEquals(claimModel.getRevision(), newClaimModel.getRevision());
    }

    @Test
    public void testInvalidChangeset() {
        startCommitterWithInvalidChangeset();

        ClaimModel claimModel = createClaimWithPendingAcceptance("party_id");

        claimCommitterService.doCommitClaim(claimModel.getPartyId(), claimModel.getId(), claimModel.getRevision());
        ClaimModel newClaimModel = claimManagementService.getClaim(claimModel.getPartyId(), claimModel.getId());
        assertEquals(pending, newClaimModel.getClaimStatus().getClaimStatusEnum());
        assertNotEquals(claimModel.getRevision(), newClaimModel.getRevision());
    }

    @Test
    public void testErrorWhenAccept() {
        startCommitterWithUnexpectedErrorWhenAccept();

        ClaimModel claimModel = createClaimWithPendingAcceptance("party_id");

        try {
            claimCommitterService.doCommitClaim(claimModel.getPartyId(), claimModel.getId(), claimModel.getRevision());
            fail();
        } catch (RuntimeException ex) {
            // do nothing
        }
        ClaimModel newClaimModel = claimManagementService.getClaim(claimModel.getPartyId(), claimModel.getId());
        assertEquals(claimModel.getClaimStatus(), newClaimModel.getClaimStatus());
        assertEquals(claimModel.getRevision(), newClaimModel.getRevision());
    }

    @Test
    public void testUnexpectedErrorWhenCommit() {
        startCommitterWithUnexpectedErrorWhenCommit();

        ClaimModel claimModel = createClaimWithPendingAcceptance("party_id");

        try {
            claimCommitterService.doCommitClaim(claimModel.getPartyId(), claimModel.getId(), claimModel.getRevision());
            fail();
        } catch (RuntimeException ex) {
            // do nothing
        }
        ClaimModel newClaimModel = claimManagementService.getClaim(claimModel.getPartyId(), claimModel.getId());
        assertEquals(claimModel.getClaimStatus(), newClaimModel.getClaimStatus());
        assertEquals(claimModel.getRevision(), newClaimModel.getRevision());
    }

    private ClaimModel createClaimWithPendingAcceptance(String partyId) {
        Claim claim = createClaim(client, conversionWrapperService, partyId, 5);
        runService(() -> client.acceptClaim(partyId, claim.getId(), 0));
        return claimManagementService.getClaim(claim.getPartyId(), claim.getId());
    }


}
