package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    //ブラウザで /signupにアクセスしたら登録画面を返す
    @GetMapping
    public String ahowSignupForm() {
        return "signup"; //templates/signup.htmlを表示
    }
}
