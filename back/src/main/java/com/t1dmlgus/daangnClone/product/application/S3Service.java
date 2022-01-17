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
import com.t1dmlgus.daangnClone.product.ui.ProductApiController;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class S3Service {

    Logger logger = LoggerFactory.getLogger(S3Service.class);

    //public static final String CLOUD_FRONT_DOMAIN_NAME="d3r3itann8ixvx.cloudfront.net";

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
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }


    public void upload(MultipartFile multipartFile, Product product){

        // 파일명
        String fileName = createFileName(multipartFile);
        logger.info("fileName, {}", fileName);

        // 업로드
        uploadImages(multipartFile, fileName);

        // 영속화
        saveImages(fileName, product);
    }

    // 이미지 업로드(s3)
    protected void uploadImages(MultipartFile multipartFile, String fileName) {
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

    // 이미지 영속화
    protected Long saveImages(String fileName, Product product) {
        Image saveImage = imageRepository.save(new Image(fileName, product));
        return saveImage.getId();
    }

    // 파일명
    protected String createFileName(MultipartFile multipartFile) {
        UUID uuid = UUID.randomUUID();
        String originalFilename = multipartFile.getOriginalFilename();

        return uuid +"_"+ originalFilename;
    }
    
    // 유효성 검사
    protected void validationFile(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new CustomApiException("이미지가 첨부되지 않았습니다.");
        }
    }
}
