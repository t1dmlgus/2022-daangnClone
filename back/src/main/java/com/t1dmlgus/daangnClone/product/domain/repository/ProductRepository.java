package com.t1dmlgus.daangnClone.product.domain.repository;

import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import com.t1dmlgus.daangnClone.product.ui.dto.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {


    // 카테고리 별 조회
    Page<Product> findByCategory(Category category, Pageable pageable);


    // 상품 상세 조회
    @Override
    InquiryProductResponseDto inquiryProductDetail(Long productId, Long userId);

    // 전체 상품 조회(페이징)
    @Override
    Page<ProductResponseDto> productAll(Long userId, Pageable pageable);
}
