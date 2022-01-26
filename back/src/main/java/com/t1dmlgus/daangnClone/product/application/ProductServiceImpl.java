package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.ProductApiController;
import com.t1dmlgus.daangnClone.product.ui.dto.AllProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductTopFourResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.util.RegisterProductTimeFromNow;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final LikesService likesService;

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
    public ResponseDto<?> inquiryProduct(Long productId, Long userId) {

        // 1. 상품
        Product product = productRepository.findById(productId).orElseThrow(
                () -> { return new CustomApiException("해당 상품은 없습니다.");});

        // 2. 상품 이미지 리스트
        List<String> productImages = getProductImages(productId);

        // 3. 해당 유저에 관한 상품 top4
        List<InquiryProductTopFourResponseDto> topFourProducts = inquiryTopFourProduct(product.getUser().getId());

        // 4. 상품 좋아요 정보(상태, 카운트)
        ProductLikesStatus productLikesStatus = getProductLikesStatus(userId, productId);
        return new ResponseDto<>("조회한 상품입니다.", new InquiryProductResponseDto(product, getProductImages(productId), productLikesStatus, topFourProducts));
    }


    @Transactional
    @Override
    public ResponseDto<?> allProduct(Long userId, Pageable pageable) {

        List<AllProductResponseDto> allProductDtos = new ArrayList<>();

        for (Product product : productRepository.findAll(pageable)) {

            // 1. 몇분 전
            String beforeTime = getRegisterProduct(product.getCreatedDate());

            // 2. 상품 좋아요 정보(상태, 카운트) 조회
            ProductLikesStatus productLikesStatus = getProductLikesStatus(userId, product.getId());

            // 3. 커버 이미지
            String coverImage = getCoverImage(product.getId());

            // 4. 랜딩 페이지 List<Dto>
            allProductDtos.add(new AllProductResponseDto(product, beforeTime, productLikesStatus, coverImage));
        }
        return new ResponseDto<>("상품을 전체 조회합니다.", allProductDtos);
    }


    // 몇분 전 기능
    protected String getRegisterProduct(LocalDateTime localDateTime) {
        return RegisterProductTimeFromNow.calculateTime(localDateTime);
    }

    // 상품 좋아요 정보(상태, 카운트) 조회
    protected ProductLikesStatus getProductLikesStatus(Long userId, Long productId) {
        return likesService.productLikesStatus(productId, userId);
    }

    // 상품 이미지 리스트 조회
    protected List<String> getProductImages(Long productId) {
        return s3Service.inquiryProductImage(productId);
    }

    // 상품 커버 이미지 조회
    protected String getCoverImage(Long productId) {
        return getProductImages(productId).get(0);
    }

    // 유저 판매 상폼 조회(top4)
    protected List<InquiryProductTopFourResponseDto> inquiryTopFourProduct(Long userId) {

        List<InquiryProductTopFourResponseDto> t4Product = new ArrayList<>();
        for (Product product : productRepository.inquiryProductTopFourByUser(userId)) {
            t4Product.add(new InquiryProductTopFourResponseDto(product, getCoverImage(product.getId())));
        }

        return t4Product;
    }


}
