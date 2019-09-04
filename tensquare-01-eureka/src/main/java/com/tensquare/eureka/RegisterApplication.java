package com.tensquare.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 注册中心的启动类
 * @author Johnny.Chen
 *
 */
@SpringBootApplication//引导类
@EnableEurekaServer//开启eureka的服务端
public class RegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterApplication.class, args);

	}

}
