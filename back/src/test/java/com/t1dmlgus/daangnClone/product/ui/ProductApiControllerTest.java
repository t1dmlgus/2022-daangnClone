package com.t1dmlgus.daangnClone.product.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1dmlgus.daangnClone.auth.WithMockCustomUser;
import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentRequest;
import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(value = ProductApiController.class)
class ProductApiControllerTest {

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
    }

    
    @DisplayName("컨트롤러 - 상품등록 테스트")
    @Test
    @WithMockCustomUser
    public void registerProductTest() throws Exception{
        //given
        MockMultipartFile file2 = new MockMultipartFile("file", "파일명.jpeg", "image/jpeg", "이미지파일".getBytes());
        doReturn(new ResponseDto<>("상품이 등록되었습니다.", 16L))
                .when(productService).registerProduct(any(), any(), any());

        //when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/product/register")
                        .file(file2)
                        .param("title", "상품명")
                        .param("price", "100")
                        .param("caption", "본문")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultActions
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()
                ));
    }

    


}