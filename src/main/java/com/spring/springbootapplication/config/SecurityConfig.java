package com.spring.springbootapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // パスワードをハッシュ化
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            //  誰でも見れるページと、ログインが必要なページを分ける
            .authorizeHttpRequests(auth -> auth
                // 登録・ログイン画面・CSSは、ログイン前でもアクセスを許可する
                .requestMatchers("/signup", "/login", "/css/**").permitAll() 
                // それ以外のページ（マイページなど）は、ログインしていないと弾く
                .anyRequest().authenticated() 
            )
            // ログイン機能の設定
            .formLogin(login -> login
                .loginPage("/login")         // ログイン画面login.htmlを使う
                .defaultSuccessUrl("/mypage") // ログインに成功したら/mypageへ飛ばす
                .permitAll()                 // ログイン画面自体は全員に公開する
            )
            // セキュリティ設定の微調整
            // POST送信（データの保存）を許可するためにCSRF対策を無効にする
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }
}
