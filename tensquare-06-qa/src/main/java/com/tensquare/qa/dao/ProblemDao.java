package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tensquare.qa.pojo.Problem;
/**
 * problem数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

	/**
	 * 分页的查询： jpql或者sql
	 * @param labelid
	 * @param of
	 * @return
	 */
	@Query(value="select p from Problem p where p.id in (select t.problemid from Pl t where t.labelid = ?1) order by p.replytime desc")
	Page<Problem> newlist(Long labelid, Pageable of);

	//热门
	@Query(value="SELECT * FROM tb_problem WHERE id IN (SELECT problemid FROM tb_pl WHERE labelid = ?1) ORDER BY reply DESC",nativeQuery=true)
	Page<Problem> hotlist(Long labelid, Pageable of);

	//等待
	@Query(value="SELECT * FROM tb_problem WHERE id IN (SELECT problemid FROM tb_pl WHERE labelid = 1) AND reply = 0 ",nativeQuery=true)
	Page<Problem> waitlist(Long labelid, Pageable of);
	
}
