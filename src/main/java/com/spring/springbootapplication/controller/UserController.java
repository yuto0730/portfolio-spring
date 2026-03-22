package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; 
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService; 

    //【GET】登録画面を表示する。チェック結果を保持するために引数に (User user) を追加
    @GetMapping("/signup")
    public String showSignupForm(User user) {
        return "signup";
    }

    //【POST】登録ボタンが押された時の処理
    @PostMapping("/signup")
    public String register(@Valid User user, BindingResult result) {
        
        // もし入力内容にルール違反（エラー）があったら
        // 保存せずに、そのまま登録画面("signup")に戻る
        if (result.hasErrors()) {
            return "signup";
        }

        // エラーがなければ保存してリダイレクト
        userService.registerUser(user);
        return "redirect:/signup";
    }
}
