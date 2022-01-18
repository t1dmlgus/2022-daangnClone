package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.product.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
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
    
    @DisplayName("서비스 - 상품 등록 테스트")
    @Test
    public void registerTest() throws Exception{
        //given

        ProductRequestDto productRequestDto = new ProductRequestDto("상품명",null,100,"상품내용");
        MockMultipartFile file = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());

        doReturn(new Product()).when(productRepository).save(any());
        doNothing().when(s3service).upload(any(),any());

        //when
        ResponseDto<?> responseDto = productServiceImpl.registerProduct(productRequestDto, file, new User());

        //then
        assertThat(responseDto.getMessage()).isEqualTo("상품이 등록되었습니다.");
        
        
    }
    
    
}