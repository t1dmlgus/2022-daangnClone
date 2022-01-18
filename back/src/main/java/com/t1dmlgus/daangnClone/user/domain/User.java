package com.t1dmlgus.daangnClone.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t1dmlgus.daangnClone.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;



@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @JsonIgnore
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

    // 비밀번호 암호화
    public void bcryptPw(String encode) {
        this.password = encode;
    }

    // 권한 설정
    public void setPermission(Role role) {
        this.role = role;
    }
}
