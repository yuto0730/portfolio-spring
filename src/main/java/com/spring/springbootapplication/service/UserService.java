package com.spring.springbootapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder; 

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
}
