package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.service.UserService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;


@Controller
public class UserController {

    @Autowired
    private UserService userService; 

    //【GET】登録画面を表示する。
    @GetMapping("/signup")
    public String showSignupForm(User user) {
        return "signup";
    }

    //【POST】登録ボタンが押された時の処理
    @PostMapping("/signup")
    public String register(@Valid User user, BindingResult result, HttpServletRequest request) {
        
        // もし入力内容にルール違反（エラー）があったら
        // 保存せずに、そのまま登録画面("signup")に戻る
        if (result.hasErrors()) {
            return "signup";
        }

        String rawPassword = user.getPassword();

        // エラーがなければ保存
        userService.registerUser(user);
        
        // メモした生パスワードを使って、Spring Securityに自動ログインを依頼する
        try {
            request.login(user.getEmail(), rawPassword);
        } catch (ServletException e) {
            // 万が一ログインに失敗したら、とりあえずログイン画面へ
            return "redirect:/login";
        }

        // 自動ログインが終わったら、登録画面ではなく login画面（"/login"）へ
        return "redirect:/login";
    }

    @GetMapping("/logout-callback")
    public String logoutCallback(RedirectAttributes redirectAttributes) {
        // フラッシュメッセージ
        redirectAttributes.addFlashAttribute("message", "ログアウトしました");
        return "redirect:/login";
    }

    // プロフィール編集画面を表示する
    @GetMapping("/profile/edit")
    public String showProfileEditPage(Model model) {
        // HTML側(th:object)で使うためのフォームを準備して画面に渡す
        model.addAttribute("profileEditForm", new com.spring.springbootapplication.form.ProfileEditForm());
        return "profile-edit";
    }
}
