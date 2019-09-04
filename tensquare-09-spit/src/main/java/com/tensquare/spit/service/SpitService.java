package com.tensquare.spit.service;


import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;

import util.IdWorker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * 业务代码
 * @author Johnny.Chen
 *
 */
@Service
public class SpitService {
	
	@Autowired
	private SpitDao spitDao;
	
	@Autowired
	private IdWorker idWorker;
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	/**
	 * 点赞：第二种方案  ：  db.集合名称.update(  {_id: 1} , { $set: {更新的逻辑}  }  )
	 * @param id
	 */
	public void thumbup(String id) {
		
		//第一个参数：Query 
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		
		//第二个参数：更新内容:   db.spit.update({userid: '1014'},{$inc: {visits: 1}})
		Update update = new Update();
		update.inc("thumbup", 1);
		
		//执行更新
		mongoTemplate.updateFirst(query, update, Spit.class);
		
	}
	
	
	/**
	 * 点赞: 第一种方案： 需要链接两次数据库
	 * @param id
	 */
	/*public void thumbup(String id) {
		Spit spit = spitDao.findById(id).get();
		spit.setThumbup(spit.getThumbup()+1);
		spitDao.save(spit);
	}*/

	

	/**
	 * 查询评论列表
	 * @param parentid
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Spit> comment(String parentid, int page, int size) {
		return spitDao.findByParentid(parentid,PageRequest.of(page-1, size));
	}
	
	//查询所有
	public List<Spit> findAll(){
		return spitDao.findByParentidIsNull();
	}
	
	
	//查询一个
	public Spit findById(String id) {
		return spitDao.findById(id).get();
	}
	
	//新增一个
	public void add(Spit spit) {
		spit.setId(idWorker.nextId()+"");
		spitDao.save(spit);
		//判断是否是评论：
		if(spit.getParentid()!=null && !"".equals(spit.getParentid())) {
			//更新parentid对应的吐槽的评论数
			//第一个参数：Query 
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
			
			//第二个参数：更新内容:   db.spit.update({userid: '1014'},{$inc: {visits: 1}})
			Update update = new Update();
			update.inc("comment", 1);
			
			//执行更新
			mongoTemplate.updateFirst(query, update, Spit.class);
		}
	}
	
	//更新
	public void update(Spit spit) {
		spitDao.save(spit);
	}
	
	//删除
	public void delete(String id) {
		spitDao.deleteById(id);
	}

}
