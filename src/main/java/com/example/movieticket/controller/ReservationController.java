package com.example.movieticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/reservations")
@Slf4j
public class ReservationController {
	
	// ==========================================
    // ① 座席画面の表示
    // ==========================================
	@GetMapping("/seat")
	public String showSeatSelection(@RequestParam("showtimeId") Long showtimeId, Model model) {
		
		log.info("画面遷移: 座席選択画面の表示処理を開始します [上映回ID={}]", showtimeId);
		
		// TODO: Service作成予定
		
		model.addAttribute("showtimeId", showtimeId);
		
		log.info("画面遷移: 座席選択画面(reservations/seat)へ遷移します");
		
		return "reservations/seat";
		
	}
}