<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>订单详情</title>
<meta name="keywords" content="<#if setting??>${setting.title!''}-${setting.seoKeywords!''}</#if>">
<meta name="description" content="<#if setting??>${setting.title!''}-${setting.seoDescription!''}</#if>">
<meta name="copyright" content="<#if setting??>${setting.title!''}-${setting.copyright!''}</#if>" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/main.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/add.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/touch/js/jquery.zclip.min.js"></script>
<script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<style type="text/css">
	.li_all .odetail{
		color: #999999;
	}
	.li_all .aleft{
		float: left;
	}
	.li_all .aright{
		float: right;
	}
	.li_all .aright span{
		color: #da251e;
	}
	.order_detail{
		padding: 0.25rem;
		border-top: #dddddd 0.01rem solid;
		background:white url(/touch/images/detail_bd.png) no-repeat bottom;
		background-size: 100%;
		color: #999999;
	}
	.order_detail label{
		color: #333;
		margin-right: 0.7rem;
		line-height: 0.4rem;
	}
	.order_detail p{
		margin-top: 6px;
	}
	.detail_order{
		width: 5.9rem;
		background: white;
		padding:0 0.25rem;
		overflow: hidden;
		margin: auto;
	}
	.detail_order li{
		float: left;
	}
	.detail_order .title{
		width: 5.9rem;
		line-height: 0.8rem;
		font-size: 0.24rem;
		border-bottom: #dddddd 0.01rem solid;
	}
	.detail_order .name{
		line-height: 0.8rem;
		color: #999999;
		width: 5.9rem;
	}
	.detail_order .name span{
		margin-right: 0.1rem;
	}
	.detail_order .dleft{
		width: 0.6rem;
		height: 60px;
	}
	.detail_order .dleft div{
		height: 1.2rem;
		width: 0.04rem;
		background: #dfdfdf;
		margin: auto;
	}
	.detail_order .dleft div span.active{
		background: #04bc7f;
	}
	.detail_order .dleft div span{
		position: absolute;
		left: -0.075rem;
		top: 0px;
		border-radius: 0.09rem;
		width: 0.18rem;
		height: 0.18rem;
		background: #dfdfdf;
	}
	.detail_order .dleft div span:nth-of-type(2){
		top: 1.2rem;
	}
	.detail_order .dright{
		width: 5.14rem;
		float: right;
	}
	.detail_order .dright p{
		height: 1.2rem;
	}
	.detail_order .dright p.active{
		color: #04bc7f;
	}
	.detail_order .dright p span{
		display: block;
		clear: both;	
	}
	.delivery_dx {
		margin-top : -15px;
	}
	.delivery_dx a {
		color : #da251e;
	}
</style>
<script type="text/javascript">
	
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>订单详情</span>
	</div>
</section>
<!-- header_top end -->
<!-- order_detail -->
<#if order??>
	<div class="order_detail">
		<label>${order.shippingName!''}</label>
		<span>${order.shippingPhone!''}</span>
		<p>${order.shippingCity!''}-${order.shippingAddress!''}</p>
	</div>
	<!-- order_detail -->
	<!-- 我的订单-产品分类 -->
	<ul class="l_goods">
		<li>
			<div class="li_title">
				<label class="lab1">订单号：<span>${order.orderNumber!''}</span></label>
				<#if order.statusId??>
					<label class="lab2">
						<#switch order.statusId>
							<#case 2>待付款<#break>
							<#case 3>待发货<#break>
							<#case 4>待收货<#break>
							<#case 5>待评价<#break>
							<#case 6>已完成<#break>
							<#case 7>已取消<#break>
						</#switch>
					</label>
				</#if>
			</div>
			<#if order??&&order.orderGoodsList??&&order.orderGoodsList?size gt 0>
				<#list order.orderGoodsList as item>
					<dl>
						<dt>
							<a href="
								<#if order.orderNumber?contains("JFDH")>
									/touch/goods/point/detail/${item.id?c}
								<#else>
									/touch/goods/detail/${item.id?c}
								</#if>
							" title="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}">
								<img src="${item.goodsCoverImageUri!''}" alt="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}">
							</a>
						</dt>
						<dd class="dd1">${item.goodsTitle!''}</dd>
						<dd class="dd2">
							<p class="p1">
								<#if order.orderNumber?contains("JFDH")>
									积分：<#if item.pointUse??>${item.pointUse?string("0.00")}<#else>0.00</#if>
								<#else>
									￥<#if item.price??>${item.price?string("0.00")}<#else>0.00</#if>
								</#if>
							</p>
							<p class="p2">x${item.quantity!''}</p>
						</dd>
					</dl>
				</#list>
			</#if>
			<div class="li_all">
				<div class="odetail aleft">
					<label>支付方式：</label>
					<span>
						<#if order.orderNumber?contains("JFDH")>
							积分兑换
						<#else>
							<#if order.payTypeId??&&order.payTypeId == 0>
								商家确认
							<#else>
								${order.payTypeTitle!''}
							</#if>
						</#if>
					</span>
				</div>
				<div class="odetail aright">
					<label>
						<#if order.orderNumber?contains("JFDH")>
							消费积分						
						<#else>
							实际付款
						</#if>
					</label>
					<span>
						<#if order.orderNumber?contains("JFDH")>
							：<#if order.pointUse??>${order.totalPrice?string("0.00")}<#else>0.00</#if>
						<#else>
							：￥<#if order.totalPrice??>${order.totalPrice?string("0.00")}<#else>0.00</#if>
						</#if>
					</span>
				</div>
			</div>
		</li>
	</ul>
	<!-- 我的订单-产品分类 -->
	<#if delivery??>
		<ul class="detail_order">
			<li class="title">
				物流详情
			</li>
			<li class="name">
				<label>物流公司：</label>
				<span>${delivery.title!''}</span>
				<label>物流单号：</label>
				<span id="deliveryNumber">${order.expressNumber!''}</span>
				<p class="delivery_dx"><label><a id="copy" href="${delivery.uri!''}">点击此处查询物流</a></label></p>
			</li>
		</ul>
	</#if>
</#if>
</body>
</html>
