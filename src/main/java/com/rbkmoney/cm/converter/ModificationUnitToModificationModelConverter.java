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
public class ModificationUnitToModificationModelConverter
        implements ClaimConverter<ModificationUnit, ModificationModel> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ModificationModel convert(ModificationUnit modificationUnit) {
        ModificationModel modificationModel =
                conversionService.convert(modificationUnit.getModification(), ModificationModel.class);
        if (modificationModel == null) {
            throw new IllegalStateException("ModificationModel can't be null");
        }
        UserInfoModel userInfoModel = conversionService.convert(modificationUnit.getUserInfo(), UserInfoModel.class);
        modificationModel.setId(modificationUnit.getModificationId());
        modificationModel.setCreatedAt(TypeUtil.stringToInstant(modificationUnit.getCreatedAt()));
        modificationModel.setChangedAt(modificationUnit.getChangedAt() != null
                ? TypeUtil.stringToInstant(modificationUnit.getChangedAt()) : null);
        modificationModel.setRemovedAt(modificationUnit.getRemovedAt() != null
                ? TypeUtil.stringToInstant(modificationUnit.getRemovedAt()) : null);
        modificationModel.setUserInfo(userInfoModel);
        return modificationModel;
    }
}
