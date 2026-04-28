package com.spring.springbootapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import jakarta.validation.Valid;
import java.security.Principal;

import com.spring.springbootapplication.repository.CategoryRepository; 
import com.spring.springbootapplication.repository.LearningDataRepository;
import com.spring.springbootapplication.repository.UserRepository;
import com.spring.springbootapplication.entity.Category; 
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.form.SkillAddForm; 
import com.spring.springbootapplication.service.LearningDataService;

import java.util.List;
import java.util.ArrayList; 
import java.time.LocalDate; 
import java.time.LocalDateTime;

@Controller
public class SkillController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LearningDataRepository learningDataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LearningDataService learningDataService;

    //スキル編集画面（一覧）の表示
    @GetMapping("/skill/edit")
    public String showSkillEditPage(
            @RequestParam(name = "month", required = false) Integer monthParam, 
            Principal principal, 
            Model model) {
        
        // 表示する月の決定（指定がなければ当月）
        LocalDate now = LocalDate.now();
        int targetMonthValue = (monthParam != null) ? monthParam : now.getMonthValue();
        
        // 検索用に「選択された月の1日」のLocalDateを作成
        LocalDate targetStudyMonth = LocalDate.of(now.getYear(), targetMonthValue, 1);
        
        // 月の選択肢リスト作成（今月・先月・先々月）
        List<LocalDate> months = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            months.add(now.minusMonths(i));
        }

        // カテゴリー一覧取得
        List<Category> categories = categoryRepository.findAll();
        
        // ログインユーザー かつ 「選択された月」の学習データのみを取得してModelに渡す
        if (principal != null) {
            User user = userRepository.findByEmail(principal.getName()).orElse(null);
            if (user != null) {
                // repositoryのメソッドを呼び出して、ユーザーIDと選択された月に紐づく学習データを取得
                List<LearningData> learningDataList = learningDataRepository.findByUserIdAndStudyMonthOrderByIdAsc(user.getId(), targetStudyMonth);
                model.addAttribute("learningDataList", learningDataList);
            }
        }
        
        model.addAttribute("categories", categories);
        model.addAttribute("months", months);
        model.addAttribute("selectedMonth", targetMonthValue);
        model.addAttribute("skillAddForm", new SkillAddForm());
        
        return "skill-edit";
    }

    // 項目追加画面の表示
    @GetMapping("/skill/new")
    public String showAddForm(
            @RequestParam("month") Integer month,
            @RequestParam("categoryId") Integer categoryId,
            Model model) {

        // IDからカテゴリー情報を取得（名前を表示するため）
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElse(null);
        }
        String categoryName = (category != null) ? category.getName() : "不明なカテゴリー";

        // 画面に必要なデータを渡す
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("skillAddForm", new SkillAddForm()); // 空のフォームを渡す

        return "skill-new";
    }

    @PostMapping("/skill/new")
    public String registerSkill(
            @RequestParam("month") Integer monthParam,
            @RequestParam("categoryId") Integer categoryId,
            @ModelAttribute @Valid SkillAddForm skillAddForm,
            BindingResult result,
            Principal principal,
            Model model) {

        // 未ログイン時のnullチェックを追加して500エラーを防止
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        
        // ユーザーがDBに存在しない場合の安全策
        if (user == null) {
            return prepareAddForm(monthParam, categoryId, model);
        }

        Integer userId = user.getId(); 
        LocalDate studyMonth = LocalDate.of(LocalDate.now().getYear(), monthParam, 1);

        // 重複チェックをhasErrorsの前に移動し、アノテーションエラーと同時に拾えるようにする
        if (skillAddForm.getName() != null && !skillAddForm.getName().isEmpty()) {
            if (learningDataRepository.findByUserIdAndCategoryIdAndStudyMonthAndName(
                    userId, categoryId, studyMonth, skillAddForm.getName()).isPresent()) {
                
                String errorMessage = skillAddForm.getName() + "は既に登録されています";
                result.rejectValue("name", "error.duplicate", errorMessage);
            }
        }

        // 入力エラー（未入力、文字数、重複など）があれば画面を戻す
        if (result.hasErrors()) {
            return prepareAddForm(monthParam, categoryId, model);
        }

        // 保存処理
        LearningData data = new LearningData();
        data.setUserId(userId);
        data.setCategoryId(categoryId);
        data.setStudyMonth(studyMonth);
        data.setName(skillAddForm.getName());
        data.setStudyTime(skillAddForm.getStudy_time());
        data.setCreatedAt(LocalDateTime.now());
        data.setUpdatedAt(LocalDateTime.now());

        learningDataRepository.save(data);

        // 保存後はリダイレクトせず、モーダルを表示するために現在の画面を再表示する
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElse(null);
        }
        model.addAttribute("categoryName", (category != null) ? category.getName() : "不明なカテゴリー");
        model.addAttribute("selectedMonth", monthParam);
        
        // 成功の合図（これがある時だけHTMLのモーダルが表示される）
        model.addAttribute("isSuccess", true); 

        return "skill-new";
    }

    private String prepareAddForm(Integer month, Integer categoryId, Model model) {
        Category category = null;
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId).orElse(null);
        }
        model.addAttribute("categoryName", (category != null) ? category.getName() : "不明なカテゴリー");
        model.addAttribute("selectedMonth", month);
        return "skill-new";
    }

    @PostMapping("/skill/update")
    public String updateSkillTime(
            @RequestParam("id") Integer id,
            @RequestParam("studyTime") Integer studyTime,
            @RequestParam("selectedMonth") Integer selectedMonth,
            RedirectAttributes redirectAttributes) { 
        
        // 更新する項目の「名前」を取得しておく（モーダルに表示するため）
        LearningData data = learningDataRepository.findById(id).orElse(null);
        String itemName = (data != null) ? data.getName() : "";

        // サービスを呼び出して学習時間を更新する
        learningDataService.updateStudyTime(id, studyTime);
        
        // モーダル表示用のフラグと項目名を、リダイレクト先に渡す
        redirectAttributes.addFlashAttribute("isSuccess", true);
        redirectAttributes.addFlashAttribute("updatedItemName", itemName);
        
        return "redirect:/skill/edit?month=" + selectedMonth;
    }

    //項目の削除処理
    @PostMapping("/skill/delete")
    public String deleteSkill(
            @RequestParam("id") Integer id,
            @RequestParam("selectedMonth") Integer selectedMonth,
            RedirectAttributes redirectAttributes) {
        
        // 削除する前に、項目の「名前」を取得しておく（モーダルに表示するため）
        LearningData data = learningDataRepository.findById(id).orElse(null);
        String itemName = (data != null) ? data.getName() : "";

        // Serviceの削除処理を呼び出して、データベースから該当の項目を消す
        learningDataService.deleteLearningData(id);
        
        // モーダル表示用のフラグと項目名を、リダイレクト先に渡す
        redirectAttributes.addFlashAttribute("isDeleteSuccess", true);
        redirectAttributes.addFlashAttribute("deletedItemName", itemName);
        
        // 削除が終わったら、今開いていた月の一覧画面に戻す
        return "redirect:/skill/edit?month=" + selectedMonth;
    }
}
