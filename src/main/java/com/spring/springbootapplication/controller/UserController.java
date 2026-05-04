package com.spring.springbootapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.dto.SkillChartDto;
import com.spring.springbootapplication.form.ProfileEditForm;
import com.spring.springbootapplication.service.UserService;
import com.spring.springbootapplication.service.LearningDataService;

import jakarta.validation.Valid;
import java.security.Principal;
import java.io.IOException;
import java.util.Base64; 
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService; 

    @Autowired
    private LearningDataService learningDataService;

    // 【GET】登録画面を表示する
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
        userService.registerUser(user);
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
    public String showProfileEditPage(Principal principal, Model model) {
        // 現在ログインしているユーザーの情報をDBから取得
        User user = userService.findUserByEmail(principal.getName());

        // フォームクラスに、現在のDBの値をセットする（初期値の表示用）
        ProfileEditForm profileEditForm = new ProfileEditForm();
        profileEditForm.setSelfIntroduction(user.getBio());
        profileEditForm.setProfileImageName(user.getProfileImageName()); 

        // フォームをModelに追加して画面に渡す
        model.addAttribute("profileEditForm", profileEditForm);
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

        // 保存処理（Service側でファイル名とバイナリデータをDBに保存）
        userService.updateUserProfile(user, profileEditForm);

        // 保存後はマイページに遷移
        return "redirect:/mypage";
    }

    // 【GET】マイページを表示する
    @GetMapping("/mypage")
    public String showMyPage(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);

        // マイページではプロフィール画像を表示する
        if (user.getProfileImage() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getProfileImage());
            model.addAttribute("userImage", "data:image/png;base64," + base64Image);
        }

        // スキルチャートのデータを取得する
        List<SkillChartDto> chartData = learningDataService.getSkillChartData(user.getId());

        // 【挙動確認用のログ出力】
        System.out.println("====== [デバッグ] スキルチャートデータ ======");
        if (chartData != null) {
            for (SkillChartDto dto : chartData) {
                System.out.println("カテゴリ: " + dto.getCategoryName() + " (ID: " + dto.getCategoryId() + ")");
                System.out.println(" -> 学習時間(先々月, 先月, 今月): " + dto.getStudyTimes());
            }
        } else {
            System.out.println("スキルチャートデータが取得できていません。");
        }
        System.out.println("===========================================");
        
        return "mypage";
    }
}
