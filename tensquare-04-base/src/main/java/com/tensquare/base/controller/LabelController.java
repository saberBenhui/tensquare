package com.tensquare.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

/**
 * 标签的控制器
 * @author Johnny.Chen
 *
 */
@RestController
@RequestMapping("label")

@CrossOrigin//允许所有
//@CrossOrigin("*") //代表允许所有的跨域访问
//@CrossOrigin({"http://www.tensquare.com","http://manager.tensquare.com"}) //代表允许所有的跨域访问
public class LabelController {
	
	@Autowired
	private LabelService labelService;
	
	/**
	 * 分页条件查询
	 * @param page 当前页
	 * @param size 页大小
	 * @param label 查询条件
	 * @return
	 */
	@PostMapping("/search/{page}/{size}")
	public Result search(@PathVariable("page") int page, @PathVariable("size") int size,@RequestBody Label label) {
		//分页查询
		Page<Label> pageBean = labelService.search(page,size,label);
		//当前页数据
		List<Label> rows = pageBean.getContent();
		//总记录数
		long totalElements = pageBean.getTotalElements();
		//返回分页结果集
		return new Result(true,StatusCode.OK,"分页条件查询成功",new PageResult<Label>(totalElements, rows));
	}
	
	//标签的条件查询
	@PostMapping("search")
	public Result search(@RequestBody Label label) {
		List<Label> list = labelService.search(label);
		return new Result(true,StatusCode.OK,"条件查询成功",list);
	}
	
	
	//查询所有
	@GetMapping
	public Result findAll() {
		List<Label> all = labelService.findAll();
		return new Result(true, StatusCode.OK, "查询成功", all);
	}
	
	//查询一个
	@GetMapping("{id}")
	public Result findById(@PathVariable("id")  Long id) {
		return new Result(true, StatusCode.OK, "查询一个成功", labelService.findById(id));
	}
	
	//新增
	@PostMapping
	public Result add(@RequestBody Label label) {
		labelService.add(label);
		return new Result(true, StatusCode.OK, "保存成功");
	}
	
	//修改
	@PutMapping("{id}")
	public Result update(@PathVariable("id")  Long id, @RequestBody Label label) {
		label.setId(id);//以url的id为主
		labelService.update(label);
		return new Result(true, StatusCode.OK, "修改成功");
	}
	
	//删除
	@DeleteMapping("{id}")
	public Result delete(@PathVariable("id")  Long id) {
		labelService.deleteById(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}

}
