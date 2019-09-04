package com.tensquare.gathering.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import util.JwtUtil;

@Component
public class JwtFilter extends HandlerInterceptorAdapter{
	
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 拦截请求
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//判断是否是管理员
		String authStr = request.getHeader("Authorization");
		//判断
		if(authStr!=null &&  authStr.startsWith("Bearer ")) {
			//解析token
			String token = authStr.substring(7);
			Claims parseJWT = null;
			try {
				parseJWT = jwtUtil.parseJWT(token);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//判断是管理员
			if(parseJWT!=null) {
				String roles = (String) parseJWT.get("roles");
				if(roles!=null && "admin".equals(roles)) {
					//把Claims放到请求
					request.setAttribute("admin_claims", parseJWT);
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	
	

}
