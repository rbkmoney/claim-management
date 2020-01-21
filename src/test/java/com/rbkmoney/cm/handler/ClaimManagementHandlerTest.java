package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.AbstractIntegrationTest;
import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.cm.util.ContextUtil;
import com.rbkmoney.cm.util.MockUtil;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.woody.api.flow.WFlow;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.annotation.Repeat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ClaimManagementHandlerTest extends AbstractIntegrationTest {

    @Autowired
    private ConversionService conversionService;

    private ClaimManagementSrv.Iface client;

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
    }

    @Test
    public void testCreateClaimAndGet() {
        Claim claim = createClaim("party_id", 5);
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
    }

    @Test
    public void testCreateClaimAndUpdate() {
        Claim claim = createClaim("party_id", generateModifications(() -> MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5)));
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
        runService(() -> client.updateClaim("party_id", claim.getId(), 0, generateModifications(() -> MockUtil.generateTBaseList(Modification.claim_modification(new ClaimModification()), 5))));
    }

    @Repeat(5)
    @Test(expected = ChangesetConflict.class)
    public void testTryingToGetConflictWhenUpdate() {
        List<Modification> modifications = generateModifications(1);
        Claim claim = createClaim("party_id", modifications);
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
        runService(() -> client.updateClaim("party_id", claim.getId(), 0, modifications));
    }

    @Test(expected = ClaimNotFound.class)
    public void testClaimNotFound() {
        callService(() -> client.getClaim("party_id", -1));
    }

    @Test(expected = ClaimNotFound.class)
    public void testGetClaimWithWrongParty() {
        Claim claim = createClaim("party_id", 5);
        assertEquals(claim, callService(() -> client.getClaim("wrong_party", claim.getId())));
    }

    @Test
    public void testCreateClaimAndAccept() {
        Claim claim = createClaim("party_id", 5);
        runService(() -> client.acceptClaim("party_id", claim.getId(), 0));
        assertEquals(ClaimStatus.pending_acceptance(new ClaimPendingAcceptance()), callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testCreateClaimAndDeny() {
        Claim claim = createClaim("party_id", 5);
        runService(() -> client.denyClaim("party_id", claim.getId(), 0, "kek"));
        assertEquals(ClaimStatus.denied(new ClaimDenied().setReason("kek")), callService(() -> client.getClaim("party_id", claim.getId())).getStatus());
    }

    @Test
    public void testCreateRequestReviewAndRequestChanges() {
        Claim claim = createClaim("party_id", 5);
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

        List<Claim> claims = createClaims(claimSearchQuery.getPartyId(), claimSearchQuery.getLimit(), 5);
        assertEquals(claims.size(), callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.pending(new ClaimPending())));
        assertEquals(claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(), callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.review(new ClaimReview()), ClaimStatus.accepted(new ClaimAccepted())));
        assertEquals(claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(), callService(() -> client.searchClaims(claimSearchQuery)).getResult().size());
    }

    @Test
    public void testSearchClaimsByClaimId() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setLimit(1);

        Claim claim = createClaims("claim_search_party_id_and_claim_id", claimSearchQuery.getLimit(), 5).get(0);
        claimSearchQuery.setClaimId(claim.getId());

        assertEquals(claim, callService(() -> client.searchClaims(claimSearchQuery).getResult().get(0)));
    }

    @Test
    public void testSearchClaimsWithContinuation() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id_with_continuation");
        claimSearchQuery.setLimit(1);

        createClaims(claimSearchQuery.getPartyId(), 20, 1);

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
        Claim claim = callService(() -> client.createClaim("party_id", generateModifications(5)));

        Value value = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetadata("party_id", claim.getId(), "key", value));
        Value value2 = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetadata("party_id", claim.getId(), "key", value2));
        assertEquals(value2, callService(() -> client.getMetadata("party_id", claim.getId(), "key")));
    }

    @Test(expected = MetadataKeyNotFound.class)
    public void removeMetadataTest() {
        Claim claim = createClaim("party_id", 5);
        Value value = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetadata("party_id", claim.getId(), "key", value));
        assertEquals(value, callService(() -> client.getMetadata("party_id", claim.getId(), "key")));
        runService(() -> client.removeMetadata("party_id", claim.getId(), "key"));
        callService(() -> client.getMetadata("party_id", claim.getId(), "key"));
    }

    private List<Claim> createClaims(String partyId, int claimCount, int modificationCountPerClaim) {
        return IntStream.rangeClosed(1, claimCount)
                .mapToObj(value -> createClaim(partyId, modificationCountPerClaim))
                .collect(Collectors.toList());
    }

    private Claim createClaim(String partyId, int modificationCount) {
        List<Modification> modification = generateModifications(modificationCount);

        return createClaim(partyId, modification);
    }

    private List<Modification> generateModifications(int modificationCount) {
        return generateModifications(() -> MockUtil.generateTBaseList(Modification.class, modificationCount));
    }

    private List<Modification> generateModifications(Supplier<List<Modification>> supplier) {
        boolean flag = true;

        List<Modification> modification = new ArrayList<>();

        while (flag) {
            flag = false;

            modification = supplier.get();

            List<ModificationModel> modificationModels = modification.stream()
                    .map(change -> conversionService.convert(change, ModificationModel.class))
                    .collect(Collectors.toList());

            for (int i = 0; i < modificationModels.size() - 1; i++) {
                for (int j = i + 1; j < modificationModels.size(); j++) {
                    if (modificationModels.get(i).canEqual(modificationModels.get(j))) {
                        flag = true;
                    }
                }
            }

            if (flag) {
                modification = supplier.get();
            }
        }
        return modification;
    }

    private Claim createClaim(String partyId, List<Modification> modification) {
        return callService(() -> client.createClaim(partyId, modification));
    }

    private <T> T callService(Callable<T> callable) {
        return callService(callable, buildDefaultUserInfo());
    }

    private void runService(ThrowableRunnable runnable) {
        runService(runnable, buildDefaultUserInfo());
    }

    @SneakyThrows
    private <T> T callService(Callable<T> callable, UserInfoModel userInfoModel) {
        return new WFlow().createServiceFork(
                () -> {
                    ContextUtil.addUserInfoToContext(userInfoModel);
                    return callable.call();
                }).call();
    }

    @SneakyThrows
    private void runService(ThrowableRunnable runnable, UserInfoModel userInfoModel) {
        new WFlow().createServiceFork(
                () -> {
                    ContextUtil.addUserInfoToContext(userInfoModel);
                    runnable.run();
                    return null;
                }).call();
    }

    private UserInfoModel buildDefaultUserInfo() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("100");
        userInfoModel.setUsername("user_id");
        userInfoModel.setEmail("user_id@users");
        userInfoModel.setType(UserTypeEnum.external);
        return userInfoModel;
    }

    public interface ThrowableRunnable {

        void run() throws Exception;

    }
}
