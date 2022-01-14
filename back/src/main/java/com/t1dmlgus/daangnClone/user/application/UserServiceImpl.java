package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.UserInquiryResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public ResponseDto<?> join(JoinRequestDto joinRequestDto) {

        User user = joinRequestDto.toEntity();

        // 1. 중복확인(이메일)
        duplicateUser(user);

        // 2. 암호화(패스웨드)
        bcryptPw(user);

        // 3. 권한 처리
        setPermission(user);

        // 4. 영속화
        User joinUser = userRepository.save(user);

        return new ResponseDto<>("회원가입이 완료되었습니다.", joinUser.getId());
    }

    protected void setPermission(User user) {
       user.setPermission(Role.ROLE_USER);
    }

    protected void duplicateUser(User user){

        boolean existsUsername = userRepository.existsByEmail(user.getEmail());
        if(existsUsername){
            throw new CustomApiException("현재 사용중인 이메일입니다.");
        }
    }

    protected void bcryptPw(User user) {

        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.bcryptPw(encode);
    }


    @Override
    @Transactional
    public ResponseDto<?> enquiry(Long userId) {

        User enquiryUser = userRepository.findById(userId).orElseThrow(
                () -> { return new CustomApiException("조회 할 회원이 없습니다.");});

        ModelMapper modelMapper = new ModelMapper();

        return new ResponseDto<>("회원이 조회되었습니다.", modelMapper.map(enquiryUser, UserInquiryResponseDto.class));
    }


}
