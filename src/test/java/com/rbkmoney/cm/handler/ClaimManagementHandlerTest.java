package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.AbstractIntegrationTest;
import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.cm.util.ContextUtil;
import com.rbkmoney.cm.util.MockUtil;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.woody.api.flow.WFlow;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.SneakyThrows;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;


public class ClaimManagementHandlerTest extends AbstractIntegrationTest {

    private ClaimManagementSrv.Iface client;

    @Before
    public void setUp() throws URISyntaxException {
        client = new THSpawnClientBuilder()
                .withAddress(new URI("http://localhost:" + port + "/v1/cm"))
                .withNetworkTimeout(0)
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
        Claim claim = createClaim("party_id", MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5));
        assertEquals(claim, callService(() -> client.getClaim("party_id", claim.getId())));
        runService(() -> client.updateClaim("party_id", claim.getId(), 0, MockUtil.generateTBaseList(Modification.claim_modfication(new ClaimModification()), 5)));
    }

    @Repeat(200)
    @Test(expected = ChangesetConflict.class)
    public void testTryingToGetConflictWhenUpdate() {
        List<Modification> modifications = MockUtil.generateTBaseList(Modification.class, 1);
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
    public void testSearchClaims() {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId("claim_search_party_id");
        claimSearchQuery.setLimit(20);

        List<Claim> claims = createClaims(claimSearchQuery.getPartyId(), claimSearchQuery.getLimit(), 5);
        assertEquals(claims.size(), callService(() -> client.searchClaims(claimSearchQuery)).size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.pending(new ClaimPending())));
        assertEquals(claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(), callService(() -> client.searchClaims(claimSearchQuery)).size());

        claimSearchQuery.setStatuses(Arrays.asList(ClaimStatus.review(new ClaimReview()), ClaimStatus.accepted(new ClaimAccepted())));
        assertEquals(claims.stream().filter(claim -> claimSearchQuery.getStatuses().contains(claim.getStatus())).count(), callService(() -> client.searchClaims(claimSearchQuery)).size());
    }

    @Test
    public void setAndGetMetadata() {
        List<Modification> modification = MockUtil.generateTBaseList(Modification.class, 5);
        Claim claim = callService(() -> client.createClaim("party_id", modification));

        Value value = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetaData("party_id", claim.getId(), "key", value));
        Value value2 = MockUtil.generateTBase(Value.class);
        runService(() -> client.setMetaData("party_id", claim.getId(), "key", value2));
        assertEquals(value2, callService(() -> client.getMetaData("party_id", claim.getId(), "key")));
    }

    private List<Claim> createClaims(String partyId, int claimCount, int modificationCountPerClaim) {
        return IntStream.rangeClosed(1, claimCount)
                .mapToObj(value -> createClaim(partyId, modificationCountPerClaim))
                .collect(Collectors.toList());
    }

    private Claim createClaim(String partyId, int modificationCount) {
        List<Modification> modification = MockUtil.generateTBaseList(Modification.class, 5);
        return createClaim(partyId, modification);
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
