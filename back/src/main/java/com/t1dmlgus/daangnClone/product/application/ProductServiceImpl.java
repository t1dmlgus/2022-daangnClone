package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.handler.exception.CustomApiException;
import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.domain.ProductRepository;
import com.t1dmlgus.daangnClone.product.ui.dto.*;
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

    @Transactional
    @Override
    public ResponseDto<?> inquiryProduct(Long productId, Long userId) {

        log.info("# 상품 서비스 >> 상품 상세정보 조회");

        // 1. 상품
        Product product = productRepository.findById(productId).orElseThrow(
                () -> { return new CustomApiException("해당 상품은 없습니다.");});
        // 2. 몇분 전
        String registerTime = getRegisterProduct(product.getCreatedDate());
        // 3. 상품 이미지 리스트
        List<String> productImages = getProductImages(productId);
        // 4. 해당 유저에 관한 상품 top4
        List<InquiryProductTopFourResponseDto> topFourProducts = inquiryTopFourProduct(productId, product.getUser().getId());
        // 5. 상품 좋아요 정보(상태, 카운트)
        ProductLikesStatus productLikesStatus = getProductLikesStatus(userId, productId);
        return new ResponseDto<>("조회한 상품입니다.", new InquiryProductResponseDto(product, registerTime, getProductImages(productId), productLikesStatus, topFourProducts));
    }


    @Transactional
    @Override
    public ResponseDto<?> allProduct(Long userId, Pageable pageable) {

        log.info("# 상품 서비스, 전체 상품 조회(페이징)");

        List<ProductResponseDto> allProductDtos = new ArrayList<>();
        Page<Product> pageAllProduct = productRepository.findAll(pageable);
        int totalPages = pageAllProduct.getTotalPages();


        //allProductDtos.get(0).get
        for (Product product : pageAllProduct) {

            // 1. 몇분 전
            String registerTime = getRegisterProduct(product.getCreatedDate());

            // 2. 상품 좋아요 정보(상태, 카운트) 조회
            ProductLikesStatus productLikesStatus = getProductLikesStatus(userId, product.getId());

            // 3. 커버 이미지
            String coverImage = getCoverImage(product.getId());

            // 4. 랜딩 페이지 List<Dto>
            allProductDtos.add(new ProductResponseDto(product, registerTime, productLikesStatus, coverImage));
        }
        return new ResponseDto<>("상품을 전체 조회합니다.", new RandingProductDto(allProductDtos, totalPages));
    }

    @Override
    public ResponseDto<?> categoryProduct(Category category,Long userId, Pageable pageable) {

        log.info("# 상품 서비스, 카테고리 별 상품 상세정보 조회");

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

    // 유저 판매 상폼 조회(top4)
    protected List<InquiryProductTopFourResponseDto> inquiryTopFourProduct(Long productId, Long userId) {

        List<InquiryProductTopFourResponseDto> t4Product = new ArrayList<>();
        for (Product product : productRepository.inquiryProductTopFourByUser(productId, userId)) {
            t4Product.add(new InquiryProductTopFourResponseDto(product, getCoverImage(product.getId())));
        }

        return t4Product;
    }


}
