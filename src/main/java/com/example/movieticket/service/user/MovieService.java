package com.example.movieticket.service.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.movieticket.entity.Movie;
import com.example.movieticket.repository.MovieRepository;

@Service
public class MovieService {
	
	//使いたいRepositoryを用意
	private final MovieRepository movieRepository;
	
	//用意したツールをSpring bootから受け取る（DI：依存性の注入）
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	
	// ==========================================
	// ① 上映中映画一覧取得メソッド（新しい順）
	// ==========================================
	public List<Movie> getNowShowingMovies() {
		
		//今日以前に公開された映画一覧を取得
		return movieRepository.findByReleaseDateLessThanEqualOrderByReleaseDateDesc(LocalDate.now());
	}
	
	// ==========================================
	// ② 公開予定映画一覧取得メソッド
	// ==========================================
	public List<Movie> getUpcomingMovies() {
		
		//今日以降に公開される映画一覧を取得
		return movieRepository.findByReleaseDateGreaterThanOrderByReleaseDateAsc(LocalDate.now());
	}
	
	// ==========================================
	// ③ 映画一覧取得メソッド
	// ==========================================
	public List<Movie> getAllMovies() {
		
		//全ての映画を取得
		return movieRepository.findAllByOrderByReleaseDateDesc();
	}
}