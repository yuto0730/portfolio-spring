package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 「 http://localhost:8080/mypage 」にアクセスが来たときの処理
    @GetMapping("/mypage")
    public String mypage() {
        // mypage.html を画面に表示
        return "mypage"; 
    }
}
