package com.spring.springbootapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.springbootapplication.entity.LearningData;
import java.util.List;

@Repository
public interface LearningDataRepository extends JpaRepository<LearningData, Integer> {
    // カテゴリーIDに紐づく学習項目を取得するためのメソッド
    List<LearningData> findByCategoryId(Integer categoryId);
}
