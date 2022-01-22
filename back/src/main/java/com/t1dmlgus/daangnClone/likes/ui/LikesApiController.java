package com.t1dmlgus.daangnClone.likes.ui;


import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.likes.application.LikesService;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/likes")
@RequiredArgsConstructor
@RestController
public class LikesApiController {

    private final LikesService likesService;

    @PostMapping("/{productId}")
    public ResponseEntity<?> addLikes(@PathVariable Long productId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDto<?> addLikesDto = likesService.addLikes(productId, principalDetails.getUser().getId());

        return new ResponseEntity<>(addLikesDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> unLikes(@PathVariable Long productId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        ResponseDto<?> unLikesDto = likesService.unLikes(productId, principalDetails.getUser().getId());

        return new ResponseEntity<>(unLikesDto, HttpStatus.OK);
    }

}
