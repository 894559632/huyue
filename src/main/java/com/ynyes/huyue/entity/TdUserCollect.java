package com.ynyes.huyue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户收藏实体类
 * 
 * @author 作者：DengXiao
 * @version 创建时间：2016年4月27日下午8:46:12
 */

@Entity
public class TdUserCollect {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 收藏用户的用户名
	@Column
	private String username;

	// 收藏商品的id
	@Column
	private Long goodsId;

	// 收藏商品的标题
	@Column
	private String goodsTitle;

	// 收藏商品的图片
	@Column
	private String goodsCoverImageUri;

	// 收藏商品的价格
	@Column(scale = 2)
	private Double price;

	// 收藏商品的销量
	@Column
	private Long soldNumber;

	// 收藏时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date collectTime;

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

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsCoverImageUri() {
		return goodsCoverImageUri;
	}

	public void setGoodsCoverImageUri(String goodsCoverImageUri) {
		this.goodsCoverImageUri = goodsCoverImageUri;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getSoldNumber() {
		return soldNumber;
	}

	public void setSoldNumber(Long soldNumber) {
		this.soldNumber = soldNumber;
	}

	public Date getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(Date collectTime) {
		this.collectTime = collectTime;
	}
}
