package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;


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

    private static User testUser;
    private static Product testProduct;
    private static ProductRequestDto productRequestDto;
    private static MockMultipartFile file;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER);
        testProduct = new Product(1L, "상품명", null, 2000, "상품내용", SaleStatus.SALE, testUser);
        productRequestDto = new ProductRequestDto("상품명", "PET_SUPPLIES", 100, "상품내용");
        file = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());
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
        doReturn(Optional.of(testProduct)).when(productRepository).findById(any(Long.class));
        doReturn(new ArrayList<>()).when(s3service).inquiryProductImage(any(Long.class));
        doReturn(true).when(likesService).checkLikesStatus(testProduct.getId(), testUser.getId());

        //when
        ResponseDto<?> product = productServiceImpl.inquiryProduct(testProduct.getId(), testUser.getId());
        //then
        assertThat(product.getMessage()).isEqualTo("조회한 상품입니다.");

    }


    @DisplayName("서비스 - 전체 상품 조회(최신순) 테스트")
    @Test
    public void allProductTest() throws Exception{
        //given

        //when


        //then
    }

}