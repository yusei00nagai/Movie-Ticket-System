package com.example.movieticket.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movieticket.entity.Movie;
import com.example.movieticket.service.user.MovieService;

@Controller
public class MovieController {
	
	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	// ==========================================
    // ① 上映映画・公開予定映画 一覧画面の表示
    // ==========================================
	@GetMapping("/movies")
	public String showMovieList(
		@RequestParam(required = false) String status, Model model) {
		
		//映画リストを入れる空箱を用意
		List<Movie> movies;
		
		//URLパラメータを見て、上映中 or 公開予定かを判断
		if("upcoming".equals(status)) {
			//「公開予定」タブ押下時
			movies = movieService.getUpcomingMovies();
		} else {
			//初期時 or 「上映中」タブ押下時
			movies = movieService.getNowShowingMovies();
		}
		
		//映画一覧をmodelにセット
		model.addAttribute("movies", movies);
		
		//ステータスをmodelにセット
		model.addAttribute("status", status);
		
		return "movies/movies";
	}
	
	// ==========================================
	// ② 映画詳細画面の表示
	// ==========================================
	@GetMapping("/movies/{id}")
	public String showMovieDetail(@PathVariable("id") Long id, Model model) {
	    //ServiceからIDをもとに映画を1件取得
	    Optional<Movie> movieOpt = movieService.getMovieById(id);
	    
	    //存在チェック
	    if(movieOpt.isPresent()) {
	        // 中身を取り出して "movie" という名前で渡す
	        model.addAttribute("movie", movieOpt.get());
	        return "movies/movie-detail";
	    } else {
	        //映画が見つからない場合、一覧画面へリダイレクト
	        return "redirect:/movies";
	    }
	}
}