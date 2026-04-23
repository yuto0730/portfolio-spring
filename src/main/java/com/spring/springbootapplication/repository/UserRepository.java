package com.spring.springbootapplication.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.springbootapplication.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    //メールアドレスでユーザーを探す
    Optional<User>findByEmail(String email);
}
