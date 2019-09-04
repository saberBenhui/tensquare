package com.tensquare.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.base.pojo.Label;

/**
 * dao层接口
 * @author Johnny.Chen
 *
 */
public interface LabelDao  extends JpaRepository<Label, Long>,JpaSpecificationExecutor<Label>{

}
