package com.rbkmoney.cm.handler;

import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rbkmoney.cm.util.ServiceUtils.callService;

public class CreateCashRegisterModificationUnitTest {

    private ClaimManagementSrv.Iface client;

    @Before
    public void setUp() throws URISyntaxException {
        client = new THSpawnClientBuilder()
                .withAddress(new URI("http://claim-management:8022/v1/cm"))
                .withNetworkTimeout(5000)
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
    @Ignore
    public void createCashRegisterModificationUnitClaim() {
        Map<String, String> params = new HashMap<>();
        params.put("url", "https://check.business.ru/api-rbkmoney/v4/");
        params.put("login", "910201188674");
        params.put("pass", "g1s84ng430dm3df94j3d");
        params.put("group", "1");
        params.put("company_inn", "910201188674");
        params.put("tax_id", "none");
        params.put("tax_mode", "usn_income");
        params.put("company_name", "Желонкина Людмила Леонидовна");
        params.put("company_email", "feo@fidele.su");
        params.put("payment_method", "full_payment");
        params.put("payment_object", "commodity");
        params.put("company_address", "298100, Крым Респ, г. Феодосия, ул.Ленина, 11");

        Claim cashRegisterClaim = callService(
                () -> client.createClaim(
                        "bf01f641-0523-4cb5-9caa-61d96678c249",
                        List.of(
                                Modification.party_modification(
                                        PartyModification.shop_modification(
                                                new ShopModificationUnit(
                                                        "22d0a254-885d-47de-8b72-de483d0d990c",
                                                        ShopModification.cash_register_modification_unit(
                                                                new CashRegisterModificationUnit(
                                                                        "1",
                                                                        CashRegisterModification.creation(
                                                                                new CashRegisterParams(
                                                                                        1,
                                                                                        params
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ),
                buildUserInfo()
        );
        System.out.println(cashRegisterClaim);

        System.out.println(callService(() -> client.getClaim(cashRegisterClaim.getPartyId(), cashRegisterClaim.getId())));
    }

    private UserInfoModel buildUserInfo() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("77a47446-8bd0-4cc4-a4c6-a60918ebf999");
        userInfoModel.setUsername("p.popov");
        userInfoModel.setEmail("p.popov@rbkmoney.com");
        userInfoModel.setType(UserTypeEnum.internal);
        return userInfoModel;
    }

}
