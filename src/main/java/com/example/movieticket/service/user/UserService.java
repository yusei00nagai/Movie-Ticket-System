package com.example.movieticket.service.user;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movieticket.entity.User;
import com.example.movieticket.repository.UserRepository;

@Service
public class UserService {
	
	//使いたいRepositoryと、暗号化ツールを用意
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	//用意したツールをSpring Bootから受け取る（DI：依存性の注入）
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	// ==========================================
	// ① 新規会員登録確認メソッド
	// ==========================================
	@Transactional
	public void registerUserConfirm(String name, String email, String rawPassword) {
		boolean exists = userRepository.existsByEmail(email);
		if (exists) {
			throw new IllegalArgumentException("このメールアドレスはすでに登録されています。");
		}
	}
	
	
	// ==========================================
	// ② 新規会員登録メソッド（DBへ保存）
	// ==========================================
	@Transactional
	public void registerUser(String name, String email, String rawPassword) {
		
		// 1. メールアドレスの重複チェック
		boolean exists = userRepository.existsByEmail(email);
		if (exists) {
			throw new IllegalArgumentException("このメールアドレスはすでに登録されています。");
		}
		
		// 2. 生のパスワードを暗号化ツールで変換
		String encodedPassword = passwordEncoder.encode(rawPassword);
		
		// 3. 新しいUserエンティティを作成し、データをDBに保存
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(encodedPassword);
		newUser.setRole("USER");
		
		userRepository.save(newUser);
	}
}