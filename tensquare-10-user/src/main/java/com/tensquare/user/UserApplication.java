package com.tensquare.user;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import util.IdWorker;
import util.JwtUtil;

/**
 * 用户微服务的引导类
 * @author Johnny.Chen
 *
 */
@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public IdWorker idWorker(){
		return new IdWorker();
	}
	
	//加密使用
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
	   return new BCryptPasswordEncoder();
	}
	
	//创建jwtUtil
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
	
}
