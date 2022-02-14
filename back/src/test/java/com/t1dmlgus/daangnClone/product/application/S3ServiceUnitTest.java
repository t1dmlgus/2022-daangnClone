package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.product.domain.Image;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.SaleStatus;
import com.t1dmlgus.daangnClone.product.domain.repository.ImageRepository;
import com.t1dmlgus.daangnClone.user.domain.Role;
import com.t1dmlgus.daangnClone.user.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@Transactional
@ExtendWith(MockitoExtension.class)
class S3ServiceUnitTest {

    Logger logger = LoggerFactory.getLogger(S3ServiceUnitTest.class);

    @InjectMocks
    private S3Service s3Service;

    @Mock
    private ImageRepository imageRepository;

    private static User testUser;
    private static Product testProduct;
    private static MockMultipartFile multipartFile;

    @BeforeAll
    static void beforeAll() {
        testUser = new User(1L, "dmlgusgngl@gmail.com", "1234", "이의현", "010-1234-1234", "t1dmlgus", Role.ROLE_USER, "박달1동");
        testProduct = new Product(1L, "상품명", null, 2000, "상품내용", SaleStatus.SALE, testUser);
        multipartFile = new MockMultipartFile("파일명", "파일명.jpeg", "image/jpeg", "파일12".getBytes());
    }
    

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
        String fileName = "uuid_파일명";
        //when

        //then
        assertThatThrownBy(() -> s3Service.uploadImages(multipartFile, fileName))
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

    @DisplayName("서비스 - 상품 이미지 조회 테스트")
    @Test
    public void inquiryProductImageTest() throws Exception{
        //given
        List<Image> st = new ArrayList<>();
        st.add(new Image(1L, "testFileName", testProduct));

        when(imageRepository.findAllByProductId(testProduct.getId()))
                .thenReturn(st);

        //when
        List<String> fileNames = s3Service.inquiryProductImage(testProduct.getId());

        //then
        assertThat(fileNames.get(0)).isEqualTo("testFileName");

    }

    @DisplayName("서비스 - 이미지 유효성 검사")
    @Test
    public void validationFile() throws Exception{
        //given

        //when
        assertThatThrownBy(() -> s3Service.validationFile(null))
                .isInstanceOf(Exception.class)
                .hasMessage("이미지가 첨부되지 않았습니다.");

        //then
    }

}


