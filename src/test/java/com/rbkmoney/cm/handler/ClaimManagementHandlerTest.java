package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.AbstractIntegrationTest;
import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.service.ConversionWrapperService;
import com.rbkmoney.cm.util.MockUtil;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.woody.api.flow.error.WUndefinedResultException;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.test.annotation.Repeat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.rbkmoney.cm.util.ServiceUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ClaimManagementHandlerTest extends AbstractIntegrationTest {

    public static final String EMPTY_STRING = "";
    @Autowired
    private ConversionWrapperService conversionWrapperService;

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
    public void testCreateClaimAndGet() {
        Claim claim = createClaim(client, conversionWrapperService, "party_id", 5);
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
    }

    @Test
    public void testCreateClaimAndUpdate() {
        Claim claim = createClaim(client, "party_id", generateModifications(conversionWrapperService, () -> MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5)));
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
        runService(() -> client.updateClaim("party_id", claim.getId(), 0, generateModifications(conversionWrapperService, () -> MockUtil.generateTBaseList(Modification.claim_modification(new ClaimModification()), 5))));
    }

    @Test(expected = InvalidChangeset.class)
    public void testTryingToInvalidChansetWhenCreateClaimAndGet() {
        Modification modification = MockUtil.generateTBase(Modification.class);
        createClaim(client, "party_id", List.of(modification, modification));
    }

    @Repeat(5)
    @Test(expected = ChangesetConflict.class)
    public void testTryingToGetConflictWhenUpdate() {
        List<Modification> modifications = generateModifications(conversionWrapperService, 1);
        Claim claim = createClaim(client, "party_id", modifications);
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
        runService(() -> client.updateClaim("party_id", claim.getId(), 0, modifications));
    }

    @Test(expected = ClaimNotFound.class)
    public void testClaimNotFound() {
        callService(() -> client.getClaim("party_id", -1));
    }

    @Test(expected = ClaimNotFound.class)
    public void testGetClaimWithWrongParty() {
        Claim claim = createClaim(client, conversionWrapperService, "party_id", 5);
        assertEquals(claim, callService(() -> client.getClaim("wrong_party", claim.getId())));
    }

    @Test(expected = WUndefinedResultException.class)
    public void testTryingToEmptyDocumentID() {
        Claim claim = createClaim(client, "claim_id", generateModifications(conversionWrapperService, () -> {
            return MockUtil.generateTBaseList(Modification.claim_modification(createClaimModification()), 1);
        }));
    }

    @NotNull
    private ClaimModification createClaimModification() {
        ClaimModification value = new ClaimModification();
        DocumentModificationUnit documentModificationUnit = new DocumentModificationUnit();
        documentModificationUnit.setId(EMPTY_STRING);
        value.setDocumentModification(documentModificationUnit);
        return value;
    }

    @Test
    public void testCreateClaimAndAccept() {
        Claim claim = createClaim(client, conversionWrapperService, "party_id", 5);
        runService(() -> client.acceptClaim("party_id", claim.getId(), 0));
        assertEquals(ClaimStatus.pending_acceptance(new ClaimPendingAcceptance()), callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testCreateClaimAndDeny() {
        Claim claim = createClaim(client, conversionWrapperService, "party_id", 5);
        runService(() -> client.denyClaim("party_id", claim.getId(), 0, "kek"));
        assertEquals(ClaimStatus.denied(new ClaimDenied().setReason("kek")), callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testCreateRequestReviewAndRequestChanges() {
        Claim claim = createClaim(client, conversionWrapperService, "party_id", 5);
        runService(() -> client.requestClaimReview("party_id", claim.getId(), 0));
        assertEquals(ClaimStatus.review(new ClaimReview()), callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
        runService(() -> client.requestClaimChanges("party_id", claim.getId(), 1));
        assertEquals(ClaimStatus.pending(new ClaimPending()), callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testSearchClaims() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id");
        claimSearchQuery.setLimit(20);

        List<Claim> claims = createClaims(client, conversionWrapperService, claimSearchQuery.getPartyId(), claimSearchQuery.getLimit(), 5);
        assertEquals(claims.size(), callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.pending(new ClaimPending())));
        assertEquals(claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(), callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.review(new ClaimReview()), ClaimStatus.accepted(new ClaimAccepted())));
        assertEquals(claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(), callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());
    }

    @Test
    public void testSearchClaimsByClaimId() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id_and_claim_id");
        claimSearchQuery.setLimit(1);

        Claim claim = createClaims(client, conversionWrapperService, claimSearchQuery.getPartyId(), claimSearchQuery.getLimit(), 5).get(0);
        claimSearchQuery.setClaimId(claim.getId());

        assertEquals(claim, callService(() -> client.searchClaims(claimSearchQuery).getResult().get(0)));
    }

    @Test
    public void testSearchClaimsWithContinuation() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id_with_continuation");
        claimSearchQuery.setLimit(1);

        createClaims(client, conversionWrapperService, claimSearchQuery.getPartyId(), 20, 1);

        List<Claim> searchedClaimList = new ArrayList<>();
        ClaimSearchResponse claimSearchResponse;
        do {
            claimSearchResponse = callService(() -> client.searchClaims(claimSearchQuery));
            assertEquals(claimSearchQuery.getLimit(), claimSearchResponse.getResult().size());
            searchedClaimList.addAll(claimSearchResponse.getResult());
            claimSearchQuery.setContinuationToken(claimSearchResponse.getContinuationToken());
        } while (claimSearchResponse.isSetContinuationToken());
        assertEquals(callService(() -> client.searchClaims(claimSearchQuery.setLimit(20))).getResult(), searchedClaimList);
    }

    @Test
    public void setAndGetMetadata() {
        Claim claim = callService(() -> client.createClaim("party_id", generateModifications(conversionWrapperService, 5)));

        Value value = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetadata("party_id", claim.getId(), "key", value));
        Value value2 = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetadata("party_id", claim.getId(), "key", value2));
        assertEquals(value2, callService(() -> client.getMetadata("party_id", claim.getId(), "key")));
    }

    @Test
    public void setAndGetMetadataError() throws TException {
        Claim claim = createClaim(client, "party_id", generateModifications(conversionWrapperService, () -> MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5)));
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
        try {
            Mockito.when(kafkaTemplate.send(any(), any())).thenThrow(new RuntimeException());
            runService(() -> client.updateClaim("party_id", claim.getId(), 0, generateModifications(conversionWrapperService, () -> MockUtil.generateTBaseList(Modification.claim_modification(new ClaimModification()), 5))));
        } catch (Exception e) {
            assertEquals(5, callService(() -> client.getClaim("party_id", claim.getId())).getChangesetSize());
            Mockito.verify(kafkaTemplate, Mockito.times(4)).send(any(), any());
        }
    }

    @Test(expected = MetadataKeyNotFound.class)
    public void removeMetadataTest() {
        Claim claim = createClaim(client, conversionWrapperService, "party_id", 5);
        Value value = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetadata("party_id", claim.getId(), "key", value));
        assertEquals(value, callService(() -> client.getMetadata("party_id", claim.getId(), "key")));
        runService(() -> client.removeMetadata("party_id", claim.getId(), "key"));
        callService(() -> client.getMetadata("party_id", claim.getId(), "key"));
    }
}
