package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.product.domain.Image;
import com.t1dmlgus.daangnClone.product.domain.ImageRepository;
import com.t1dmlgus.daangnClone.product.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;


@Transactional
@ExtendWith(MockitoExtension.class)
class S3ServiceUnitTest {

    Logger logger = LoggerFactory.getLogger(S3ServiceUnitTest.class);

    @InjectMocks
    private S3Service s3Service;

    @Mock
    private ImageRepository imageRepository;

    @DisplayName("서비스 - 이미지 파일명 생성 테스트")
    @Test
    public void createFileName() throws Exception{
        //given
        MockMultipartFile file = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());
        //when
        String fileName = s3Service.createFileName(file);
        logger.info("fileName, {}", fileName);
        
        //then
        assertThat(fileName).contains("파일명.jpeg");
    }    

    @DisplayName("서비스 - 이미지 업로드(실패) 테스트")
    @Test
    public void uploadImageTest() throws Exception{
        //given
        MockMultipartFile file = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());
        String fileName = "uuid_파일명";
        //when

        //then
        assertThatThrownBy(() -> s3Service.uploadImages(file, fileName))
                .isInstanceOf(Exception.class)
                .hasMessage("s3 이미지 업로드에 실패하였습니다.");

    }

    @DisplayName("서비스 - 이미지 저장 테스트")
    @Test
    public void saveImageTest() throws Exception{
        //given
        String fileName = "uuid_파일명";
        Product product = new Product();
        given(imageRepository.save(any())).willReturn(new Image(1L, fileName, product));

        //when
        Long saveImageId = s3Service.saveImages(fileName, product);

        //then
        assertThat(saveImageId).isEqualTo(1L);

    }
}


