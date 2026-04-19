package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; /* WF指定 */
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.spring.springbootapplication.repository.CategoryRepository; 
import com.spring.springbootapplication.entity.Category; 
import java.util.List;
import java.util.ArrayList; 
import java.time.LocalDate; 

@Controller
public class SkillController {

    @Autowired
    private CategoryRepository categoryRepository;

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
        model.addAttribute("selectedMonth", targetMonthValue); // 選択状態を保持するため
        
        return "skill-edit";
    }
}
