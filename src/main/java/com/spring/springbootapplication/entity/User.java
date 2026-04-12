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

    // 自己紹介文(50文字以上200文字以下)
    // 新規登録を邪魔しないよう、ここでの「50文字以上」のチェックを外す
    @Size(max = 200, message = "自己紹介は200文字以下で入力してください")
    @Column(name = "self_introduction", length = 200)
    private String selfIntroduction;

    // プロフィール画像（ファイル名を保存）
    // Renderではファイルが消えるためコメントアウト
    // @Column(name = "profile_image_name")
    // private String profileImageName;

    // プロフィール画像（画像データそのものをDBに保存）
    // PostgreSQLのbytea型を指定することで、画像バイナリを直接保存できるようにする
    @Lob
    @Column(name = "profile_image", columnDefinition = "bytea")
    private byte[] profileImage;
}
