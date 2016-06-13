<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>确认订单</title>
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
<!-- js -->
<script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<script type="text/javascript">
	$(function(){
	});
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="/touch" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>确认订单</span>
	</div>
</section>
<!-- header_top end -->
<!-- sure_order_title -->
<div class="sure_order_title">
	<#if order.shippingName??>
		<div style="overflow:hidden;">
			<p>收货人: ${order.shippingName!''}</p>
			<label>电话：${order.shippingPhone!''}</label>
		</div>
		<a class="address" href="/touch/order/address/list<#if order??>?orderId=${order.id?c}</#if>" title="<#if setting??>${setting.title!''}-</#if>收货地址">${order.shippingCity!''}-${order.shippingAddress!''}</a>
	<#else>
		<a id="addAddress" class="address" style="
			line-height:0.9rem;
			text-align:center;
			background:#cc1421;
			color:#fff;
			padding:0px;
			border-radius:5px;" href="/touch/order/add/address<#if order??>?orderId=${order.id?c}</#if>" title="<#if setting??>${setting.title!''}-</#if>添加收货地址">请添加收货地址</a>
	</#if>
	<#--
		<div style="overflow:hidden;">
			<p>收货人: 周大江</p>
			<label>电话：1388***5583</label>
		</div>
		<a class="address" href="#" title="">dddddd</a>
	-->
		
</div>
<div class="color_bd"></div>
<!-- sure_order_title end -->
<!-- buy_car  -->
<ul class="buy_car">
	<#if order??&&order.orderGoodsList?size gt 0>
		<#list order.orderGoodsList as item>
			<#if item??>
				<li class="sure_order">
					<div class="test">
						<div class="box">
							<img class="history" alt="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}" src="${item.goodsCoverImageUri!''}"/>
							<div class="text">
								<p>蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B</p>
								<#-- 三种情况：1. 消费订单；2. 积分订单；3. 奖品兑换 -->
								<#if order??&&order.totalPrice??&&order.totalPrice gt 0>
									<span>￥<#if item.price??>${item.price?string("0.00")}<#else>0.00</#if><font>X${item.quantity!'0'}</font></span>
								</#if>

								<#if order??&&order.pointUse??&&order.pointUse gt 0>
									<span><#if item.pointUse??>${item.pointUse?string("0.00")}<#else>0.00</#if>积分<font>X${item.quantity!'0'}</font></span>
								</#if>

								<#if order??&&order.orderNumber??&&order.orderNumber?contains("CJDD")>
									<span>￥0.00<font>X1</font></span>
								</#if>
							</div>
						</div>
					</div>
				</li>
			</#if>
		</#list>
	</#if>
</ul>
<!-- buy_car end -->
<!-- pay -->
<div class="sure_order_pay">
	<#--
	<div class="all_choice">
		<p style="line-height:0.8rem;"><label>商品金额：</label><span>￥<#if order??&&order.totalGoodsPrice??>${order.totalGoodsPrice?string("0.00")}<#else>0.00</#if></span></p>
	</div>
	-->

	<#-- 三种方式：1. 消费订单；2. 积分订单；3.抽奖订单 -->
	<#if order??&&order.totalPrice??&&order.totalPrice gt 0>
		<div class="all_math">实付款：￥<#if order??&&order.totalPrice??>${order.totalPrice?string("0.00")}<#else>0.00</#if></div>
		<a href="javascript:pay(${order.id?c})" title="<#if setting??>${setting.title!''}-</#if>去结算">去结算</a>
	</#if>

	<#if order??&&order.pointUse??&&order.pointUse gt 0>
		<div class="all_math">消费：<#if order??&&order.pointUse??>${order.pointUse?string("0.00")}<#else>0.00</#if>积分</div>
		<a href="javascript:pointPay(${order.id?c})" title="<#if setting??>${setting.title!''}-</#if>去兑换">去兑换</a>
	</#if>

	<#if order??&&order.orderNumber??&&order.orderNumber?contains("CJDD")>
		<a style="width:100%" href="javascript:awardPay(${order.id?c})" title="<#if setting??>${setting.title!''}-</#if>立即领取">立即领取</a>
	</#if>
</div>
<!-- pay end -->
</body>
<script>
function pay(orderId){
	var button = document.getElementById("addAddress");
	if(button){
		alert("请添加收货地址");
		return;
	}
	window.location.href = "/touch/order/clear?orderId=" + orderId;
}

function pointPay(orderId){
	var button = document.getElementById("addAddress");
	if(button){
		alert("请添加收货地址");
		return;
	}
	window.location.href = "/touch/order/point/clear?orderId=" + orderId;
}

function awardPay(orderId){
	var button = document.getElementById("addAddress");
	if(button){
		alert("请添加收货地址");
		return;
	}
	window.location.href = "/touch/order/award/clear?orderId=" + orderId;
}
</script>
</html>
