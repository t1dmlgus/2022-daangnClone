package com.t1dmlgus.daangnClone.user.domain;

import com.t1dmlgus.daangnClone.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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



}
