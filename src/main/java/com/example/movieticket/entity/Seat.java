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
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "seats", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"screen_id", "seat_number"})
})
@Data
@EntityListeners(AuditingEntityListener.class)
public class Seat {
	//座席ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//スクリーンID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "screen_id", nullable = false)
	private Screen screen;
	
	//座席番号
	@Column(name = "seat_number", nullable = false)
	private String seatNumber;
	
	//作成日時
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//更新日時
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}