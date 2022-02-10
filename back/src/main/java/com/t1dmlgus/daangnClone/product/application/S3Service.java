package com.t1dmlgus.daangnClone.product.application;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.product.domain.Image;
import com.t1dmlgus.daangnClone.product.domain.ImageRepository;
import com.t1dmlgus.daangnClone.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    public static final String CLOUD_FRONT_DOMAIN_NAME="dhtabz8gaqtjf.cloudfront.net/";

    private final ImageRepository imageRepository;
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;


    @PostConstruct
    public void setS3Client() {

        log.info("# s3 연동 >> setS3Client");
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }


    public void upload(MultipartFile multipartFile, Product product){

        log.info("# 이미지 등록 >> upload");
        // 파일명
        String fileName = createFileName(multipartFile);
        // 업로드
        uploadImages(multipartFile, fileName);
        // 영속화
        saveImages(fileName, product);
    }

    @Transactional
    public void initDataImage(List<MultipartFile> multipartFiles, List<Product> products){

        List<Image> images = new ArrayList<>();

        log.info("# 상품 초기화 >> 전체 이미지 업로드");
        for (int i = 0; i < 10; i++) {
            String fileName = createFileName(multipartFiles.get(i));
            // 업로드
            uploadImages(multipartFiles.get(i), fileName);
            images.add(new Image("https://" + CLOUD_FRONT_DOMAIN_NAME + fileName, products.get(i)));
        }

        log.info("# 상품 초기화 >> 전체 이미지 영속화");
        imageRepository.saveAll(images);
    }



    protected void uploadImages(MultipartFile multipartFile, String fileName) {

        log.info("# 이미지 업로드 >> uploadImages(s3 업로드)");
        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
            objMeta.setContentLength(bytes.length);

            s3Client.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objMeta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));


        } catch (Exception e) {
            //e.printStackTrace();
            throw new CustomApiException("s3 이미지 업로드에 실패하였습니다.");
        }
    }

    protected Long saveImages(String fileName, Product product) {

        log.info("# 이미지 영속화 >> saveImages");
        Image saveImage = imageRepository.save(new Image("https://" + CLOUD_FRONT_DOMAIN_NAME + fileName, product));
        return saveImage.getId();
    }

    // 파일명
    protected String createFileName(MultipartFile multipartFile) {

        log.info("# 이미지 명 >> createFileName");
        UUID uuid = UUID.randomUUID();
        String originalFilename = multipartFile.getOriginalFilename();

        return uuid +"_"+ originalFilename;
    }
    
    // 유효성 검사
    protected void validationFile(MultipartFile multipartFile) {

        log.info("# 이미지 유효성 검사 >> validationFile");
        if (multipartFile == null) {
            throw new CustomApiException("이미지가 첨부되지 않았습니다.");
        }
    }


    // 상품 이미지 조회
    public List<String> inquiryProductImage(Long productId) {

        log.info("# 이미지 조회 >> inquiryProductImage");
        List<String> ProductImages = imageRepository.findAllByProductId(productId)
                .stream().map(Image::getFileName).collect(Collectors.toList());

        return ProductImages;
    }
}
