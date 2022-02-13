package com.t1dmlgus.daangnClone.product.domain.repository;

import com.t1dmlgus.daangnClone.product.domain.Category;
import com.t1dmlgus.daangnClone.product.domain.Product;
import com.t1dmlgus.daangnClone.product.ui.dto.InquiryProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    // 상품유저가 등록한 최근 상품 상위 4개 조회
    @Query(value = "SELECT * FROM product WHERE user_Id = :userId\n" +
            "AND id <> :productId\n "+
            "order by created_date desc\n" +
            "LIMIT 4", nativeQuery = true)
    List<Product> inquiryProductTopFourByUser(@Param("productId") Long productId, @Param("userId") Long userId);


    // 카테고리 별 조회
    Page<Product> findByCategory(Category category, Pageable pageable);


    // 상품 상세 조회
    @Override
    InquiryProductResponseDto inquiryProductDetail(Long productId, Long userId);
}
