package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.repository.CategoryRepository; 
import com.spring.springbootapplication.entity.Category; 
import com.spring.springbootapplication.form.SkillAddForm; // 追加
import java.util.List;
import java.util.ArrayList; 
import java.time.LocalDate; 

@Controller
public class SkillController {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * スキル編集画面（一覧）の表示
     */
    @GetMapping("/skill/edit")
    public String showSkillEditPage(
            @RequestParam(name = "month", required = false) Integer monthParam, 
            Model model) {
        
        // 表示する月の決定（指定がなければ当月）
        LocalDate now = LocalDate.now();
        int targetMonthValue = (monthParam != null) ? monthParam : now.getMonthValue();
        
        // 月の選択肢リスト作成（今月・先月・先々月）
        List<LocalDate> months = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            months.add(now.minusMonths(i));
        }

        // カテゴリー一覧取得
        List<Category> categories = categoryRepository.findAll();
        
        model.addAttribute("categories", categories);
        model.addAttribute("months", months);
        model.addAttribute("selectedMonth", targetMonthValue);
        
        return "skill-edit";
    }

    // 項目追加画面の表示
    @GetMapping("/skill/new")
    public String showAddForm(
            @RequestParam("month") Integer month,
            @RequestParam("categoryId") Integer categoryId,
            Model model) {

        // IDからカテゴリー情報を取得（名前を表示するため）
        Category category = categoryRepository.findById(categoryId).orElse(null);
        String categoryName = (category != null) ? category.getName() : "不明なカテゴリー";

        // 画面に必要なデータを渡す
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("skillAddForm", new SkillAddForm()); // 空のフォームを渡す

        return "skill-new";
    }
}
