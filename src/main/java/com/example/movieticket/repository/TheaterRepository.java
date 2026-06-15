package com.example.movieticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Theater;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
	
	//劇場の名称検索（あいまい検索）
	List<Theater> findByNameContaining(String keyword);
}