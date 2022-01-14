package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.UserInquiryResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Transactional
    public ResponseDto<?> join(JoinRequestDto joinRequestDto) {

        User user = joinRequestDto.toEntity();
        User joinUser = userRepository.save(user);

        return new ResponseDto<>("회원가입이 완료되었습니다.", joinUser.getId());
    }


    @Override
    public ResponseDto<?> enquiry(Long userId) {

        User enquiryUser = userRepository.findById(userId).orElseThrow(
                () -> { return new CustomApiException("조회 할 회원이 없습니다.");});

        ModelMapper modelMapper = new ModelMapper();
        UserInquiryResponseDto mapper = modelMapper.map(enquiryUser, UserInquiryResponseDto.class);

        return new ResponseDto<>("회원이 조회되었습니다.", mapper);
    }


}
