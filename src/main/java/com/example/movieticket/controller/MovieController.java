package com.example.movieticket.controller;

import org.springframework.stereotype.Controller;

import com.example.movieticket.service.user.MovieService;

@Controller
public class MovieController {
	
	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	// ==========================================
    // ① 上映映画一覧画面の表示
    // ==========================================
	@GetMapping("movies")
	
}