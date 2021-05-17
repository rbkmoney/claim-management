package com.rbkmoney.cm.converter.file;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.file.FileModificationModel;
import com.rbkmoney.cm.model.file.FileModificationTypeEnum;
import com.rbkmoney.damsel.claim_management.FileModificationUnit;
import com.rbkmoney.geck.common.util.TBaseUtil;
import org.springframework.stereotype.Component;

@Component
public class FileModificationUnitToFileModificationModelConverter
        implements ClaimConverter<FileModificationUnit, FileModificationModel> {

    @Override
    public FileModificationModel convert(FileModificationUnit fileModificationUnit) {
        FileModificationModel fileModificationModel = new FileModificationModel();
        fileModificationModel.setFileId(fileModificationUnit.getId());
        fileModificationModel.setFileModificationType(
                TBaseUtil.unionFieldToEnum(
                        fileModificationUnit.getModification(),
                        FileModificationTypeEnum.class
                )
        );
        return fileModificationModel;
    }
}
