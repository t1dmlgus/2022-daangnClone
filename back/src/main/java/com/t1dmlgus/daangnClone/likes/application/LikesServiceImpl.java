package com.t1dmlgus.daangnClone.likes.application;

import com.t1dmlgus.daangnClone.likes.domain.LikesRepository;
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
}
