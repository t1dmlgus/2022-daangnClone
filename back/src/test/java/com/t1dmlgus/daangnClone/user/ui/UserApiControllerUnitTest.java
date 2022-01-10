package com.t1dmlgus.daangnClone.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1dmlgus.daangnClone.user.application.UserServiceImpl;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = UserApiController.class)
class UserApiControllerUnitTest {

    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userServiceImpl;


    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }


    @DisplayName("회원가입 컨트롤러 테스트")
    @Test
    public void ControllerJoinTest() throws Exception{
        //given
        JoinRequestDto joinRequestDto = new JoinRequestDto("dmlgusgngl@gmail.com",
                "1234", "이의현", "2232-1234", "t1dmlgus");
        String json = new ObjectMapper().writeValueAsString(joinRequestDto);


        doReturn(new ResponseDto<>("회원가입이 완료되었습니다.", 1L))
                .when(userServiceImpl).join(joinRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/user/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                .andDo(print());

    }
}