package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关引导类
 * @author Johnny.Chen
 *
 */
@SpringBootApplication //引导类
@EnableDiscoveryClient //服务发现
@EnableZuulProxy 	//开启网关代理
public class GatewayApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
