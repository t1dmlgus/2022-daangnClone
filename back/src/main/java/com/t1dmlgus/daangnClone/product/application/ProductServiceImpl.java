package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.ProductApiController;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.user.ui.dto.product.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    Logger logger = LoggerFactory.getLogger(ProductApiController.class);

    @Transactional
    @Override
    public ResponseDto<?> registerProduct(ProductRequestDto productRequestDto, MultipartFile multipartFile, User user) {

        Product product = productRequestDto.toEntity(user);

        // 1. 영속화
        Product saveProduct = productRepository.save(product);
        logger.info("saveProduct, {}", saveProduct);

        // 2. 이미지 업로드
        s3Service.upload(multipartFile, saveProduct);

        return new ResponseDto<>("상품이 등록되었습니다.", saveProduct.getId());
    }


}
