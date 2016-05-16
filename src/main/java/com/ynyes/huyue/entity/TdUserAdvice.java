package com.ynyes.huyue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户反馈实体类
 * 
 * @author 作者：DengXiao
 * @version 版本：下午4:32:35
 */

@Entity
public class TdUserAdvice {

	// 自增主键
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 联系人
	@Column
	private String phone;

	// 内容
	@Lob
	private String content;

	// 提交时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date adviceTime;

	// 是否回馈
	@Column
	private Boolean isReply;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getAdviceTime() {
		return adviceTime;
	}

	public void setAdviceTime(Date adviceTime) {
		this.adviceTime = adviceTime;
	}

	public Boolean getIsReply() {
		return isReply;
	}

	public void setIsReply(Boolean isReply) {
		this.isReply = isReply;
	}

}
