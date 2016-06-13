package com.ynyes.huyue.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 积分兑换记录
 * 
 * @author 作者：DengXiao
 * @version 版本：2016年5月31日下午3:15:08
 */
@Entity
public class TdExchangeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 兑换的用户
	@Column
	private String username;

	// 兑换的用户id
	@Column
	private Long userId;

	// 相关订单号
	@Column
	private String orderNumber;

	// 兑换商品id
	@Column
	private Long goodsId;

	// 兑换的商品名称
	@Column
	private String goodsTitle;

	// 兑换的商品图片
	@Column
	private String goodsCoverImageUri;

	// 兑换的商品消耗的积分
	@Column
	private Double pointUsed;

	// 兑换的商品的数量
	@Column
	private Long quantity;

	// 兑换的时间
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date exchangeTime;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public Double getPointUsed() {
		return pointUsed;
	}

	public void setPointUsed(Double pointUsed) {
		this.pointUsed = pointUsed;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
}
