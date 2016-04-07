package com.ynyes.huyue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 前台用户实体类
 * 
 * @author DengXiao
 */
@Entity
public class TdUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 用户名
	@Column
	private String username;

	// 密码
	@Column
	private String password;

	// 用户城市
	@Column
	private String city;

	// 真实姓名
	@Column
	private String realName;

	// 电话号码
	@Column
	private String mobile;

	// 性别
	@Column
	private String sex;

	// 生日
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthday;

	// 微信账号
	@Column
	private String wechatUsername;

	// 支付宝账号
	@Column
	private String aliUsername;

	// QQ账号
	@Column
	private String qqUsername;

	// 总消费额度
	@Column(scale = 2)
	private Double totalConsume;

	// 会员积分
	@Column(scale = 2)
	private Double point;

	// 排序号
	@Column(scale = 2)
	private Double sortId;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getWechatUsername() {
		return wechatUsername;
	}

	public void setWechatUsername(String wechatUsername) {
		this.wechatUsername = wechatUsername;
	}

	public String getAliUsername() {
		return aliUsername;
	}

	public void setAliUsername(String aliUsername) {
		this.aliUsername = aliUsername;
	}

	public String getQqUsername() {
		return qqUsername;
	}

	public void setQqUsername(String qqUsername) {
		this.qqUsername = qqUsername;
	}

	public Double getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(Double totalConsume) {
		this.totalConsume = totalConsume;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public Double getSortId() {
		return sortId;
	}

	public void setSortId(Double sortId) {
		this.sortId = sortId;
	}
}
