package com.t1dmlgus.daangnClone.user.ui;

import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> join(@Valid @RequestBody JoinRequestDto joinRequestDto, BindingResult bindingResult){

        ResponseDto<?> joinUser = userService.join(joinRequestDto);

        return new ResponseEntity<>(joinUser, HttpStatus.CREATED);
    }

    // 회원조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> enquiryUser(@PathVariable Long userId) {

        ResponseDto<?> enquiryUser = userService.enquiry(userId);

        return new ResponseEntity<>(enquiryUser, HttpStatus.OK);
    }
}
