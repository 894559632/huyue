package com.ynyes.huyue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 积分变更明细
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月30日下午3:34:16
 */

@Entity
public class TdPointLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 用户名
	@Column
	private String username;

	// 变更时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date changeTime;

	// 变更额度
	@Column
	private Double fee;

	// 变更类型（1. 消费增加积分；2. 管理员修改积分；3. 兑换使用积分）
	@Column
	private Long type;

	// 相关订单
	@Column
	private String orderNumber;

	// 相关订单id
	@Column
	private Long orderId;

	// 操作者
	@Column
	private String operator;
	
	//变更后的积分额度
	@Column
	private Double changedPoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Double getChangedPoint() {
		return changedPoint;
	}

	public void setChangedPoint(Double changedPoint) {
		this.changedPoint = changedPoint;
	}
}
