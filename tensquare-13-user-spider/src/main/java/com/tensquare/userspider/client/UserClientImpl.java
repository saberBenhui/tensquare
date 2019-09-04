package com.tensquare.userspider.client;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.tensquare.userspider.pojo.User;

import entity.Result;
import entity.StatusCode;

/**
 * 熔断类
 * @author Johnny.Chen
 *
 */
@Component
public class UserClientImpl implements UserClient{

	public Result add( User user  ) {
		return new Result(false,StatusCode.REMOTE_ERROR,"远程调用用户微服务失败");
	}
}
