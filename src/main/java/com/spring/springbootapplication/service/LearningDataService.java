package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.entity.Category;
import com.spring.springbootapplication.entity.LearningData;
import com.spring.springbootapplication.dto.SkillChartDto;
import com.spring.springbootapplication.repository.CategoryRepository;
import com.spring.springbootapplication.repository.LearningDataRepository;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class LearningDataService {

    @Autowired
    private LearningDataRepository learningDataRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
        if (userId == null) {
            return null;
        }

        // 直近3ヶ月の期間を算出する
        LocalDate now = LocalDate.now();
        LocalDate startMonth = now.minusMonths(2).withDayOfMonth(1);
        LocalDate endMonth = now.withDayOfMonth(now.lengthOfMonth());

        // ユーザーIDと指定期間に紐づくデータを取得する
        List<LearningData> learningDataList = learningDataRepository.findByUserIdAndStudyMonthBetween(userId, startMonth, endMonth);

        // すべてのカテゴリー情報を取得する
        List<Category> categories = categoryRepository.findAll();
        List<SkillChartDto> chartDataList = new ArrayList<>();

        // カテゴリーごとに直近3ヶ月分の学習時間を合計する
        for (Category category : categories) {
            SkillChartDto dto = new SkillChartDto();
            dto.setCategoryId(category.getId());
            dto.setCategoryName(category.getName());

            List<Integer> times = new ArrayList<>();
            // 先々月、先月、今月の順にループ処理を行う
            for (int i = 2; i >= 0; i--) {
                LocalDate targetMonth = now.minusMonths(i).withDayOfMonth(1);

                // 対象のカテゴリーかつ対象の学習月に合致するデータの学習時間を合計
                int totalTime = learningDataList.stream()
                        .filter(data -> data.getCategoryId().equals(category.getId()))
                        .filter(data -> data.getStudyMonth().withDayOfMonth(1).equals(targetMonth))
                        .mapToInt(LearningData::getStudyTime)
                        .sum();

                times.add(totalTime);
            }

            dto.setStudyTimes(times);
            chartDataList.add(dto);
        }

        return chartDataList;
    }
}
