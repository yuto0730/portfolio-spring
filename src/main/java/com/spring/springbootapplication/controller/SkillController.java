package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.repository.CategoryRepository; 
import com.spring.springbootapplication.entity.Category; 
import java.util.List;
import java.time.LocalDate; 
import java.util.ArrayList; 

@Controller
public class SkillController {

    // データベースからデータを取得するための「リポジトリ」を準備
    @Autowired
    private CategoryRepository categoryRepository;

    // 【GET】 学習項目編集画面を表示する
    @GetMapping("/skill/edit")
    public String showSkillEditPage(Model model) {
        
        // データベースからすべてのカテゴリーを取得
        List<Category> categories = categoryRepository.findAll();
        
        // 取得したカテゴリー一覧をHTMLに渡す
        model.addAttribute("categories", categories);

        //現在の月から過去3ヶ月分のリストを作成
        List<LocalDate> months = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            months.add(now.minusMonths(i));
        }
        model.addAttribute("months", months);
        return "skill-edit";
    }
}
