package com.t1dmlgus.daangnClone.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthController {

    // 인증 정보
    @ResponseBody
    @GetMapping("/auth")
    public Authentication auth(){

        return SecurityContextHolder.getContext().getAuthentication();
    }

    // 인덱스 페이지
    @GetMapping("/")
    public String index(){
        return "index.html";
    }

}
