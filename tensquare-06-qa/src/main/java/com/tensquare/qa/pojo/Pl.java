package com.tensquare.qa.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

//中间表的映射
@Entity
@Table(name="tb_pl")
//如果是联合主键需要这个注解
@IdClass(Pl.class)
public class Pl implements Serializable{
	
	@Id
	private Long problemid;
	
	@Id
	private Long labelid;
	
	

	public Long getProblemid() {
		return problemid;
	}

	public Long getLabelid() {
		return labelid;
	}

	public void setProblemid(Long problemid) {
		this.problemid = problemid;
	}

	public void setLabelid(Long labelid) {
		this.labelid = labelid;
	}
	
	
	
	

}
