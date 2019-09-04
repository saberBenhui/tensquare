package com.tensquare.articlespider.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tensquare.articlespider.pipeline.ArticlePipeline;
import com.tensquare.articlespider.processer.ArticleProcesser;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.RedisScheduler;

@Component
public class ArticleSpiderTask {
	
	@Autowired
	private ArticleProcesser articleProcesser;
	
	@Autowired
	private ArticlePipeline articlePipeline;
	
	@Autowired
	private RedisScheduler redisScheduler;
	
	
	@Value("${article.http_path}")
	private String path;
	
	@Scheduled(cron="21 24 12 * * ? ")
	public void task1() {
		Spider
		.create(articleProcesser)
		.setScheduler(redisScheduler)
		.addPipeline(articlePipeline)
		.addUrl(path)
		.start();
	}

}
