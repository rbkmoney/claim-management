package com.rbkmoney.cm.model.file;

import com.rbkmoney.cm.model.ClaimModificationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FileModificationModel extends ClaimModificationModel {

    @Column(nullable = false)
    private String fileId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileModificationTypeEnum fileModificationType;

}
