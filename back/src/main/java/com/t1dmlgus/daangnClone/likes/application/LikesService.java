package com.t1dmlgus.daangnClone.likes.application;


import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;

public interface LikesService {

    // 좋아요 추가
    ResponseDto<?> addLikes(Long productId, Long userId);

    // 좋아요 취소
    ResponseDto<?> unLikes(Long productId, Long userId);

    // 좋아요 상태확인
    boolean checkLikesStatus(Long productId, Long userId);

    // 상품 좋아요 카운트 조회
    int countProductLikes(Long productId);

    // 상품에 대한 좋아요 정보
    ProductLikesStatus productLikesStatus(Long productId, Long userId);
}
