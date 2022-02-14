package com.t1dmlgus.daangnClone.product.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t1dmlgus.daangnClone.auth.WithMockCustomUser;
import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.product.domain.repository.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.TopFourProduct;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentRequest;
import static com.t1dmlgus.daangnClone.common.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @MockBean
    private ProductRepository productRepository;


    private static User testUser;
    private static Product testProduct;
    private static MockMultipartFile file;
    private static List<ProductResponseDto> categoryByProductDtos;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER, "박달1동");
        testProduct = new Product(1L, "상품명", Category.BEAUTY, 2000, "상품내용", SaleStatus.SALE, testUser);
        file = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());

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

    @DisplayName("컨트롤러 - 상품조회 테스트")
    @Test
    @WithMockCustomUser
    public void inquiryProductTest() throws Exception{
        //given
        String registerTime = "5초 전";
        ProductLikesStatus productLikesStatus = new ProductLikesStatus(true, 3);
        List<String> productImages = new ArrayList<>(List.of("testImageUrl1", "testImageUrl2", "testImageUrl3"));


        List<TopFourProduct> t4Prod =
                new ArrayList<>(List.of(new TopFourProduct(testProduct, "coverImageUrl")
                , new TopFourProduct(testProduct, "coverImageUrl2")));

        InquiryProductResponseDto inquiryProductResponseDto
                = new InquiryProductResponseDto(testProduct,registerTime, productImages, productLikesStatus, t4Prod);

        doReturn(new ResponseDto<>("조회한 상품입니다.", inquiryProductResponseDto))
                .when(productService).inquiryProduct(testProduct.getId(), testUser.getId());


        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/product/{productId}", testProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}", getDocumentRequest(), getDocumentResponse()));
    }

    @DisplayName("컨트롤러 - 카테고리 별 상품 조회 테스트")
    @Test
    @WithMockCustomUser
    public void productByCategoryTest() throws Exception{
        //given
        PageRequest of = PageRequest.of(0, 10);

        String registerTime = "5초 전";
        ProductLikesStatus productLikesStatus = new ProductLikesStatus(true, 3);
        String coverImage = "testImageUrl1";


        categoryByProductDtos = new ArrayList<>(List.of(new ProductResponseDto(testProduct, registerTime, productLikesStatus, coverImage)));
        doReturn(new ResponseDto<>("카테고리 별로 조회합니다.", categoryByProductDtos))
                .when(productService).categoryProduct(Category.BEAUTY, testUser.getId(), of);


        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/product/category/{category}", Category.BEAUTY)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }



}