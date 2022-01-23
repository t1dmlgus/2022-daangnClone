package com.t1dmlgus.daangnClone.likes.ui;

import com.t1dmlgus.daangnClone.auth.WithMockCustomUser;
import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
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

import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentRequest;
import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(value = LikesApiController.class)
class LikesApiControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private LikesService likesService;

    private static User testUser;
    private static Product testProduct;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER);
        testProduct = new Product(1L, "상품명", null, 2000, "상품내용", SaleStatus.SALE, testUser);
    }

    @DisplayName("컨트롤러 - 좋아요 추가 테스트")
    @Test
    @WithMockCustomUser
    public void addLikesTest() throws Exception{
        //given
        doReturn(new ResponseDto<>("좋아요가 추가되었습니다.", null))
                .when(likesService).addLikes(testProduct.getId(), testUser.getId());

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/likes/{productId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse())
                );

    }

    @DisplayName("컨트롤러 - 좋아요 취소 테스트")
    @Test
    @WithMockCustomUser
    public void unLikesTest() throws Exception{
        //given
        doReturn(new ResponseDto<>("좋아요가 취소되었습니다.", null))
                .when(likesService).unLikes(testProduct.getId(), testUser.getId());

        //when
        ResultActions resultActions = mockMvc.perform(
                delete("/api/likes/{productId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse())
                );

    }


}