package com.tensquare.articlespider.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tensquare.articlespider.pojo.Article;

import entity.Result;

/**
 * 远程调用
 * @author Johnny.Chen
 *
 */
@FeignClient(value="tensquare-article",fallback=ArticleClientImpl.class) //http://localhost:9004
public interface ArticleClient {
	
	@RequestMapping(value="/article",method=RequestMethod.POST)
	public Result add(@RequestBody Article article );

}
