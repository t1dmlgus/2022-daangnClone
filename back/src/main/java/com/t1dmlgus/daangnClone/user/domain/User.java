package com.t1dmlgus.daangnClone.user.domain;

import com.t1dmlgus.daangnClone.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public User(String email, String password, String name, String phone, String nickName, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.nickName = nickName;
        this.role = role;
    }





}
