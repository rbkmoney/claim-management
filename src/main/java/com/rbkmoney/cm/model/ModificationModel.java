package com.rbkmoney.cm.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@ToString
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
public class ModificationModel {

    @Id
    @GeneratedValue
    private long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_info_id", referencedColumnName = "id")
    private UserInfoModel userInfo;

    public boolean canEqual(Object that) {
        return that instanceof ModificationModel;
    }

}
