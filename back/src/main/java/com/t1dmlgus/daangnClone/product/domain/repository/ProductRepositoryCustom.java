package com.t1dmlgus.daangnClone.product.domain.repository;

import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    // 상품 상세 조회(querydsl)
    InquiryProductResponseDto inquiryProductDetail(Long productId, Long userId);

    // 전체 상품 조회(querydsl)
    Page<ProductResponseDto> productAll(Long userId, Pageable pageable);
}
