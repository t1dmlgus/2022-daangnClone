package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.product.domain.repository.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.util.RegisterProductTimeFromNow;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Transactional
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private S3Service s3service;
    @Mock
    private LikesService likesService;

    private RegisterProductTimeFromNow registerProductTimeFromNow;
    private static MockedStatic<RegisterProductTimeFromNow> mockedRegisterProductTimeFromNow;

    private static User testUser;
    private static Product testProduct;
    private static ProductRequestDto productRequestDto;
    private static MockMultipartFile file;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER, "박달1동");
        testProduct = new Product(1L, "상품명", Category.BEAUTY, 2000, "상품내용", SaleStatus.SALE, testUser);
        productRequestDto = new ProductRequestDto("상품명", "PET_SUPPLIES", 100, "상품내용");
        file = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());

        mockedRegisterProductTimeFromNow = mockStatic(RegisterProductTimeFromNow.class);
    }

    @AfterAll
    static void afterAll(){
        mockedRegisterProductTimeFromNow.close();
    }

    @BeforeEach
    public void setUp(){
        registerProductTimeFromNow = new RegisterProductTimeFromNow();
    }

    @DisplayName("서비스 - 상품 등록 테스트")
    @Test
    public void registerTest() throws Exception {
        //given
        doReturn(new Product()).when(productRepository).save(any());
        doNothing().when(s3service).upload(any(), any());

        //when
        ResponseDto<?> responseDto = productServiceImpl.registerProduct(productRequestDto, file, testUser);
        //then
        assertThat(responseDto.getMessage()).isEqualTo("상품이 등록되었습니다.");
    }

    @DisplayName("서비스 - 상품 조회 테스트")
    @Test
    public void InquiryProductTest() throws Exception {
        //given

        //when
        ResponseDto<?> product = productServiceImpl.inquiryProduct(testProduct.getId(), testUser.getId());
        //then
        assertThat(product.getMessage()).isEqualTo("상품을 조회했습니다.");

    }


    @DisplayName("서비스 - 전체 상품 조회(최신순) 테스트")
    @Test
    public void allProductTest() throws Exception{
        //given
        // 상품 리스트 페이징
        PageRequest of = PageRequest.of(0, 5);
        //when
        ResponseDto<?> allProductDtos = productServiceImpl.allProduct(testUser.getId(), of);
        //then
        assertThat(allProductDtos.getMessage()).isEqualTo("상품을 전체 조회합니다.");
    }

    @DisplayName("서비스 - 몇분 전 테스트")
    @Test
    public void registerProductCreateDate() throws Exception{

        // RegisterProductTimeFromNow 테스트도 따로 필요할 듯
        //given
        when(RegisterProductTimeFromNow.calculateTime(any()))
                .thenReturn("50초 전");
        //when
        String time = productServiceImpl.getRegisterProduct(LocalDateTime.now());
        //then
        assertThat(time).isEqualTo("50초 전");

    }

    @DisplayName("서비스 - 카테고리 별 상품 조회 테스트")
    @Test
    public void productByCategoryTest() throws Exception{
        //given
        // 상품 리스트 페이징
        PageRequest of = PageRequest.of(0, 5);
        Page<Product> p = new PageImpl<>(new ArrayList<>(List.of(testProduct, testProduct)));
        // 전체 상품 조회
        when(productRepository.findByCategory(Category.BEAUTY, of)).thenReturn(p);
        // 해당 상품 좋아요 상태
        when(likesService.productLikesStatus(testProduct.getId(), testUser.getId()))
                .thenReturn(new ProductLikesStatus(true, 5));
        // 해당 상품 이미지 리스트 조회
        when(s3service.inquiryProductImage(anyLong()))
                .thenReturn(new ArrayList<>(List.of("testImage01", "testImage02", "testImage03")));

        //when
        ResponseDto<?> allProductDtos = productServiceImpl.categoryProduct(Category.BEAUTY, testUser.getId(), of);
        //then
        assertThat(allProductDtos.getMessage()).isEqualTo("카테고리 별로 조회합니다.");
    }

}