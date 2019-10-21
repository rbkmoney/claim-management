package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class ModificationModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_info_id", referencedColumnName = "id")
    private UserInfoModel userInfo;

}
