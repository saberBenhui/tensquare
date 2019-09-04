package com.tensquare.base.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Result;
import entity.StatusCode;

/**
 * 全局异常
 * @author Johnny.Chen
 * 
 * 每一个微服务都应该有这么一个全局异常类
 *
 */
@ControllerAdvice //异常全局通知，增强控制的异常处理
public class BaseExceptionHandler {
	
	@ResponseBody //返回json结构
	@ExceptionHandler(Exception.class)//捕获哪一种异常
	public Result exception1(Exception e) {
		return new Result(false,StatusCode.ERROR,"基础微服务异常："+e.getMessage());
	}
	
}
