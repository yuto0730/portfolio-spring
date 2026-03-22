package com.spring.springbootapplication.service;

import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.springbootapplication.entity.User;
import com.spring.springbootapplication.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        //データベースから、メールアドレスを使ってユーザーを探す
        Optional<User> optionalUser = userRepository.findByEmail(email);

        //もし、中身が空っぽなら、エラーにする
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("ユーザーが見つかりません");
        }

        //見つかった場合は、箱の中からユーザー情報を「get」で取り出す
        User user = optionalUser.get();

        //取り出したユーザーから、メアドとパスワードを抜き出す
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        return new org.springframework.security.core.userdetails.User(
            userEmail,
            userPassword,
            AuthorityUtils.createAuthorityList("ROLE_USER") 
        );
    }
}
