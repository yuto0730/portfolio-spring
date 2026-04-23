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
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // カテゴリーID
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    // 学習項目名：50文字以内
    @Column(name = "name", length = 50, nullable = false) 
    private String name;

    // 学習時間
    @Column(name = "study_time", nullable = false)
    private Integer studyTime;

    // 学習月
    @Column(name = "study_month", nullable = false)
    private LocalDate studyMonth;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
