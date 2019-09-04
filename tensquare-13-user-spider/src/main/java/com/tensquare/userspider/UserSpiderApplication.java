package com.tensquare.userspider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import us.codecraft.webmagic.scheduler.RedisScheduler;

@SpringCloudApplication
//开启远程调用
@EnableFeignClients

//开启定时启动
@EnableScheduling
public class UserSpiderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserSpiderApplication.class, args);
	}
	
	//注入redis的ip地址
	@Value("${spring.redis.host}")
	private String host;
	
	//url去重使用的
	@Bean
	public RedisScheduler redisScheduler() {
		return new RedisScheduler(host);
	}

}
