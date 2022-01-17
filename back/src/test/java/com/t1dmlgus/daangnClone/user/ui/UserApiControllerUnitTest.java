package com.t1dmlgus.daangnClone.user.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1dmlgus.daangnClone.handler.exception.CustomValidationException;
import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.UserInquiryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentRequest;
import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(value = UserApiController.class)
class UserApiControllerUnitTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
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
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));

    }

    @DisplayName("컨트롤러 - 회원가입 유효성 테스트")
    @Test
    public void ValidationUserTest() throws Exception{
        //given
        JoinRequestDto joinRequestDto = new JoinRequestDto("", "1234", "이의현", "2232-1234", "t1dmlgus");
        String json = new ObjectMapper().writeValueAsString(joinRequestDto);


        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("email", "email을 적어주세요");

        // new ResponseDto -> data 가 안담긴다.
        when(userService.join(joinRequestDto)).thenThrow(new CustomValidationException("유효성 검사 실패", errorMap));

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/user/signup")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("유효성 검사 실패"))
                .andExpect(jsonPath("$.data.email").value("email을 적어주세요"))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));
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
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));

    }

}