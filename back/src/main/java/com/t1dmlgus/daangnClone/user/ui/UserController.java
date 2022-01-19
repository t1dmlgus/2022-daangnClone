package com.t1dmlgus.daangnClone.user.ui;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // 회원가입
    @GetMapping("/signup")
    public String signup(){
        return "user/signup";
    }


    // 로그인
    @GetMapping("/signin")
    public String signin(){ return "user/signin"; }
}
