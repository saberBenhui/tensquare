package com.tensquare.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class LoginFilter extends ZuulFilter{
	
	/**
	 * 前面过滤
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 数字越小越先执行
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * 是否执行run方法
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println("请求类型："+request.getMethod());
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            //OPTIONS请求不做拦截操作
        	System.out.println("是options请求");
            return false;
        }
		return true;
	}

	/**
	 * 把认证的请求头再一次放入请求头中
	 */
	@Override
	public Object run() throws ZuulException {
		System.out.println("来到网关2了。。。。。。");
		//把请求头信息放回请求头中
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		//获取请求头
		String token = request.getHeader("Authorization");
		if(token!=null && !"".equals(token)) {
			//再一次放回请求头中
			ctx.addZuulRequestHeader("Authorization", token);
		}
		return null;
	}

	
}
