package com.example.movieticket.entity;

import java.time.LocalDate;
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
@Table(name = "movies")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Movie {
	//映画ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//映画タイトル
	@Column(nullable = false)
	private String title;
	
	//あらすじ
	@Column(columnDefinition = "TEXT")
	private String description;
	
	//上映時間（分）
	@Column(nullable = false)
	private Integer duration;
	
	//公開日
	@Column(name = "release_date")
	private LocalDate releaseDate;
	
	//作成日次
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//更新日時
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}