package com.t1dmlgus.daangnClone.user.ui;

import com.t1dmlgus.daangnClone.user.application.UserService;
import com.t1dmlgus.daangnClone.user.application.UserServiceImpl;
import com.t1dmlgus.daangnClone.user.ui.dto.JoinRequestDto;
import com.t1dmlgus.daangnClone.user.ui.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserServiceImpl userServiceImpl;


    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto joinRequestDto){

        ResponseDto<?> join = userServiceImpl.join(joinRequestDto);

        return new ResponseEntity<>(join, HttpStatus.OK);
    }
}
