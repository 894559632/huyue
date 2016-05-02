package com.ynyes.huyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 收货地址
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年5月2日下午3:43:27
 */

@Entity
public class TdShippingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 用户id
	@Column
	private Long userId;

	// 用户名
	@Column
	private String username;

	// 城市id
	@Column
	private Long cityId;

	// 城市名称
	@Column
	private String cityTitle;

	// 收货人姓名
	@Column
	private String receiveName;

	// 收货人电话
	@Column
	private String receivePhone;

	// 收货人详细地址
	@Lob
	private String detail;

	// 是否是默认地址
	@Column
	private Boolean isDefault;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityTitle() {
		return cityTitle;
	}

	public void setCityTitle(String cityTitle) {
		this.cityTitle = cityTitle;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getReceivePhone() {
		return receivePhone;
	}

	public void setReceivePhone(String receivePhone) {
		this.receivePhone = receivePhone;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
}
