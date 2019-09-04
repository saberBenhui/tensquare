package com.tensquare.gathering.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.gathering.pojo.Gathering;
import com.tensquare.gathering.service.GatheringService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
/**
 * gathering控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/gathering")
public class GatheringController {

	@Autowired
	private GatheringService gatheringService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Gathering> pageList = gatheringService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",gatheringService.findSearch(searchMap));
    }
	
    @Autowired
    private HttpServletRequest request;
    
    
	/**
	 * 增加
	 * @param gathering
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Gathering gathering  ){
		Claims admin = (Claims) request.getAttribute("admin_claims");
		//获取当前登录的用户：admin.getId();
		//判断
		if(admin==null) {
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足，或者登录超时，请重新登录！");
		}
		
		gatheringService.add(gathering);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param gathering
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Gathering gathering, @PathVariable String id ){
		
		Claims admin = (Claims) request.getAttribute("admin_claims");
		//获取当前登录的用户：admin.getId();
		//判断
		if(admin==null) {
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足，或者登录超时，请重新登录！");
		}
		
		gathering.setId(id);
		gatheringService.update(gathering);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		
		Claims admin = (Claims) request.getAttribute("admin_claims");
		//获取当前登录的用户：admin.getId();
		//判断
		if(admin==null) {
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足，或者登录超时，请重新登录！");
		}
		
		gatheringService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
