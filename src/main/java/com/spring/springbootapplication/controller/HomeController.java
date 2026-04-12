package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    // ログイン画面の表示とフラッシュメッセージの処理 
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        Object error = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

        if (error != null) {
            model.addAttribute("errorMessage", "メールアドレス、もしくはパスワードが間違っています");
            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        }
        
        return "login";
    }
}
