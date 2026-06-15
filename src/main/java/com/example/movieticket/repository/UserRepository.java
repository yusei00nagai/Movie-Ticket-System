package com.example.movieticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    //新規登録用：メールアドレスが既に存在するかを true/false で取得
    boolean existsByEmail(String email);
    
    //ログイン用：メールアドレスからユーザー情報を丸ごと取得
    Optional<User> findByEmail(String email);
    
}