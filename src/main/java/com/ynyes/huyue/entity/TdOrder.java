package com.ynyes.huyue.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 订单
 *
 * 记录了订单详情
 * 
 * @author Sharon
 *
 */

@Entity
public class TdOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 订单号
    @Column(unique=true)
    private String orderNumber;
    
    // 订单商品
    @OneToMany
    @JoinColumn(name="tdOrderId")
    private List<TdOrderGoods> orderGoodsList;
    
    //收获城市
    @Column
    private String shippingCity;
    
    // 收货地址
    @Column
    private String shippingAddress;
    
    // 收货人
    @Column
    private String shippingName;
    
    // 收货电话
    @Column
    private String shippingPhone;
    
    // 收货备用电话
    @Column
    private String spareShippingPhone;
    
    // 邮政编码
    @Column
    private String postalCode;
    
    // 支付方式
    @Column
    private Long payTypeId;
    
    // 支付方式名称
    @Column
    private String payTypeTitle;
    
    // 支付方式手续费
    @Column(scale=2)
    private Double payTypeFee;
    
    // 配送方式
    @Column
    private Long deliverTypeId;
    
    // 配送方式名称
    @Column
    private String deliverTypeTitle;
    
    // 配送方式名称
    @Column(scale=2)
    private Double deliverTypeFee;
    
    // 快递公司编号
    @Column
    private String expressCampany;
    
    // 快递单号
    @Column
    private String expressNumber;
    
    // 快递详情查询接口
    @Column
    private String expressUri;
    
    // 快递用户留言备注
    @Column
    private String userRemarkInfo;
    
    // 后台备注
    @Column
    private String remarkInfo;
    
    // 是否索要发票
    @Column
    private Boolean isNeedInvoice;
    
    // 发票抬头
    @Column
    private String invoiceTitle;
    
    // 发票内容
    @Column
    private String invoiceContent;
    
    // 发票类型
    @Column
    private String invoiceType;
    
    // 下单时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    
    // 取消时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;
    
    // 确认时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkTime;
    
    // 付款时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    
    // 配送时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;
    
    // 收货时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
    
    // 评价时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
    
    // 完成时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    
    // 订单状态  1:待确认 2:待付款 3:待发货 4:待收货 5: 待评价 6: 已完成 7: 已取消  8:用户删除
    @Column
    private Long statusId;
    
    // 订单类型 1：普通订单 2：组合购买订单 3：抢购订单 4：团购订单 5:积分商品订单
    @Column
    private Long typeId;
    
    // 订单取消原因
    @Column
    private String cancelReason;
    
    //取消申请时间 zhangji
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date cancelApplyTime;
    
    // 退款时间 zhangji
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date refundTime;;
    
    //是否取消订单 zhangji
    @Column
    private  Boolean isCancel;
    
    //是否退款 zhangji
    @Column
    private Boolean isRefund;
    
    //退款金额 zhangji
    @Column(scale = 2)
    private Double refund;
    
    //退款详细 zhangji
    @Column
    private String handleDetail;
    
    // 用户
    @Column
    private String username;
    
    // 发货时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

	// 配货人
    @Column
    private String deliveryPerson;
    
    // 配送人
    @Column
    private String distributionPerson;
    
    // 收款人
    @Column
    private String moneyReceivePerson;
    
    // 商品总金额
    @Column(scale=2)
    private Double totalGoodsPrice;
    
    // 订单总金额
    @Column(scale=2)
    private Double totalPrice;
    
    // 排序号
    @Column
    private Long sortId;
    
    // 使用积分数 
    @Column
    private Double pointUse;
    
    // 可获取积分
    @Column
    private Double points;
    
    // 使用虚拟货币
    @Column
    private Double virtualCurrencyUse;
    
    // 使用优惠券抵用额度
    @Column
    private Double couponUse;
    
    @Column
    private String couponTitle;
    
    // 是否在线付款
    @Column
    private Boolean isOnlinePay;

    // 分享用户id
    @Column
    private Long shareId;
    
    // 分享用户可获取积分
    @Column
    private Long totalSharePoints;
    
    @Column
    private String realName;
    
    public Long getTotalSharePoints() {
		return totalSharePoints;
	}

	public void setTotalSharePoints(Long totalSharePoints) {
		this.totalSharePoints = totalSharePoints;
	}

	public Double getRefund() {
		return refund;
	}

	public void setRefund(Double refund) {
		this.refund = refund;
	}

	public Boolean getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Boolean isCancel) {
		this.isCancel = isCancel;
	}

	public Long getShareId() {
		return shareId;
	}

	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCouponUse() {
		return couponUse;
	}

	public void setCouponUse(Double couponUse) {
		this.couponUse = couponUse;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getHandleDetail() {
		return handleDetail;
	}

	public void setHandleDetail(String handleDetail) {
		this.handleDetail = handleDetail;
	}

	public String getOrderNumber() {
        return orderNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<TdOrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<TdOrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public Double getVirtualCurrencyUse() {
		return virtualCurrencyUse;
	}

	public void setVirtualCurrencyUse(Double virtualCurrencyUse) {
		this.virtualCurrencyUse = virtualCurrencyUse;
	}

	public Long getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Long payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getPayTypeTitle() {
        return payTypeTitle;
    }

    public void setPayTypeTitle(String payTypeTitle) {
        this.payTypeTitle = payTypeTitle;
    }

    public Double getPayTypeFee() {
        return payTypeFee;
    }

    public void setPayTypeFee(Double payTypeFee) {
        this.payTypeFee = payTypeFee;
    }

    public String getDeliverTypeTitle() {
        return deliverTypeTitle;
    }

    public void setDeliverTypeTitle(String deliverTypeTitle) {
        this.deliverTypeTitle = deliverTypeTitle;
    }

    public Long getDeliverTypeId() {
        return deliverTypeId;
    }

    public void setDeliverTypeId(Long deliverTypeId) {
        this.deliverTypeId = deliverTypeId;
    }

    public Double getDeliverTypeFee() {
        return deliverTypeFee;
    }

    public void setDeliverTypeFee(Double deliverTypeFee) {
        this.deliverTypeFee = deliverTypeFee;
    }

    public String getUserRemarkInfo() {
        return userRemarkInfo;
    }

    public void setUserRemarkInfo(String userRemarkInfo) {
        this.userRemarkInfo = userRemarkInfo;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public Boolean getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Boolean isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpressCampany() {
        return expressCampany;
    }

    public void setExpressCampany(String expressCampany) {
        this.expressCampany = expressCampany;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressUri() {
        return expressUri;
    }

    public void setExpressUri(String expressUri) {
        this.expressUri = expressUri;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getDistributionPerson() {
        return distributionPerson;
    }

    public void setDistributionPerson(String distributionPerson) {
        this.distributionPerson = distributionPerson;
    }

    public String getMoneyReceivePerson() {
        return moneyReceivePerson;
    }

    public void setMoneyReceivePerson(String moneyReceivePerson) {
        this.moneyReceivePerson = moneyReceivePerson;
    }

    public Double getTotalGoodsPrice() {
        return totalGoodsPrice;
    }

    public void setTotalGoodsPrice(Double totalGoodsPrice) {
        this.totalGoodsPrice = totalGoodsPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public Boolean getIsOnlinePay() {
        return isOnlinePay;
    }

    public void setIsOnlinePay(Boolean isOnlinePay) {
        this.isOnlinePay = isOnlinePay;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Double getPointUse() {
        return pointUse;
    }

    public void setPointUse(Double pointUse) {
        this.pointUse = pointUse;
    }
    
    
    public Boolean getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Boolean isRefund) {
		this.isRefund = isRefund;
	}

	public Date getCancelApplyTime() {
		return cancelApplyTime;
	}

	public void setCancelApplyTime(Date cancelApplyTime) {
		this.cancelApplyTime = cancelApplyTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getSpareShippingPhone() {
		return spareShippingPhone;
	}

	public void setSpareShippingPhone(String spareShippingPhone) {
		this.spareShippingPhone = spareShippingPhone;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
