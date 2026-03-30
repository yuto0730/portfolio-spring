package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal; 
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.entity.User;
import jakarta.servlet.http.HttpSession; // 追記：セッションを使うために必要

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    // ログイン画面の表示とフラッシュメッセージの処理 
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        // セッションに保存したログイン失敗を確認
        Object error = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

        if (error != null) {
            // エラーがあったら、画面に渡すメッセージ
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
            
            //一度セットしたらセッションから消す
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        }
        
        return "login";
    }

    @GetMapping("/mypage")
    public String mypage(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            if (user != null) {
                model.addAttribute("username", user.getName());
            }
        }
        return "mypage";
    }
}
