package com.example.movieticket.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movieticket.dto.TheaterScheduleDto;
import com.example.movieticket.entity.Movie;
import com.example.movieticket.service.user.MovieService;
import com.example.movieticket.service.user.ShowtimeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MovieController {
	
	private final MovieService movieService;
	private final ShowtimeService showtimeService;
	
	public MovieController(MovieService movieService, ShowtimeService showtimeService) {
		this.movieService = movieService;
		this.showtimeService = showtimeService;
		
	}
	
	// ==========================================
    // ① 上映映画・公開予定映画 一覧画面の表示
    // ==========================================
	@GetMapping("/movies")
	public String showMovieList(
		@RequestParam(required = false) String status, Model model) {
		
		log.info("画面遷移: 映画一覧画面の表示処理を開始します [status={}]", status);
		
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
		
		log.info("画面遷移: 映画一覧画面(movies/movies)へ遷移します");
		return "movies/movies";
	}
	
	// ==========================================
	// ② 映画詳細画面の表示
	// ==========================================
	@GetMapping("/movies/{id}")
	public String showMovieDetail(@PathVariable("id") Long id, Model model) {
		
		log.info("画面遷移: 映画詳細画面の表示処理を開始します [映画ID={}]", id);
		
	    //ServiceからIDをもとに映画を1件取得
	    Optional<Movie> movieOpt = movieService.getMovieById(id);
	    
	    //存在チェック
	    if(movieOpt.isPresent()) {
	    	
	        // 中身を取り出して "movie" という名前で渡す
	        model.addAttribute("movie", movieOpt.get());
	        
	        //TheaterServiceから、劇場一覧を取得
	        Map<String, List<TheaterScheduleDto>> theaterMap = showtimeService.getScheduleMapByMovie(id);
	        
	        //劇場情報をmodeにセット
	        model.addAttribute("theaterMap", theaterMap);
	        
	        log.info("画面遷移: 映画詳細画面(movies/movie-detail)へ遷移します [映画名={}]", movieOpt.get().getTitle());
	        
	        return "movies/movie-detail";
	    } else {
	    	
	    	log.warn("画面遷移: 指定された映画が見つからないため、一覧へリダイレクトします [映画ID={}]", id);
	    	
	        //映画が見つからない場合、一覧画面へリダイレクト
	        return "redirect:/movies";
	    }
	}
}