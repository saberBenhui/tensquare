package com.tensquare.recruit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Enterprise;
/**
 * enterprise数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

	/**
	 * 查询热门企业
	 * @param f
	 * @return
	 */
	List<Enterprise> findTop9ByIshot(String f);
	
}
