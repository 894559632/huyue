package com.ynyes.huyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 城市管理实体类
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月26日下午10:05:56
 */

@Entity
public class TdCity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//城市名
	@Column
	private String title;
	
	//是否启用
	@Column
	private Boolean isEnable;
	
	//排序号
	@Column(scale = 2)
	private Double sortId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
}
