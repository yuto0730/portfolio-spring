package com.spring.springbootapplication.repository;

import com.spring.springbootapplication.entity.LearningData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface LearningDataRepository extends JpaRepository<LearningData, Integer> {

    // カテゴリーIDに紐づく学習項目を取得するためのメソッド
    List<LearningData> findByCategoryId(Integer categoryId);

    // ユーザーID、カテゴリーID、学習月に紐づくデータを取得する
    List<LearningData> findByUserIdAndCategoryIdAndStudyMonth(Integer userId, Integer categoryId, LocalDate studyMonth);

    // 重複チェック：同じユーザー、同じカテゴリー、同じ月、同じ名前のデータが既に存在するか確認する
    Optional<LearningData> findByUserIdAndCategoryIdAndStudyMonthAndName(Integer userId, Integer categoryId, LocalDate studyMonth, String name);

    // ユーザーIDと学習月でデータを一覧取得するメソッド
    List<LearningData> findByUserIdAndStudyMonth(Integer userId, LocalDate studyMonth);
}
