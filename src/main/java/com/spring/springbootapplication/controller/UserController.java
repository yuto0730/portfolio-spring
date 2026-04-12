package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.form.ProfileEditForm;
import com.spring.springbootapplication.service.UserService;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;

@Controller
public class UserController {

    @Autowired
    private UserService userService; 

    // 【GET】登録画面を表示する。
    @GetMapping("/signup")
    public String showSignupForm(User user) {
        return "signup";
    }

    // 【POST】登録ボタンが押された時の処理
    @PostMapping("/signup")
    public String register(@Valid User user, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "signup";
        }
        String rawPassword = user.getPassword();
        userService.registerUser(user);
        try {
            request.login(user.getEmail(), rawPassword);
        } catch (ServletException e) {
            return "redirect:/login";
        }
        return "redirect:/login";
    }

    // 【GET】ログアウト後のコールバック処理
    @GetMapping("/logout-callback")
    public String logoutCallback(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "ログアウトしました");
        return "redirect:/login";
    }

    // 【GET】プロフィール編集画面を表示する。
    @GetMapping("/profile/edit")
    public String showProfileEditPage(ProfileEditForm profileEditForm) {
        return "profile-edit";
    }
}
