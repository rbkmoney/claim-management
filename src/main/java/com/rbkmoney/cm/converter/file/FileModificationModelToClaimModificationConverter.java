package com.rbkmoney.cm.converter.file;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.file.FileModificationModel;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.FileModification;
import com.rbkmoney.damsel.claim_management.FileModificationUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@Component
public class FileModificationModelToClaimModificationConverter
        implements ClaimConverter<FileModificationModel, ClaimModification> {

    @Lazy
    @Autowired
    private ConversionService conversionService;

    @Override
    public ClaimModification convert(FileModificationModel fileModificationModel) {
        return ClaimModification.file_modification(
                new FileModificationUnit()
                        .setId(fileModificationModel.getFileId())
                        .setModification(
                                conversionService.convert(fileModificationModel, FileModification.class)
                        )
        );
    }
}
