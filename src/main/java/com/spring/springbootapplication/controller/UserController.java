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
import java.security.Principal;
import java.io.IOException;
import java.util.Base64; 

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
    public String register(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "signup";
        }
        
        // ユーザー情報を保存する（パスワード暗号化はUserService側で実施）
        userService.registerUser(user);
        
        // 登録後は、ログイン画面にリダイレクトさせる
        return "redirect:/login";
    }

    // 【GET】ログアウト後のコールバック処理
    @GetMapping("/logout-callback")
    public String logoutCallback(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "ログアウトしました");
        return "redirect:/login";
    }

    // 【GET】プロフィール編集画面を表示する
    @GetMapping("/profile/edit")
    public String showProfileEditPage(ProfileEditForm profileEditForm) {
        return "profile-edit";
    }

    // 【POST】プロフィール編集を保存する
    @PostMapping("/profile/edit")
    public String updateProfile(@Valid ProfileEditForm profileEditForm, BindingResult result, Principal principal) throws IOException {
        
        // バリデーションエラーがある場合は編集画面に戻る
        if (result.hasErrors()) {
            return "profile-edit";
        }

        // 現在ログインしているユーザーを特定
        User user = userService.findUserByEmail(principal.getName());

        // 画像保存とDB更新を実行
        userService.updateUserProfile(user, profileEditForm);

        // 登録後はTOP画面（/mypage）に遷移する
        return "redirect:/mypage";
    }

    // 【GET】マイページを表示する
    @GetMapping("/mypage")
    public String showMyPage(Principal principal, org.springframework.ui.Model model) {
        // 現在ログインしているユーザーの情報をDBから取得する
        User user = userService.findUserByEmail(principal.getName());
        
        // 取得したユーザー情報を「user」という名前でHTMLに渡す
        model.addAttribute("user", user);

        // DBの画像をBase64形式に変換してHTMLに渡す
        if (user.getProfileImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getProfileImage());
            model.addAttribute("userImage", "data:image/png;base64," + base64Image);
        }
        
        return "mypage";
    }
}
