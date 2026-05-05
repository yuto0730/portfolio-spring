package com.spring.springbootapplication.dto;

import java.util.List;
import lombok.Data;

@Data
public class SkillChartDto {

    // カテゴリーID
    private Integer categoryId;

    // カテゴリー名
    private String categoryName;

    // 3ヶ月分の学習時間
    private List<Integer> studyTimes;
}
