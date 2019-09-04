package com.tensquare.articlespider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 引导类就搞定了
 * @author Johnny.Chen
 *
 */
/*@SpringBootApplication
@EnableDiscoveryClient*/
@SpringCloudApplication
//开启远程调用
@EnableFeignClients

//开启定时启动
@EnableScheduling
public class ArticleSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleSpiderApplication.class, args);
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
