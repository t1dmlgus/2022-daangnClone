package com.t1dmlgus.daangnClone.user.application;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    
    
    @DisplayName("회원가입 서비스 테스트")
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


}