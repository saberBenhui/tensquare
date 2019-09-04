package com.tensquare.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;

import util.IdWorker;

/**
 * 业务层代码
 * @author Johnny.Chen
 *
 */
@Service
public class LabelService {
	
	@Autowired
	private LabelDao labelDao;
	
	@Autowired
	private IdWorker idWorker;
	
	/**
	 * 分页条件查询
	 * @param page
	 * @param size
	 * @param label
	 * @return
	 */
	public Page<Label> search(int page, int size, Label label) {
		//拼接查询条件
		//Specification<Label> spec = createSpec(label);
		//分页条件
		//PageRequest pageable = PageRequest.of(page-1, size);
		//分页查询
		return labelDao.findAll(createSpec(label), PageRequest.of(page-1, size));
	}
	
	private Specification<Label> createSpec(Label label) {
		return new Specification<Label>() {
			/**
			 * root： 用于获取实体类的属性
			 * query: 查询顶层接口
			 * cb: 动态构建查询条件
			 */
			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				List<Predicate> preList = new ArrayList<Predicate>();
				
				//根据标签名称模糊查询
				if(label.getLabelname()!=null && !"".equals(label.getLabelname())) {
					//获取类型
					Path<String> path = root.get("labelname");
					//构建查询条件
					Predicate like = cb.like(path, "%"+label.getLabelname()+"%");
					//存入查询条件
					preList.add(like);
				}
				
				//根据状态精确查询
				if(label.getState()!=null && !"".equals(label.getState())) {
					//获取类型
					Path<String> path = root.get("state");
					//构建查询条件
					Predicate e = cb.equal(path, label.getState());
					//存入查询条件
					preList.add(e);
				}
				//===========================
				
				
				//实例化查询的条件的数组
				Predicate[] predicate =  new Predicate[preList.size()];
				
				//把list中的Predicate 转存入数组中
				predicate = preList.toArray(predicate);
				
				
				//predicate = (Predicate[]) preList.toArray();
				
				//执行查询
				return cb.and(predicate);
			}
		};
	}
	
	//条件查询: labelname = java
	public List<Label> search(Label label) {
		//拼接查询条件
		Specification<Label> spec = createSpec(label);
		//执行动态查询
		return labelDao.findAll(spec);
	}
	
	
	
	//查询所有
	public List<Label> findAll(){
		return labelDao.findAll();
	}
	
	//根据id查询一个
	public Label findById(Long id) {
		return labelDao.findById(id).get();
	}
	
	//新增一个
	public void add(Label label) {
		//指定id
		label.setId(idWorker.nextId());
		labelDao.save(label);
	}
	
	//修改一个
	public void update(Label label) {
		labelDao.save(label);
	}
	
	//删除
	public void deleteById(Long id) {
		labelDao.deleteById(id);
	}


}
