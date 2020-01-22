package com.rbkmoney.cm.util;

import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.cm.service.ConversionWrapperService;
import com.rbkmoney.damsel.claim_management.Claim;
import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.woody.api.flow.WFlow;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceUtils {

    public static List<Claim> createClaims(ClaimManagementSrv.Iface client, ConversionWrapperService conversionWrapperService, String partyId, int claimCount, int modificationCountPerClaim) {
        return IntStream.rangeClosed(1, claimCount)
                .mapToObj(value -> createClaim(client, conversionWrapperService, partyId, modificationCountPerClaim))
                .collect(Collectors.toList());
    }

    public static Claim createClaim(ClaimManagementSrv.Iface client, ConversionWrapperService conversionWrapperService, String partyId, int modificationCount) {
        List<Modification> modification = generateModifications(conversionWrapperService, modificationCount);

        return createClaim(client, partyId, modification);
    }

    public static List<Modification> generateModifications(ConversionWrapperService conversionWrapperService, int modificationCount) {
        return generateModifications(conversionWrapperService, () -> MockUtil.generateTBaseList(Modification.class, modificationCount));
    }

    public static List<Modification> generateModifications(ConversionWrapperService conversionWrapperService, Supplier<List<Modification>> supplier) {
        boolean flag = true;

        List<Modification> modification = new ArrayList<>();

        while (flag) {
            flag = false;

            modification = supplier.get();

            List<ModificationModel> modificationModels = conversionWrapperService.convertModifications(modification);

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

    public static Claim createClaim(ClaimManagementSrv.Iface client, String partyId, List<Modification> modification) {
        return callService(() -> client.createClaim(partyId, modification));
    }

    public static <T> T callService(Callable<T> callable) {
        return callService(callable, buildDefaultUserInfo());
    }

    public static void runService(ThrowableRunnable runnable) {
        runService(runnable, buildDefaultUserInfo());
    }

    @SneakyThrows
    public static <T> T callService(Callable<T> callable, UserInfoModel userInfoModel) {
        return new WFlow().createServiceFork(
                () -> {
                    ContextUtil.addUserInfoToContext(userInfoModel);
                    return callable.call();
                }).call();
    }

    @SneakyThrows
    public static void runService(ThrowableRunnable runnable, UserInfoModel userInfoModel) {
        new WFlow().createServiceFork(
                () -> {
                    ContextUtil.addUserInfoToContext(userInfoModel);
                    runnable.run();
                    return null;
                }).call();
    }

    public static UserInfoModel buildDefaultUserInfo() {
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
