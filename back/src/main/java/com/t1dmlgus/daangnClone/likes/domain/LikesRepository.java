package com.t1dmlgus.daangnClone.likes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(product_Id, user_Id, created_date) VALUES(:productId, :userId, now())", nativeQuery = true)
    void likes(@Param("productId") Long productId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE product_Id =:productId AND user_Id = :userId", nativeQuery = true)
    void unLikes(@Param("productId") Long productId, @Param("userId") Long userId);

    //@Query(value = "select EXISTS (select * from likes where product_id=:productId AND user_id=:userId) as success", nativeQuery = true)
    boolean existsByProductIdAndUserId(Long productId, Long userId);

}
