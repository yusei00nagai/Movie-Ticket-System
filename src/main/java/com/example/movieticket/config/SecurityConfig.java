package com.example.movieticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// ==========================================
	// 1. パスワード暗号化マシンの登録（@Bean）
	// ==========================================
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// ==========================================
	// 2. 警備員（Spring Security）のルール設定
	// ==========================================
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// ① アクセス制限のルール
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/", // ★ここを追加：未ログインでもTOPページを見られるようにする
					"/login", "/register", "/register/confirm", 
					"/css/**", "/js/**", "/images/**", // 静的リソース
					"/movies/**", "/theaters/**", "/showtimes/**", // 閲覧系の画面
					"/api/movies/**", "/api/theaters/**", "/api/showtimes/**" // 閲覧系のAPI
				).permitAll() // これらは未ログインでも全員アクセスOK
				.anyRequest().authenticated() // それ以外のページ（座席選択、予約、マイページなど）はログイン必須
			)
			// ② ログインに関する設定
			.formLogin(login -> login
				.loginPage("/login") // ログイン画面のURL
				.usernameParameter("email") // ログイン時、IDの代わりにメアドを使う設定
				.passwordParameter("password") // パスワードの入力欄の名前
				.defaultSuccessUrl("/", true) // ログインが成功したら自動的にTOPページへ飛ばす
				.permitAll()
			)
			// ③ ログアウトに関する設定
			.logout(logout -> logout
				.logoutUrl("/logout") // ログアウトを実行するURL
				.logoutSuccessUrl("/login?logout") // ログアウトが成功したらログイン画面に戻す
				.permitAll()
			);

		return http.build();
	}
}