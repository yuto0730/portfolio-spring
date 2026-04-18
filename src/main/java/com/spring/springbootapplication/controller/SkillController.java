package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SkillController {

    // 【GET】学習項目編集画面を表示する
    @GetMapping("/skill/edit")
    public String showSkillEditPage() {
        return "skill-edit";
    }
}
