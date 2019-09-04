package com.tensquare.articlespider.processer;

import java.util.List;

import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 文章的处理类
 * @author Johnny.Chen
 *
 */
@Component
public class ArticleProcesser implements PageProcessor{

	/**
	 * 处理我们需要的页面
	 */
	@Override
	public void process(Page page) {
		//1、获取我们需要的url
		page.addTargetRequests(page.getHtml().regex("https://my.oschina.net/u/[a-zA-Z0-9_]/blog/[0-9]{7}").all());
		
		//2、获取我们需要的内容： title和content
		String title = page.getHtml().xpath("//*[@id=\"mainScreen\"]/div/div[1]/div/div[2]/div[1]/div[2]/h2/text()").toString();
		String content = page.getHtml().xpath("//*[@id=\"articleContent\"]").toString();
		
		//3、传递到Pipeline
		if(title!=null && content!=null){
			page.putField("title", title);
			page.putField("content", content);
		}else {
			page.setSkip(true);//跳过爬取
		}
	}

	//站点信息设置
	@Override
	public Site getSite() {
		return Site.me().setRetryTimes(2).setSleepTime(500).setTimeOut(2000);
	}

}
