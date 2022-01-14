package com.t1dmlgus.daangnClone.user.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    
    
    @DisplayName("서비스 - 회원가입 테스트")
    @Test
    public void joinTest() throws Exception{
        //given

        JoinRequestDto joinRequestDto = new JoinRequestDto("dmlgusgngl@gmail.com",
                "1234", "이의현", "2232-1234", "t1dmlgus");
        User user = joinRequestDto.toEntity();

        doReturn(user).when(userRepository).save(any(User.class));

        //when
        ResponseDto<?> join = userServiceImpl.join(joinRequestDto);

        //then
        assertThat(join.getMessage()).isEqualTo("회원가입이 완료되었습니다.");
    }

    @DisplayName("서비스 - 회원조회 테스트")
    @Test
    public void enquiryUserTest() throws Exception{
        //given
        User user1 = new User("dmlgusgngl@gmail.com", "1234", "이의현", "1234-1234", "t1dmlgus", Role.ROLE_USER);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user1));

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