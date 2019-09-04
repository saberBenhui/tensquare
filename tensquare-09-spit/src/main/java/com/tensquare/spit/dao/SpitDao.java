package com.tensquare.spit.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tensquare.spit.pojo.Spit;

/**
 * 
 * @author Johnny.Chen
 * 
 * MongoRepository<操作的实体类, ID的类型>
 *
 */
public interface SpitDao extends MongoRepository<Spit, String>{

	/**
	 * 查询评论列表
	 * @param parentid
	 * @param of
	 * @return
	 */
	Page<Spit> findByParentid(String parentid, Pageable of);

	/**
	 * 查询吐槽数据，parentid不为空才是吐槽
	 * @return
	 */
	List<Spit> findByParentidIsNull();

}
