package com.t1dmlgus.daangnClone.likes.application;

import com.t1dmlgus.daangnClone.likes.domain.LikesRepository;
import com.t1dmlgus.daangnClone.likes.ui.dto.ProductLikesStatus;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;

    @Transactional
    @Override
    public ResponseDto<?> addLikes(Long productId, Long userId) {

        likesRepository.likes(productId, userId);

        return new ResponseDto<>("좋아요가 추가되었습니다.", null);

    }

    @Transactional
    @Override
    public ResponseDto<?> unLikes(Long productId, Long userId) {

        likesRepository.unLikes(productId, userId);

        return new ResponseDto<>("좋아요가 취소되었습니다.", null);
    }


    @Transactional
    @Override
    public boolean checkLikesStatus(Long productId, Long userId) {

        return likesRepository.existsByProductIdAndUserId(productId, userId);
    }

    @Transactional
    @Override
    public int countProductLikes(Long productId) {

        return likesRepository.countByProductId(productId);
    }

    @Transactional
    @Override
    public ProductLikesStatus productLikesStatus(Long productId, Long userId) {

        // 좋아요 상태(상품에 대한)
        boolean statusUserLikesProduct = checkLikesStatus(productId, userId);
        // 좋아요 카운트(상품에 대한)
        int countProductLikes = countProductLikes(productId);

        return new ProductLikesStatus(statusUserLikesProduct, countProductLikes);

    }

}
