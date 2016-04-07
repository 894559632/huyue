package com.ynyes.huyue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 网站设置实体类
 * 
 * @author DengXiao
 */

@Entity
public class TdSetting {

	/*--------------------单例模式----------------------*/
	private static TdSetting INSTANCE = new TdSetting();

	private TdSetting() {
		super();
	}

	public static TdSetting getInstance() {
		return INSTANCE;
	}

	public static void setInstance(TdSetting setting) {
		INSTANCE = setting;
	}
	/*-----------------------------------------------*/

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 网站名称
	@Column
	private String title;

	// 网站域名
	@Column
	private String domainName;

	// 网站LOGO
	@Column
	private String logoUri;

	// 公司名称
	@Column
	private String company;

	// 通信地址
	@Column
	private String address;

	// 联系电话
	@Column
	private String telephone;

	// 在线客服QQ
	@Column
	private String qq;

	// 管理员邮箱
	@Column
	private String adminEmail;

	// 网站备案号
	@Column
	private String icpNumber;

	// 首页标题SEO
	@Column
	private String seoTitle;

	// 首页关键词SEO
	@Column
	private String seoKeywords;

	// 页面描述SEO
	@Column
	private String seoDescription;

	// 版权信息
	@Column
	private String copyright;

	// 开启触屏版
	@Column
	private Boolean isTouchEnable;

	// 触屏版地址
	@Column
	private String touchUri;

	// 注册用户协议
	@Column
	private String registerNego;

	// 注册成功奖励积分
	@Column
	private Long registerSuccessPoints;

	// 每次抽奖消耗积分
	@Column
	private Long lotteryPoints;

	// 微信二维码
	@Column
	private String wxQrCode;

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

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getLogoUri() {
		return logoUri;
	}

	public void setLogoUri(String logoUri) {
		this.logoUri = logoUri;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getIcpNumber() {
		return icpNumber;
	}

	public void setIcpNumber(String icpNumber) {
		this.icpNumber = icpNumber;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Boolean getIsTouchEnable() {
		return isTouchEnable;
	}

	public void setIsTouchEnable(Boolean isTouchEnable) {
		this.isTouchEnable = isTouchEnable;
	}

	public String getTouchUri() {
		return touchUri;
	}

	public void setTouchUri(String touchUri) {
		this.touchUri = touchUri;
	}

	public String getRegisterNego() {
		return registerNego;
	}

	public void setRegisterNego(String registerNego) {
		this.registerNego = registerNego;
	}

	public Long getRegisterSuccessPoints() {
		return registerSuccessPoints;
	}

	public void setRegisterSuccessPoints(Long registerSuccessPoints) {
		this.registerSuccessPoints = registerSuccessPoints;
	}

	public Long getLotteryPoints() {
		return lotteryPoints;
	}

	public void setLotteryPoints(Long lotteryPoints) {
		this.lotteryPoints = lotteryPoints;
	}

	public String getWxQrCode() {
		return wxQrCode;
	}

	public void setWxQrCode(String wxQrCode) {
		this.wxQrCode = wxQrCode;
	}

}
