package com.tensquare.articlespider.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tensquare.articlespider.client.ArticleClient;
import com.tensquare.articlespider.pojo.Article;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 文章的入库类
 * @author Johnny.Chen
 *
 */
@Component
public class ArticlePipeline implements Pipeline{
	
	@Autowired
	private ArticleClient articleClient;

	@Override
	public void process(ResultItems res, Task task) {
		Article a = new Article();
		a.setTitle(res.get("title"));
		a.setContent(res.get("content"));
		articleClient.add(a);
	}

}
