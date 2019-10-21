package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.damsel.claim_management.UserInfo;
import com.rbkmoney.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class UserInfoToUserInfoModelConverter implements ClaimConverter<UserInfo, UserInfoModel> {
    @Override
    public UserInfoModel convert(UserInfo userInfo) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId(userInfo.getId());
        userInfoModel.setEmail(userInfo.getEmail());
        userInfoModel.setUsername(userInfo.getUsername());
        userInfoModel.setType(TBaseUtil.unionFieldToEnum(userInfo.getType(), UserTypeEnum.class));
        return userInfoModel;
    }
}
