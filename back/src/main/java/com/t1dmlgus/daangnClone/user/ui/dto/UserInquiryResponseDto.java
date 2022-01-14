package com.t1dmlgus.daangnClone.user.ui.dto;

import com.t1dmlgus.daangnClone.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInquiryResponseDto {

    private Long id;
    private String name;
    private String email;
    private Role role;


}
