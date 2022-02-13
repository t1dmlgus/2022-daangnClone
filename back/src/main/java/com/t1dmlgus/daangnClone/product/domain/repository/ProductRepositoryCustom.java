package com.t1dmlgus.daangnClone.product.domain.repository;

import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;

public interface ProductRepositoryCustom {

    // 상품 상세 조회(querydsl)
    InquiryProductResponseDto inquiryProductDetail(Long productId, Long userId);
}
