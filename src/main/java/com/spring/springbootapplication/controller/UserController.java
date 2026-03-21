package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService; 

    //【GET】ブラウザで /signup にアクセスした時に、登録画面を表示する
    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    //【POST】登録ボタンが押された時に、入力されたデータを受け取って保存する
    @PostMapping("/signup")
    public String register(User user) {
        // 画面から届いた user 情報を、判定ルール(Service)に渡して保存してもらう
        userService.registerUser(user);
        
        // 保存が終わったら、TOP画面（今はまだないので、とりあえずルート / ）へリダイレクト
        return "redirect:/";
    }
}
