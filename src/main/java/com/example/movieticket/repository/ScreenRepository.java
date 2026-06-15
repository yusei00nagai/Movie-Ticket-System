package com.example.movieticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {
	
	//劇場管理画面などで、特定の劇場に所属するスクリーン一覧を取得
	List<Screen> findByTheaterId(Long theaterId);
	
}