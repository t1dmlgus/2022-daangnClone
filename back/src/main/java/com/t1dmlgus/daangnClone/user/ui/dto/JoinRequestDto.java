package com.t1dmlgus.daangnClone.user.ui.dto;

import com.t1dmlgus.daangnClone.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinRequestDto {

    @Email(message = "email 형식이 아닙니다.")
    @NotBlank(message = "email을 적어주세요")
    private String email;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 적어주세요")
    private String name;

    private String phone;

    @NotBlank(message = "닉네임을 적어주세요")
    private String nickName;

    private String place;

    public User toEntity(){

        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .nickName(nickName)
                .place(place)
                .build();
    }


}
