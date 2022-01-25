package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doReturn;


@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private JoinRequestDto joinRequestDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        joinRequestDto = new JoinRequestDto("dmlgusgngl@gmail.com", "1234", "이의현", "2232-1234", "t1dmlgus", "박달1동");
        testUser = new User("dmlgusgngl@gmail.com", "1234", "이의현", "1234-1234", "t1dmlgus", Role.ROLE_USER, "박달1");
    }

    @DisplayName("서비스 - 회원가입 테스트")
    @Test
    public void joinTest() throws Exception{
        //given
        User user = joinRequestDto.toEntity();
        doReturn(user).when(userRepository).save(any(User.class));

        //when
        ResponseDto<?> join = userServiceImpl.join(joinRequestDto);

        //then
        assertThat(join.getMessage()).isEqualTo("회원가입이 완료되었습니다.");
    }

    @DisplayName("서비스- 이메일 중복확인 테스트")
    @Test
    public void duplicateUsernameTest() throws Exception {
        //given
        User testUser1 = User.builder()
                .email("테스트1@gmail.com")
                .build();
        User testUser2 = User.builder()
                .email("테스트1@gmail.com")
                .build();

        when(userRepository.existsByEmail(testUser1.getEmail())).thenReturn(true);
        //when

        //then
        assertThatThrownBy(() -> userServiceImpl.duplicateUser(testUser2))
                .isInstanceOf(CustomApiException.class)
                .hasMessage("현재 사용중인 이메일입니다.");
    }

    @DisplayName("서비스 - 비밀번호 암호화 테스트")
    @Test
    public void bcryptPwTest() throws Exception {
        //given
        User testUser = User.builder()
                .email("테스트1")
                .password("1234")
                .build();

        //when
        userServiceImpl.bcryptPw(testUser);
        //then
        assertThat(testUser.getPassword()).isNotEqualTo("1234");

    }

    @DisplayName("서비스 - 권한 설정 테스트")
    @Test
    public void setPermissionTest() throws Exception{
        //given
        User testUser = User.builder()
                .email("테스트1")
                .password("1234")
                .build();

        //when
        userServiceImpl.setPermission(testUser);
        
        //then
        assertThat(testUser.getRole()).isEqualTo(Role.ROLE_USER);
    }


    @DisplayName("서비스 - 회원조회 테스트")
    @Test
    public void enquiryUserTest() throws Exception{
        //given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(testUser));

        //when
        ResponseDto<?> enquiryUser = userServiceImpl.enquiry(any(Long.class));

        //then
        assertThat(enquiryUser.getMessage()).isEqualTo("회원이 조회되었습니다.");

    }

    @DisplayName("서비스 - 회원조회 실패 테스트")
    @Test
    public void FailEnquiryUserTest() throws Exception{
        //given
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        //when

        //then
        assertThatThrownBy(() -> userServiceImpl.enquiry(any(Long.class)))
                .isInstanceOf(CustomApiException.class)
                .hasMessage("조회 할 회원이 없습니다.");


    }

}