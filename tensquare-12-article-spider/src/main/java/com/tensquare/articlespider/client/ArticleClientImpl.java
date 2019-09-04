package com.tensquare.articlespider.client;

import org.springframework.stereotype.Component;

import com.tensquare.articlespider.pojo.Article;

import entity.Result;
import entity.StatusCode;

/**
 * 熔断的类
 * @author Johnny.Chen
 *
 */
@Component
public class ArticleClientImpl implements ArticleClient{

	@Override
	public Result add(Article article) {
		return new Result(false,StatusCode.REMOTE_ERROR,"远程调用文章微服务失败了");
	}

}
