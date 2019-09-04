package com.tensquare.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtil;


@SpringBootApplication
//开启springcache
@EnableCaching
public class GatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatheringApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker();
	}
	//创建jwtUtil
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
}
