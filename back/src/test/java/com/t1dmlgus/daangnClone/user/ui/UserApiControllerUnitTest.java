package com.t1dmlgus.daangnClone.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.UserInquiryResponseDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@WebMvcTest(value = UserApiController.class)
class UserApiControllerUnitTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }
    
    @DisplayName("컨트롤러 - 회원가입 테스트")
    @Test
    public void JoinUserTest() throws Exception{
        //given
        JoinRequestDto joinRequestDto = new JoinRequestDto("dmlgusgngl@gmail.com",
                "1234", "이의현", "2232-1234", "t1dmlgus");
        String json = new ObjectMapper().writeValueAsString(joinRequestDto);


        doReturn(new ResponseDto<>("회원가입이 완료되었습니다.", 1L))
                .when(userService).join(joinRequestDto);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/user/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                .andDo(print());

    }


    @DisplayName("컨트롤러 - 회원조회 테스트")
    @Test
    public void enquiryUserTest() throws Exception{
        //given
        UserInquiryResponseDto userInquiryResponseDto = new UserInquiryResponseDto(1L,"이의현","dmlgusgngl@gmail.com", Role.ROLE_USER);
        doReturn(new ResponseDto<>("회원이 조회되었습니다.", userInquiryResponseDto))
                .when(userService).enquiry(any(Long.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/user/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );
        
        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원이 조회되었습니다."))
                .andDo(print());

    }

}