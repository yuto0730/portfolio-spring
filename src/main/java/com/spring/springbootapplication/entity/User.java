package com.spring.springbootapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; 
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 名前：空っぽダメ、255文字まで
    @NotBlank(message = "氏名は必ず入力してください")
    @Size(max = 255, message = "氏名は255文字以内で入力してください")
    private String name;

    // メール：空っぽダメ、メアドの形になってること
    @NotBlank(message = "メールアドレスは必ず入力してください")
    @Email(message = "メールアドレスが正しい形式ではありません")
    private String email;

    // パスワード：空っぽダメ、英数字8文字以上
    @NotBlank(message = "パスワードは必ず入力してください")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "英数字8文字以上で入力してください")
    private String password;
}
