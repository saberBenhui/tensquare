package com.tensquare.user.controller;
import java.util.HashMap;
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

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import util.JwtUtil;
/**
 * user控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 删除: 只有管理员才能删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		
		Claims admin = (Claims) request.getAttribute("admin_claims");
		//获取当前登录的用户：admin.getId();
		//判断
		if(admin==null) {
			return new Result(false,StatusCode.ACCESS_ERROR,"权限不足");
		}
		//如果是管理员就可以删除
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Result login(@RequestBody User user) {
		//调用业务逻辑
		User loginUser = userService.login(user);
		//如果用户为空登录失败
		if(loginUser==null) {
			return new Result(false,StatusCode.USER_PASS_ERROR,"手机号码或者密码错误");
		}
		//其他的是成功
		//登录成功
		Map<String,String> data = new HashMap<String, String>();
		//签发token
		String token = jwtUtil.createJWT(loginUser.getId(), loginUser.getLoginname(), "user");
		data.put("token", token);
		//用户名称
		data.put("name", loginUser.getNickname());
		//用户头像
		String img = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
		data.put("avatar", img);
		
		
		
		return new Result(true,StatusCode.OK,"登录成功",data);
	}
	
	
	
	//发送手机验证码
	@RequestMapping(value="/sendsms/{mobile}",method=RequestMethod.POST)
	public Result sendsms(@PathVariable(value="mobile",required=true) String mobile) {
		//调用业务逻辑
		userService.sendsms(mobile);
		//返回
		return new Result(true,StatusCode.OK,"恭喜：发送短信验证码成功");
	}
	
	
	//用户注册
	@RequestMapping(value="/register/{code}",method=RequestMethod.POST)
	public Result register(@PathVariable(value="code",required=true) String code,@RequestBody User user) {
		//调用service
		userService.register(code,user);
		//返回
		return new Result(true,StatusCode.OK,"恭喜：注册成功，请妥善保管好你的用户名和密码！");
	}
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		System.out.println("远程调用："+user);
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	

}
