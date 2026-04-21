package com.spring.springbootapplication.form;

import lombok.Data;

//項目追加画面用のフォームクラス
@Data
public class SkillAddForm {

    private String name; //項目名

    private Integer studyTime; //学習時間（分）
}
