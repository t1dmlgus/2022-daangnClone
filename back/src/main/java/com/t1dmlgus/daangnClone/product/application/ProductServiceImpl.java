package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.handler.aop.LogExecutionTime;
import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.repository.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.TopFourProduct;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import com.t1dmlgus.daangnClone.util.RegisterProductTimeFromNow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final LikesService likesService;

    @Transactional
    @Override
    public ResponseDto<?> registerProduct(ProductRequestDto productRequestDto, MultipartFile multipartFile, User user) {

        log.info("# 상품 서비스 >> 상품 등록");

        // 1. 영속화
        Product product = productRequestDto.toEntity(user);
        Product saveProduct = productRepository.save(product);

        // 2. 이미지 업로드
        s3Service.upload(multipartFile, saveProduct);
        return new ResponseDto<>("상품이 등록되었습니다.", saveProduct.getId());
    }

    @LogExecutionTime
    @Transactional
    @Override
    public ResponseDto<?> inquiryProduct(Long productId, Long userId) {

        log.info("# 상품 서비스 >> 상품 상세정보 조회");
        InquiryProductResponseDto inquiryProductResponseDto = productRepository.inquiryProductDetail(productId, userId);

        return new ResponseDto<>("상품을 조회했습니다.", inquiryProductResponseDto);
    }


    @LogExecutionTime
    @Transactional
    @Override
    public ResponseDto<?> allProduct(Long userId, Pageable pageable) {

        log.info("# 상품 서비스, 전체 상품 조회(페이징)");
        Page<ProductResponseDto> productResponseDtos = productRepository.productAll(userId, pageable);

        return new ResponseDto<>("상품을 전체 조회합니다.", productResponseDtos);
    }

    @Transactional
    @Override
    public ResponseDto<?> categoryProduct(Category category,Long userId, Pageable pageable) {

        log.info("# 상품 서비스, 카테고리 별 상품 상세 조회");

        List<ProductResponseDto> categoryByProductDtos = new ArrayList<>();
        Page<Product> productByCategory = productRepository.findByCategory(category, pageable);

        for (Product product : productByCategory) {
            // 1. 몇분 전
            String registerTime = getRegisterProduct(product.getCreatedDate());
            // 2. 커버 이미지
            String coverImage = getCoverImage(product.getId());
            // 3. 좋아요 정보(상태, 카운트)
            ProductLikesStatus productLikesStatus = getProductLikesStatus(userId, product.getId());
            // 4. 카테고리 상품 DTO
            categoryByProductDtos.add(new ProductResponseDto(product, registerTime,productLikesStatus, coverImage));
        }

        return new ResponseDto<>("카테고리 별로 조회합니다.", categoryByProductDtos);
    }

    @Transactional
    @Override
    public List<Product> initDataProduct(List<ProductRequestDto> productRequestDtos, User adminUser) {

        log.info("# 상품 초기화 >> 전체상품 등록");
        List<Product> collect = productRequestDtos.stream().map(i -> i.toEntity(adminUser)).collect(Collectors.toList());
        productRepository.saveAll(collect);
        return collect;
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



}
