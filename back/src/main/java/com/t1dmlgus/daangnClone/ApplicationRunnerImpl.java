package com.t1dmlgus.daangnClone;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;


@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============ApplicationRunnerImpl");

        //dummyUser();
        //dummyProduct();
    }

    private void dummyUser() {
        for (int i = 1; i <= 10; i++) {
            userService.join(new JoinRequestDto("dumy"+i+"@gmail.com", "1234", "이의현"+i, "010-1234-1234", "t"+i+"dmlgus", "박달" + i + "동"));
        }
    }

    public void dummyProduct() throws IOException {

        // 1. 회원은 10명으로 한정 -> random
        // 2. 상품은 100개 추가
        // 3.등록한 회원이 random으로 생성
        // 4. 상품 이미지 random

        for (int i = 1; i <= 200; i++) {

            // 랜덤 더미 유저 가져오기
            User dummyUser = getDummyUser();
            // 랜덤 더미 상품 커버 이미지 가져오기
            MultipartFile dummyCoverImage = fileToMultipart();
            // 상품 요청 DTO
            ProductRequestDto productRequestDto = new ProductRequestDto("더미상품명" + i, "LIFE_PROCESSED_FOOD", i * 100, "더미 상품입니다" + i);

            // 더미 상품 등록(100개)
            productService.registerProduct(productRequestDto, dummyCoverImage, dummyUser);
        };
    }

    private User getDummyUser() {
        return userRepository.findById((long) ((Math.random() * 10000) % 10)+1).orElseThrow(
                () -> new CustomApiException("해당 아이디는 없습니다.")
        );
    }

    private MultipartFile fileToMultipart() throws IOException {
        int randomInt = (int) ((Math.random() * 10000) % 10)+1;
        File file =  new ClassPathResource("static/img/dummyImg/dummyImg"+randomInt+".png").getFile();
        FileItem fileItem = new DiskFileItem("file", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());

        InputStream input = new FileInputStream(file);
        OutputStream os = fileItem.getOutputStream();
        IOUtils.copy(input, os);

        return new CommonsMultipartFile(fileItem);
    }

}
