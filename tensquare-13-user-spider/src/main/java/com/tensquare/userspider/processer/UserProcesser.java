package com.tensquare.userspider.processer;

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
public class UserProcesser implements PageProcessor{

	/**
	 * 处理我们需要的页面
	 */
	@Override
	public void process(Page page) {
		//1、获取我们需要的url
		page.addTargetRequests(page.getHtml().regex("http://www.cfei.net/archives/[0-9]{6}").all());
		
		//2、获取我们需要的内容： title和content
		String nickname = page.getHtml().xpath("//*[@id=\"page-content\"]/div/div/div[2]/div[1]/div/div[2]/a/text()").toString();
		String avatar = page.getHtml().xpath("//*[@id=\"page-content\"]/div/div/div[2]/div[1]/div/div[1]/a").css("img", "src").toString();
		
		System.out.println("爬取头像："+avatar);
		//3、传递到Pipeline
		if(nickname!=null){
			page.putField("nickname", nickname);
			page.putField("avatar", avatar);
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
