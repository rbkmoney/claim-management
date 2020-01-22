package com.rbkmoney.cm.util;

import com.rbkmoney.cm.handler.ClaimManagementHandlerTest;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.damsel.claim_management.PartyModification;
import com.rbkmoney.woody.api.flow.WFlow;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.Callable;

public class ServiceUtils {

    public static Claim createClaim(ClaimManagementSrv.Iface client, String partyId, int modificationCount) {
        List<Modification> modification = MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5);
        return createClaim(client, partyId, modification);
    }

    public static Claim createClaim(ClaimManagementSrv.Iface client, String partyId, List<Modification> modification) {
        return callService(() -> client.createClaim(partyId, modification));
    }

    public static <T> T callService(Callable<T> callable) {
        return callService(callable, buildDefaultUserInfo());
    }

    @SneakyThrows
    private static <T> T callService(Callable<T> callable, UserInfoModel userInfoModel) {
        return new WFlow().createServiceFork(
                () -> {
                    ContextUtil.addUserInfoToContext(userInfoModel);
                    return callable.call();
                }).call();
    }

    private static UserInfoModel buildDefaultUserInfo() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("100");
        userInfoModel.setUsername("user_id");
        userInfoModel.setEmail("user_id@users");
        userInfoModel.setType(UserTypeEnum.external);
        return userInfoModel;
    }

    public static void runService(ClaimManagementHandlerTest.ThrowableRunnable runnable) {
        runService(runnable, buildDefaultUserInfo());
    }

    @SneakyThrows
    private static void runService(ClaimManagementHandlerTest.ThrowableRunnable runnable, UserInfoModel userInfoModel) {
        new WFlow().createServiceFork(
                () -> {
                    ContextUtil.addUserInfoToContext(userInfoModel);
                    runnable.run();
                    return null;
                }).call();
    }

}
