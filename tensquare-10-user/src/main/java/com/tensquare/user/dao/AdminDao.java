package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.Admin;
/**
 * admin数据访问接口
 * @author Administrator
 *
 */
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{

	/**
	 * 根据登录名查询 管理员
	 * @param loginname
	 * @return
	 */
	Admin findByLoginname(String loginname);
	
}
