package com.example.movieticket.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.movieticket.entity.User;
import com.example.movieticket.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	//使いたいRepositoryと、暗号化ツールを用意
	private final UserRepository userRepository;
	
	//用意したツールをSpring Bootから受け取る（DI：依存性の注入)
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	//implements UserDetailsServiceと定義した場合、必ず"loadUserByUsernameメソッド"を"UserDetails型"で作成しなければならない
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		//1.メアドでDBからユーザーを探す
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません"));
		
		//2.取得後、Spring Securityが読める形式に「UserDetails」を変換
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.roles("USER")
				.build();
	}
}