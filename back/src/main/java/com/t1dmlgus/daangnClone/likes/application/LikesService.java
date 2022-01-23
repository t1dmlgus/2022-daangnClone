package com.t1dmlgus.daangnClone.likes.application;


import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;

public interface LikesService {

    // 좋아요 추가
    ResponseDto<?> addLikes(Long productId, Long userId);

    // 좋아요 취소
    ResponseDto<?> unLikes(Long productId, Long userId);

    // 좋아요 상태확인
    boolean checkLikesStatus(Long productId, Long userId);

}
