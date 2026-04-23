package com.spring.springbootapplication.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SkillAddForm {

    // 項目名：空ではない、50文字以内
    @NotBlank(message = "項目名は必ず入力してください")
    @Size(max = 50, message = "項目名は50文字以内で入力してください")
    private String name;

    // 学習時間：空ではない、0以上の数字
    @NotNull(message = "学習時間は必ず入力してください")
    @Min(value = 0, message = "学習時間は0以上の数字で入力してください")
    private Integer study_time;
}
