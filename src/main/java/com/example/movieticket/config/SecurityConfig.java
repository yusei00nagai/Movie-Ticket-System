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
                .requestMatchers("/login", "/register", "/register/confirm", "/css/**", "/js/**").permitAll() // ログインや登録画面、デザインファイルは全員アクセスOK
                .anyRequest().authenticated() // それ以外のページ（映画予約など）は全員ログインが必要
            )
            // ② ログインに関する設定
            .formLogin(login -> login
                .loginPage("/login") // ログイン画面のURL
                .usernameParameter("email") // ログイン時、IDの代わりにメアドを使う設定
                .passwordParameter("password") // パスワードの入力欄の名前
                .defaultSuccessUrl("/movies", true) // ログインが成功したら自動的に映画一覧ページへ飛ばす
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