package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl{

    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<?> join(JoinRequestDto joinRequestDto) {

        User user = joinRequestDto.toEntity();

        User joinUser = userRepository.save(user);

        return new ResponseDto<>("회원가입이 완료되었습니다.", joinUser.getId());
    }


}
