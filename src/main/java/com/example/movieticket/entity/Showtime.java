package com.example.movieticket.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "showtimes")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Showtime {
	//上映ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//映画ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", nullable = false)
	private Movie movie;
	
	//スクリーンID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screen_id", nullable = false)
	private Screen screen;
	
	//上映開始時刻
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;
	
	//上映終了時刻
	@Column(name = "end_time", nullable = false)
	private LocalDateTime endTime;
	
	//作成日時
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//更新日時
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}
