package com.t1dmlgus.daangnClone.user.ui.dto;

import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    private String email;

    private String password;

    private String name;

    private String phone;

    private String nickName;

    public User toEntity(){

        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .nickName(nickName)
                .build();
    }


}
