package com.rbkmoney.cm.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserInfoModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    public String userId;

    @Column(nullable = false)
    public String email;

    @Column(nullable = false)
    public String username;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public UserTypeEnum type;

}
