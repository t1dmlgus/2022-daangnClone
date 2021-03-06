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


    private JoinRequestDto joinRequestDto;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        joinRequestDto = new JoinRequestDto("", "1234", "?????????", "2232-1234", "t1dmlgus","??????1???");

    }
    
    @DisplayName("???????????? - ???????????? ?????????")
    @Test
    public void JoinUserTest() throws Exception{
        //given
        String json = new ObjectMapper().writeValueAsString(joinRequestDto);


        doReturn(new ResponseDto<>("??????????????? ?????????????????????.", 1L))
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
                .andExpect(jsonPath("$.message").value("??????????????? ?????????????????????."))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));

    }

    @DisplayName("???????????? - ???????????? ????????? ?????????")
    @Test
    public void ValidationUserTest() throws Exception{
        //given
        String json = new ObjectMapper().writeValueAsString(joinRequestDto);


        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("email", "email??? ???????????????");

        // new ResponseDto -> data ??? ????????????.
        when(userService.join(joinRequestDto)).thenThrow(new CustomValidationException("????????? ?????? ??????", errorMap));

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
                .andExpect(jsonPath("$.message").value("????????? ?????? ??????"))
                .andExpect(jsonPath("$.data.email").value("email??? ???????????????"))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));
    }


    @DisplayName("???????????? - ???????????? ?????????")
    @Test
    public void enquiryUserTest() throws Exception{
        //given
        UserInquiryResponseDto userInquiryResponseDto = new UserInquiryResponseDto(1L,"?????????","dmlgusgngl@gmail.com", Role.ROLE_USER);
        doReturn(new ResponseDto<>("????????? ?????????????????????.", userInquiryResponseDto))
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
                .andExpect(jsonPath("$.message").value("????????? ?????????????????????."))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));

    }

}