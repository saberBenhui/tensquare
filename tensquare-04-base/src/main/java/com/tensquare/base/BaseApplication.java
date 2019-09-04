package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import util.IdWorker;

/**
 * 
 * @author Johnny.Chen
 *
 */
@SpringBootApplication//引导类
@EnableDiscoveryClient//服务发现
public class BaseApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BaseApplication.class, args);
	}
	
	/**
	 * 把idwork给Spring容器管理
	 */
	@Bean
	public IdWorker idWorker() {
		return new IdWorker();
	}

}
