package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.cm.model.UserTypeEnum;
import com.rbkmoney.damsel.claim_management.ExternalUser;
import com.rbkmoney.damsel.claim_management.InternalUser;
import com.rbkmoney.damsel.claim_management.UserInfo;
import com.rbkmoney.damsel.claim_management.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserInfoModelToUserInfoConverter implements ClaimConverter<UserInfoModel, UserInfo> {

    @Override
    public UserInfo convert(UserInfoModel userInfoModel) {
        return new UserInfo()
                .setId(userInfoModel.getUserId())
                .setEmail(userInfoModel.getEmail())
                .setUsername(userInfoModel.getUsername())
                .setType(toUserType(userInfoModel.getType()));
    }

    private UserType toUserType(UserTypeEnum userType) {
        switch (userType) {
            case external:
                return UserType.external_user(new ExternalUser());
            case internal:
                return UserType.internal_user(new InternalUser());
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", userType));
        }
    }
}
