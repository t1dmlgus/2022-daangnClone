package com.t1dmlgus.daangnClone.user.ui;

import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto){

        ResponseDto<?> joinUser = userService.join(joinRequestDto);

        return new ResponseEntity<>(joinUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> enquiryUser(@PathVariable Long userId) {

        ResponseDto<?> enquiryUser = userService.enquiry(userId);

        return new ResponseEntity<>(enquiryUser, HttpStatus.OK);
    }
}
