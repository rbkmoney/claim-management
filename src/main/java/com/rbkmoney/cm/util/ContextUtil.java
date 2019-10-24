package com.rbkmoney.cm.util;

import com.rbkmoney.cm.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.cm.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.woody.api.trace.ContextUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextUtil {

    public static UserInfoModel getUserInfoFromContext() {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserId(ContextUtils.getCustomMetadataValue(UserIdentityIdExtensionKit.INSTANCE.getExtension()));
        userInfoModel.setUsername(ContextUtils.getCustomMetadataValue(UserIdentityUsernameExtensionKit.INSTANCE.getExtension()));
        userInfoModel.setEmail(ContextUtils.getCustomMetadataValue(UserIdentityEmailExtensionKit.INSTANCE.getExtension()));
        userInfoModel.setType(ContextUtils.getCustomMetadataValue(UserIdentityRealmExtensionKit.INSTANCE.getExtension()));
        return userInfoModel;
    }

    public static void addUserInfoToContext(UserInfoModel userInfoModel) {
        ContextUtils.setCustomMetadataValue(UserIdentityIdExtensionKit.KEY, userInfoModel.getUserId());
        ContextUtils.setCustomMetadataValue(UserIdentityEmailExtensionKit.KEY, userInfoModel.getEmail());
        ContextUtils.setCustomMetadataValue(UserIdentityUsernameExtensionKit.KEY, userInfoModel.getUsername());
        ContextUtils.setCustomMetadataValue(UserIdentityRealmExtensionKit.KEY, userInfoModel.getType());
    }

}
