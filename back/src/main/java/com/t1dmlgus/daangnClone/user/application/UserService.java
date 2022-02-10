package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;

public interface UserService {

    // 회원가입
    ResponseDto<?> join(JoinRequestDto JoinRequestDto);

    // 회원조회
    ResponseDto<?> inquiry(Long userId);

    // 관리자 유저 등록
    User initDataUser(Long id);

}
