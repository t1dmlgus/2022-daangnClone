package com.t1dmlgus.daangnClone.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM PRODUCT where user_Id = :userId\n" +
            "order by created_date desc\n" +
            "LIMIT 4", nativeQuery = true)
    List<Product> inquiryProductTopFourByUser(@Param("userId") Long userId);
}
