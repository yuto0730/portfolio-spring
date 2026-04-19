package com.spring.springbootapplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "learning_data")
@Data
public class LearningData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // ID

    // ユーザーID
    @Column(name = "user_id")
    private Integer userId;

    // カテゴリーID
    @Column(name = "category_id")
    private Integer categoryId;

    // 学習項目名：50文字以内
    @Column(name = "name", length = 50) /* WF指定 */
    private String name;

    // 学習時間
    @Column(name = "study_time")
    private Integer studyTime;

    // 学習月
    @Column(name = "study_month")
    private LocalDate studyMonth;

    // 作成日時
    @Column(name = "created_id")
    private LocalDateTime createdId;

    // 更新日時
    @Column(name = "updated_id")
    private LocalDateTime updatedId;
}
