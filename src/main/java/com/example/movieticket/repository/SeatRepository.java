package com.example.movieticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
	
	//座席選択画面で、特定のスクリーンの全座席（座席マップ）を取得
	List<Seat> findByScreenId(Long screenId);
}