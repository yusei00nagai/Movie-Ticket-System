package com.example.movieticket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movieticket.service.user.UserService;

@Controller
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
        this.userService = userService;
    }
	
	// ==========================================
    // ① ログイン画面の表示
    // ==========================================
	@GetMapping("/login")
	public String showLoginForm( ) {
		return "auth/login";
	}
	
	// ==========================================
    // ② 新規登録画面の表示
    // ==========================================
	@GetMapping("/register")
	public String showRegisterForm() {
		return "auth/register";
	}
	
	// ==========================================
	// ③ 新規登録の実行処理
	// ==========================================
	@PostMapping("/register")
	public String registerUser(
			@RequestParam String name, 
			@RequestParam String email,
			@RequestParam String rawPassword,
			RedirectAttributes redirectAttributes) {

		try {
			// UserServiceの新規会員登録メソッドを呼び出し、値を渡す
			userService.registerUser(name, email, rawPassword);

			// 成功メッセージをセットしてログイン画面へリダイレクト
			redirectAttributes.addFlashAttribute("successMessage", "会員登録が完了しました。ログインしてください。");
			return "redirect:/login";

		} catch (Exception e) {
			// 登録失敗時（重複エラーなど）はエラーメッセージをセットして登録画面へ戻す
			redirectAttributes.addFlashAttribute("errorMessage", "登録に失敗しました。このメールアドレスは既に登録されている可能性があります。");
			return "redirect:/register";
		}
	}
}