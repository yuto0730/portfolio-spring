package com.spring.springbootapplication.form;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileEditForm {

    // 要件：50文字以上200文字以下
    // 初期値を空文字("")にしておくことで、入力がない場合は「空っぽ」としてエラーになるようにする
    @Size(min = 50, max = 200, message = "自己紹介は50文字以上200文字以下で入力してください")
    private String selfIntroduction = "";

    // 画像ファイルを受け取るための型
    private MultipartFile profileImage;
}
