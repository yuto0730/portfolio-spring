package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal; 
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.entity.User;         

@Controller
public class HomeController {

    @Autowired
    private UserService userService; // データベースからユーザーを探す

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    @GetMapping("/mypage")
    public String mypage(Model model, Principal principal) {
        if (principal != null) {
            // メアド(getName)を元に、データベースからユーザー情報を丸ごと持ってくる
            User user = userService.findUserByEmail(principal.getName());
            
            // その中の「名前（氏名）」だけを画面に送る！
            model.addAttribute("username", user.getName());
        }
        return "mypage";
    }
}
