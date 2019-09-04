package com.tensquare.spit.controller;

import java.util.concurrent.TimeUnit;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
	
	@Autowired
	private SpitService  spitService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	
	//吐槽点赞
	@RequestMapping(value="/thumbup/{id}",method=RequestMethod.PUT)
	public Result thumbup(@PathVariable String id) {
		//模拟当前登录用户
		String userid = "11111";
		
		//key
		String key = "thumbup_"+userid+"_"+id;
		
		//从缓存中找
		String flag = (String) redisTemplate.opsForValue().get(key);
		
		//如果有标记说明已经点过赞了
		if(flag!=null&&"1".equals(flag)) {
			return new Result(false,StatusCode.REPEATE_ERROR,"亲，不能重复点赞哦~");
		}
		//调用service
		spitService.thumbup(id);
		//更新标记
		redisTemplate.opsForValue().set(key, "1", 30, TimeUnit.DAYS);
		//点赞成功
		return new Result(true,StatusCode.OK,"点赞成功");
	}
	
	
	
	
	
	
	//查询所有
	@RequestMapping(value="/comment/{parentid}/{page}/{size}",method=RequestMethod.GET)
	public Result comment(
			@PathVariable String parentid,
			@PathVariable int page,
			@PathVariable int size
			) {
		
		//调用service进行查询
		Page<Spit> pageBean = spitService.comment(parentid,page,size);
		
		//分页对象
		PageResult<Spit> p = new PageResult<>(pageBean.getTotalElements(),pageBean.getContent());
		
		//返回
		return new Result(true,StatusCode.OK,"查询评论列表成功",p);
	}
	
	//查询所有
	@RequestMapping(method=RequestMethod.GET)
	public Result findAll() {
		return new Result(true,StatusCode.OK,"查询所有成功",spitService.findAll());
	}
	
	
	//查询一个
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Result findById(@PathVariable String id) {
		return new Result(true,StatusCode.OK,"查询一个成功",spitService.findById(id));
	}
	
	
	//新增
	/**
	 * 1、吐槽：   如果没有parentid就是吐槽
	 * 2、吐槽的评论： 如果有parentid就是评论
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Spit spit) {
		spitService.add(spit);
		return new Result(true,StatusCode.OK,"保存成功");
	}
	
	//修改
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@PathVariable String id,@RequestBody Spit spit) {
		spit.setId(id);
		spitService.update(spit);
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	
	//删除
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id) {
		spitService.delete(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	

}
