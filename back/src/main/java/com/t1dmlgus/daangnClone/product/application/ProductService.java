package com.t1dmlgus.daangnClone.product.application;

import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductRequestDto;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    // 상품 등록
    ResponseDto<?> registerProduct(ProductRequestDto productRequestDto, MultipartFile multipartFile, User user);

    // 상품 조회
    ResponseDto<?> inquiryProduct(Long productId, Long userId);

    // 전체 상품 조회(최신순)
    ResponseDto<?> allProduct(Long userId, Pageable pageable);

    // 카테고리 별 조회
    ResponseDto<?> categoryProduct(Category category, Long userId, Pageable pageable);

    // 상품 데이터 초기화
    List<Product> initDataProduct(List<ProductRequestDto> productRequestDtos, User user);


    // 상품 수정
    // 상품 삭제


}
