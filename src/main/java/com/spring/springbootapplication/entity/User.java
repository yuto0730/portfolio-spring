package com.spring.springbootapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; 
import lombok.Data;
import java.time.LocalDateTime; 

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // 名前：必須、255文字まで
    @NotBlank(message = "氏名は必ず入力してください")
    @Size(max = 255, message = "氏名は255文字以内で入力してください")
    @Column(name = "name")
    private String name;

    // メール：必須、正しい形式であること
    @NotBlank(message = "メールアドレスは必ず入力してください")
    @Email(message = "メールアドレスが正しい形式ではありません")
    @Column(name = "email")
    private String email;

    // パスワード：必須、英数字8文字以上
    @NotBlank(message = "パスワードは必ず入力してください")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{8,}$", message = "英数字8文字以上で入力してください")
    @Column(name = "password")
    private String password;

    // 自己紹介文：200文字以下
    @Size(max = 200, message = "自己紹介は200文字以下で入力してください")
    @Column(name = "bio", length = 200)
    private String bio;

    // プロフィール画像（画像データそのものをDBに直接保存）
    @Column(name = "profile_image", columnDefinition = "bytea")
    private byte[] profileImage;

    // プロフィール画像のファイル名を保存する項目
    @Column(name = "profile_image_name")
    private String profileImageName;

    // 作成日時
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 更新日時
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
