package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.ProductApiController;
import com.t1dmlgus.daangnClone.product.ui.dto.AllProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductTopFourResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


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

    @Transactional
    @Override
    public ResponseDto<?> inquiryProduct(Long productId) {
        // 1. 상품 get
        Product product = productRepository.findById(productId).orElseThrow(
                () -> { return new CustomApiException("해당 상품은 없습니다.");});

        // 2. 상품 id -> 상품 이미지리스트 get
        List<String> productImages = s3Service.inquiryProductImage(productId);
        // 3. 해당 유저에 관한 상품(top4) get
        List<InquiryProductTopFourResponseDto> t4Prod = inquiryTopFourProduct(product.getUser().getId());

        return new ResponseDto<>("조회한 상품입니다.", new InquiryProductResponseDto(product, productImages, t4Prod));
    }


    @Transactional
    @Override
    public ResponseDto<?> allProduct() {


        List<AllProductResponseDto> allProductDto = new ArrayList<>();
        List<Product> allProduct = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        for (Product product : allProduct) {
            String coverImage = s3Service.inquiryProductImage(product.getId()).get(0);
            allProductDto.add(new AllProductResponseDto(product, coverImage));
        }
        return new ResponseDto<>("전체 상품 조회힙니다.", allProductDto);
    }

    // 유저 판매 상폼 조회(top4)
    protected List<InquiryProductTopFourResponseDto> inquiryTopFourProduct(Long userId) {

        List<InquiryProductTopFourResponseDto> t4Product = new ArrayList<>();
        List<Product> products = productRepository.inquiryProductTopFourByUser(userId);
        for (Product product : products) {

            // 상품 첫번째 이미지
            String coverImage = s3Service.inquiryProductImage(product.getId()).get(0);
            t4Product.add(new InquiryProductTopFourResponseDto(product, coverImage));
        }

        return t4Product;
    }



}
