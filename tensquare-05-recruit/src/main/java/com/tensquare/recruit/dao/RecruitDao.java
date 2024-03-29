package com.tensquare.recruit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;
/**
 * recruit数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

	/**
	 * 推荐职位
	 * @param string
	 * @return
	 */
	List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String string);

	/**
	 * 最新职位
	 * @param string
	 * @return
	 */
	List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String string);
	
}
