package com.rbkmoney.cm.converter;

import com.rbkmoney.cm.model.ModificationModel;
import com.rbkmoney.cm.model.UserInfoModel;
import com.rbkmoney.damsel.claim_management.ModificationUnit;
import com.rbkmoney.geck.common.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class ModificationUnitToModificationModelConverter implements ClaimConverter<ModificationUnit, ModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationModel convert(ModificationUnit modificationUnit) {
        ModificationModel modificationModel = conversionService.convert(modificationUnit.getModification(), ModificationModel.class);
        UserInfoModel userInfoModel = conversionService.convert(modificationUnit.getUserInfo(), UserInfoModel.class);
        modificationModel.setId(modificationUnit.getModificationId());
        modificationModel.setCreatedAt(TypeUtil.stringToLocalDateTime(modificationUnit.getCreatedAt()));
        modificationModel.setUserInfo(userInfoModel);
        return modificationModel;
    }
}
