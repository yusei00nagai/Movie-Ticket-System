package com.example.movieticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieTicketSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketSystemApplication.class, args);
	}
}

