package com.tensquare.userspider.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tensquare.userspider.pojo.User;

import entity.Result;
/**
 * user控制器层
 * @author Administrator
 *
 */
@FeignClient(value="tensquare-user",fallback=UserClientImpl.class)//http://locahost:9007
public interface UserClient {

	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public Result add(@RequestBody User user  );
	
}
