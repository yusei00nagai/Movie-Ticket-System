package com.example.movieticket.controller;

import org.springframework.stereotype.Controller;

import com.example.movieticket.service.user.TheaterService;

@Controller
public class TheaterController {
	
	private final TheaterService theaterService;
	
	public TheaterController(TheaterService theaterService) {
		this.theaterService = theaterService;
	}
	
	
}