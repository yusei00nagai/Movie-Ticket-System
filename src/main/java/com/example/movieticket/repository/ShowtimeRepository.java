package com.example.movieticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Showtime;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime,Long > {
	
	//映画詳細画面で、その映画の全スケジュールを取得
	List<Showtime> findByMovieId(Long movieId);
	
	//劇場詳細画面で、その劇場の上映スケジュールをスクリーン越しに取得
	List<Showtime> findByScreenTheaterId(Long theaterId);
}