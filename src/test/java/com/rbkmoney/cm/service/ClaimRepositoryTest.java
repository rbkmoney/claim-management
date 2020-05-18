package com.rbkmoney.cm.service;

import com.rbkmoney.cm.AbstractIntegrationTest;
import com.rbkmoney.cm.model.*;
import com.rbkmoney.cm.model.comment.CommentModificationModel;
import com.rbkmoney.cm.model.comment.CommentModificationTypeEnum;
import com.rbkmoney.cm.model.shop.ShopLocationModificationModel;
import com.rbkmoney.cm.repository.ClaimRepository;
import com.rbkmoney.cm.repository.ClaimSpecifications;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    @Test
    @Transactional
    public void testSearchClaimsByEmail() {
        ClaimModel claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));
        ShopLocationModificationModel shopLocationModificationModel = new ShopLocationModificationModel();
        shopLocationModificationModel.setShopId("shop_id");
        ShopUrlLocationModel shopLocationModel = new ShopUrlLocationModel();
        shopLocationModel.setUrl("url");
        shopLocationModificationModel.setLocation(shopLocationModel);
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.qwe");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);
        shopLocationModificationModel.setUserInfo(userInfoModel);
        claimModel.setModifications(Arrays.asList(shopLocationModificationModel));
        claimRepository.save(claimModel);

        claimModel = new ClaimModel();
        claimModel.setPartyId("party_id");
        claimModel.setClaimStatus(new ClaimStatusModel(ClaimStatusEnum.pending, null));
        CommentModificationModel commentModificationModel = new CommentModificationModel();
        commentModificationModel.setCommentId("comment_id");
        commentModificationModel.setCommentModificationType(CommentModificationTypeEnum.creation);
        userInfoModel = new UserInfoModel();
        userInfoModel.setUserId("213");
        userInfoModel.setEmail("qwe@qwe.q123");
        userInfoModel.setUsername("qwe");
        userInfoModel.setType(UserTypeEnum.external);
        commentModificationModel.setUserInfo(userInfoModel);
        claimModel.setModifications(Arrays.asList(commentModificationModel));
        claimRepository.save(claimModel);

        List<ClaimModel> claimModels = claimRepository.findAll(ClaimSpecifications.equalsByEmail(userInfoModel.getEmail()));
        assertEquals(1L, claimModels.size());
        assertEquals(userInfoModel.getEmail(), claimModels.get(0).getModifications().get(0).getUserInfo().getEmail());

        claimModels = claimRepository.findAll(ClaimSpecifications.equalsByEmail(null));
        assertEquals(2L, claimModels.size());
    }

}
