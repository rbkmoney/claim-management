package com.rbkmoney.cm.service;

import com.rbkmoney.cm.AbstractIntegrationTest;
import com.rbkmoney.cm.model.*;
import com.rbkmoney.cm.model.shop.ShopLocationModificationModel;
import com.rbkmoney.cm.repository.ClaimRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.ConstraintViolationException;
import java.util.List;

public class ClaimRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private ClaimRepository claimRepository;

    @Test(expected = ConstraintViolationException.class)
    public void testRequireNullSave() throws Throwable {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));

        ShopLocationModificationModel shopLocationModificationModel = new ShopLocationModificationModel();
        shopLocationModificationModel.setShopId("shop_id");

        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.qwe");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);
        shopLocationModificationModel.setUserInfo(userInfoModel);
        //require non null here
        shopLocationModificationModel.setLocation( new ShopUrlLocationModel());

        claimModel.setModifications(List.of(shopLocationModificationModel));

        try {
            claimRepository.save(claimModel);
        } catch (TransactionSystemException ex) {
            throw ex.getOriginalException().getCause();
        }
    }

}
