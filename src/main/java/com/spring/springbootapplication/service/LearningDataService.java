package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.dto.SkillChartDto;
import com.spring.springbootapplication.repository.LearningDataRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LearningDataService {

    @Autowired
    private LearningDataRepository learningDataRepository;

    // 学習時間を更新するメソッド
    public void updateStudyTime(Integer id, Integer studyTime) {
        
        if (id == null) {
            return;
        }

        // 受け取ったIDを使って、データベースから該当の学習データを探し出す
        LearningData learningData = learningDataRepository.findById(id).orElse(null);
        
        // データが無事に見つかった場合のみ、上書き処理を行う
        if (learningData != null) {
            // 学習時間を新しい値にセットする
            learningData.setStudyTime(studyTime);
            // 更新日時も「今」の時間に上書きしておく
            learningData.setUpdatedAt(LocalDateTime.now());
            
            // 変更した内容をデータベースに保存（UPDATE）する
            learningDataRepository.save(learningData);
        }
    }

    // 学習データを削除するメソッド
    public void deleteLearningData(Integer id) {
        if (id != null) {
            learningDataRepository.deleteById(id);
        }
    }

    // スキルチャートのデータを取得するメソッド
    public List<SkillChartDto> getSkillChartData(Integer userId) {
        return null;
    }
}
