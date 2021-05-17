package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class UserInfoModel {

    @NotNull
    @Column(nullable = false)
    public String userId;
    @NotNull
    @Column(nullable = false)
    public String email;
    @NotNull
    @Column(nullable = false)
    public String username;
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public UserTypeEnum type;
    @Id
    @GeneratedValue
    private long id;

}
