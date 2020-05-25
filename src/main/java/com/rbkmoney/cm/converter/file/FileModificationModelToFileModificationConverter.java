package com.rbkmoney.cm.converter.file;

import com.rbkmoney.cm.converter.ClaimConverter;
import com.rbkmoney.cm.model.file.FileModificationModel;
import com.rbkmoney.damsel.claim_management.FileCreated;
import com.rbkmoney.damsel.claim_management.FileDeleted;
import com.rbkmoney.damsel.claim_management.FileModification;
import org.springframework.stereotype.Component;

@Component
public class FileModificationModelToFileModificationConverter implements ClaimConverter<FileModificationModel, FileModification> {
    @Override
    public FileModification convert(FileModificationModel fileModificationModel) {
        switch (fileModificationModel.getFileModificationType()) {
            case creation:
                return FileModification.creation(new FileCreated());
            case deletion:
                return FileModification.deletion(new FileDeleted());
            default:
                throw new IllegalArgumentException(String.format("Unknown type '%s'", fileModificationModel.getFileModificationType()));
        }
    }
}
