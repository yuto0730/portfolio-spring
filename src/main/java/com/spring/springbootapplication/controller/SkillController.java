package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class SkillController {

    // 【GET】 学習項目編集画面を表示する
    @GetMapping("/skill/edit")
    public String showSkillEditPage(Model model) {
        // HTMLの th:each="category : ${categories}" で .name を参照しているため
        // Mapを使用してダミーデータを作成
        List<Map<String, String>> categories = Arrays.asList(
            Map.of("name", "バックエンド"),
            Map.of("name", "フロントエンド"),
            Map.of("name", "インフラ")
        );
        model.addAttribute("categories", categories);
        
        return "skill-edit";
    }
}
