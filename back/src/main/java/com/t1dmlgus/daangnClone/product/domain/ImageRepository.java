package com.t1dmlgus.daangnClone.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    // 해당 상품 이미지 조회
    List<Image> findAllByProductId(Long productId);
}
