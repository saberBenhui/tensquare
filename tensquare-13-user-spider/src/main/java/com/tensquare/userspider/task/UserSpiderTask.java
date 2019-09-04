package com.tensquare.userspider.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tensquare.userspider.pipeline.UserPipeline;
import com.tensquare.userspider.processer.UserProcesser;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class UserSpiderTask {
	
	@Autowired
	private UserProcesser userProcesser;
	
	@Autowired
	private UserPipeline userPipeline;
	
	@Autowired
	private RedisScheduler redisScheduler;
	
	
	@Value("${article.http_path}")
	private String path;
	
	@Scheduled(cron="11 27 13 * * ? ")
	public void task1() {
		Spider
		.create(userProcesser)
		.setScheduler(redisScheduler)
		.addPipeline(userPipeline)
		.addUrl(path)
		.start();
	}

}
