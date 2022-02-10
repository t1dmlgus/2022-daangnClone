package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.UserInquiryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public ResponseDto<?> join(JoinRequestDto joinRequestDto) {

        log.info("# 유저 서비스 >> 회원가입");
        User user = joinRequestDto.toEntity();
        
        duplicateUser(user);
        bcryptPw(user);
        setPermission(user);

        log.info("# 유저 서비스 >> 유저 영속화");
        User joinUser = userRepository.save(user);
        return new ResponseDto<>("회원가입이 완료되었습니다.", joinUser.getId());
    }

    protected void setPermission(User user) {
        
       log.info("# 유저 서비스 >> 권한 처리");
       user.setPermission(Role.ROLE_USER);
    }

    protected void duplicateUser(User user){

        log.info("# 유저 서비스 >> 중복확인(이메일)");
        boolean existsUsername = userRepository.existsByEmail(user.getEmail());
        if(existsUsername){
            throw new CustomApiException("현재 사용중인 이메일입니다.");
        }
    }

    protected void bcryptPw(User user) {

        log.info("# 유저 서비스 >> 암호화(패스웨드)");
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.bcryptPw(encode);
    }


    @Override
    @Transactional
    public ResponseDto<?> inquiry(Long userId) {

        log.info("# 유저 서비스 >> 회원조회");
        User enquiryUser = userRepository.findById(userId).orElseThrow(
                () -> { return new CustomApiException("조회 할 회원이 없습니다.");});

        ModelMapper modelMapper = new ModelMapper();
        return new ResponseDto<>("회원이 조회되었습니다.", modelMapper.map(enquiryUser, UserInquiryResponseDto.class));
    }

    @Override
    public User initDataUser(Long id) {

        log.info("# 유저 초기화 >> 관리자 등록");
        if (userRepository.existsById(id)) {
            return userRepository.getById(id);
        }
        return userRepository.save(new User("admin@gmail.com", "1234", "관리자", "010-1234-1234", "관리자", Role.ROLE_ADMIN, "박달동"));
    }


}
