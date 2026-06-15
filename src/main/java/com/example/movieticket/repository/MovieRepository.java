package com.example.movieticket.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticket.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	// ① 上映中の映画一覧（公開日が「指定した日付(今日)」以下のもの）を、公開日が新しい順に取得
    List<Movie> findByReleaseDateLessThanEqualOrderByReleaseDateDesc(LocalDate date);
    
    // ② 公開予定の映画一覧（公開日が「指定した日付(今日)」より大きいもの）を、公開日が近い順に取得
    List<Movie> findByReleaseDateGreaterThanOrderByReleaseDateAsc(LocalDate date);
    
    // （おまけ）もし条件関係なく、単純に「全映画を公開日の新しい順に一覧表示したい」だけならコレ！
    List<Movie> findAllByOrderByReleaseDateDesc();
    
}