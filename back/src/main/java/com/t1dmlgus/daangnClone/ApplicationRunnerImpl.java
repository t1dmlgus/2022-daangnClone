package com.t1dmlgus.daangnClone;

import com.t1dmlgus.daangnClone.product.application.ProductService;
import com.t1dmlgus.daangnClone.product.application.S3Service;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private ProductService productService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("# 데이터 초기화 >> ApplicationRunnerImpl");
        initProduct(initUser());
    }

    private User initUser() {
        log.info("# 유저 초기화 >> ");
        return userService.initDataUser(1993L);
    }

    public void initProduct(User adminUser) throws IOException {

        log.info("# 상품 초기화 >> ");
        List<Product> products = productService.initDataProduct(initDataProductByCsv(), adminUser);

        log.info("# 상품 이미지 초기화 >> ");
        String[] category = {"DIGITAL_DEVICE","FURNITURE_INTERIOR"};

        for (int index = 0; index < category.length; index++) {
            List<MultipartFile> multipartFiles = fileToMultipart(category[index]);
            s3Service.initDataImage(multipartFiles, products, index);
        }

    }

    private List<MultipartFile> fileToMultipart(String data) throws IOException {

        log.info("# 상품 초기화 >> 이미지 파일 가져오기");

        List<MultipartFile> multipartFiles = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {

            File file = new ClassPathResource("static/data/"+data+"/" + i + ".PNG").getFile();
            FileItem fileItem = new DiskFileItem("", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());

            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);

            multipartFiles.add(new CommonsMultipartFile(fileItem));
        }
        return multipartFiles;
    }


    public List<ProductRequestDto> initDataProductByCsv() throws IOException {

        log.info("# 상품 초기화 >> csv 데이터 가져오기");
        ClassPathResource resource = new ClassPathResource("static/data/carrot_dummyData.csv");

        return Files.readAllLines(resource.getFile().toPath())
                .stream().map(line -> {
                    String[] split = line.split(",");
                    return new ProductRequestDto(split[0], split[1], Integer.parseInt(split[2]), split[3]);
                }).collect(Collectors.toList());
    }
}
