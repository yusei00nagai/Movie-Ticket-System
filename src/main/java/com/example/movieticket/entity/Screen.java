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
@Table(name ="screens", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"theater_id", "name"})
})
@Data
@EntityListeners(AuditingEntityListener.class)
public class Screen {
	//スクリーンID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//所属劇場ID
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "theater_id", nullable = false)
	private Theater theater;
	
	//スクリーン名
	@Column(nullable = false)
	private String name;
	
	//作成日時
	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	//更新日時
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}