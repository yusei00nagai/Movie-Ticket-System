package com.example.movieticket.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User {
	//ユーザーID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//表示用ユーザー名
	@Column(nullable = false)
	private String name;
	
	//ログインID
	@Column(nullable = false, unique = true)
	private String email;
	
	//ハッシュ化パスワード
	@Column(nullable = false, length = 255)
	private String password;
	
	//権限
	@Column(nullable = false)
	private String role;
	
	//作成日時
	//@EntityListenersを記載しないと@CreatedDateは使用できない
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//更新日時
	//@EntityListenersを記載しないと@LastModifiedDateは使用できない
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
}