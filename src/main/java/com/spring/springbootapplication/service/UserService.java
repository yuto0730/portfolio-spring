package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public User registerUser(User user) {
        
        // 入力が空っぽじゃないかチェック
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new RuntimeException("氏名は必ず入力してください");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new RuntimeException("メールアドレスは必ず入力してください");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("パスワードは必ず入力してください");
        }

        // メールアドレスの重複チェック（DBを確認）
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("このメールアドレスは既に登録されています");
        }

        //パスワードを暗号（ハッシュ形式）に変える
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // すべてOKなら保存
        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        // リポジトリからメールアドレスで検索して、見つかったら返し、なければ null を返す
        return userRepository.findByEmail(email).orElse(null);
    }

    // プロフィール（自己紹介と画像）を更新するメソッド
    public void updateUserProfile(User user, com.spring.springbootapplication.form.ProfileEditForm form) throws IOException {
        //自己紹介文をセット
        user.setSelfIntroduction(form.getSelfIntroduction());

        // 画像の保存処理
        MultipartFile file = form.getProfileImage();
        if (file != null && !file.isEmpty()) {
            /* // ファイル名を取得
            String fileName = file.getOriginalFilename();
            // 保存先のパスを指定（さっき作ったフォルダ）
            Path uploadPath = Paths.get("src/main/resources/static/images/profile/");
            // 実際にファイルを指定した場所に保存する
            file.transferTo(uploadPath.resolve(fileName));
            // DBに保存するために、エンティティにファイル名をセットする
            user.setProfileImageName(fileName);
            */

            // DBにバイトデータを直接保存
            user.setProfileImage(file.getBytes());
        }

        //DBを更新（保存）
        userRepository.save(user);
    }
}
