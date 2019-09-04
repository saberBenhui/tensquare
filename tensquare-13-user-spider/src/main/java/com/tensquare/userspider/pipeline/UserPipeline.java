package com.tensquare.userspider.pipeline;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tensquare.userspider.client.UserClient;
import com.tensquare.userspider.pojo.User;
import com.tensquare.userspider.util.DownloadUtil;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class UserPipeline implements Pipeline{
	
	@Autowired
	private UserClient userClient;
	
	@Value("${user.save_path}")
	private String savepath;

	@Override
	public void process(ResultItems res, Task task) {
		//获取数据
		String nickname = res.get("nickname");
		String avatar = res.get("avatar");//下载的url
		String url = res.get("avatar");
		
		//实例对象
		User u = new User();
		u.setNickname(nickname);
		//下载图片
		// 根据url下载图片：   https://static.oschina.net/uploads/user/695/1391482_50.jpeg?t=1485826415000
		//if(url.indexOf("?")>0) {
			//String[] split = url.split("\\?");
			//url = split[0]; //  https://static.oschina.net/uploads/user/695/1391482_50.jpeg
			url = url.substring(url.lastIndexOf("/")+1); // 1391482_50.jpeg
			//System.out.println(url);
			u.setAvatar(url);
			//下载图片
			if(url!=null) {
				try {
					/**
					 * 参数一：图片地址
					 * 参数二：图片名称
					 * 参数三：保存路径
					 */
					DownloadUtil.download(avatar, url, savepath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		//}
		//保存
		userClient.add(u);
		
	}
	
	

}
