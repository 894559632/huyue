<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>我的订单</title>
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
<script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<style type="text/css">
	.wait{
		width: 100%;
		height: 0px;
		background: rgba(0,0,0,0.8);
		position: absolute;
		left: 0px;
		bottom: 0px;
		color: white;
		overflow: hidden;
	}
	.wait_box{
		width: 120px;
		margin: auto;
		overflow: hidden;
	}
	.wait img{
		margin-top: 12px;
		float: left;
		-webkit-transition:30s linear;
	}
	.wait span{
		font-size: 1.2em;
		margin-top: 14px;
		padding-left: 20px;
		float: left;
	}
</style>
<script type="text/javascript">
	var page = 0;
	$(function(){
		goodsIn();
		function goodsIn(){
			$(document).bind('scroll',ajaxFn);
			function ajaxFn(){
				if($(document).scrollTop() >= $(document).height()-$(window).height()){
					$(document).ajaxStart(function(){
						$('.wait').animate({height:'50px'});
						$('.wait img').css({WebkitTransform:'rotate(-10000deg)'});
					});
					
					var type = $("#type").val();
					
				    $.ajax({
				    	type:"post",
				    	url:"/touch/user/order/get",
				    	data:{
				    		type : type,
				    		page : page
				    	},
				    	success:function(res){
				    		var data = $("#data").html();
				    		$("#data").html(data + res);
				    		//测试用
				    		//恢复高度(保留恢复高度)
				    		$('html').height($(document).height());
				    	}
				    });
				    //解除监听
		    		$(document).unbind('scroll',ajaxFn);
		    		//加载成功后恢复监听
		    		$(document).ajaxSuccess(function(){
		    			$(document).bind('scroll',ajaxFn);
		    			$('.wait').animate({height:'0px'});
		    			$('.wait img').css({WebkitTransform:'rotate(0deg)'});
		    		});
				};
			};
		};
	});
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="/touch/user" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>我的订单</span>
	</div>
</section>
<!-- header_top end -->
<!-- 我的订单-分类选择 -->
<div class="l_sure_order_select">
	<a href="/touch/user/order/list?type=0" title="<#if setting??>${setting.title!''}-</#if>全部订单" <#if type??&&type==0>class="active"</#if>>全部</a>
	<a href="/touch/user/order/list?type=2" title="<#if setting??>${setting.title!''}-</#if>待付款订单" <#if type??&&type==2>class="active"</#if>>待付款</a>
	<a href="/touch/user/order/list?type=4" title="<#if setting??>${setting.title!''}-</#if>待收货订单" <#if type??&&type==4>class="active"</#if>>待收货</a>
	<a href="/touch/user/order/list?type=5" title="<#if setting??>${setting.title!''}-</#if>待评价订单" <#if type??&&type==5>class="active"</#if>>待评价</a>
</div>
<!-- 我的订单-分类选择 -->
<!-- 我的订单-产品分类 -->
<input type="hidden" id="type" value="${type!'0'}">
<ul class="l_goods" id="data">
	<#if order_page??&&order_page.content?size gt 0>
		<#list order_page.content as item>
			<#if item??>
				<li>
					<div class="li_title">
						<label class="lab1">订单号：<span>${item.orderNumber!''}</span></label>
						<#if item.statusId??>
							<label class="lab2">
								<#switch item.statusId>
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
					<#if item.orderGoodsList??&&orderGoodsList?size gt 0>
						<#list item.orderGoodsList as goods>
							<#if goods??>
								<dl>
									<dt><a href="/touch/goods/detail?id=${goods.goodsId?c}" title="<#if setting??>${setting.title!''}-</#if>${goods.goodsTitle!''}"><img src="${goods.goodsCoverImageUri!''}" alt="<#if setting??>${setting.title!''}-</#if>${goods.goodsTitle!''}"></a></dt>
									<dd class="dd1">${goods.goodsTitle!''}</dd>
									<dd class="dd2">
										<p class="p1">￥<#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></p>
										<p class="p2">x${goods.quantity!'0'}</p>
									</dd>
								</dl>
							</#if>
						</#list>
					</#if>
					<div class="li_all">共<span>${item.orderGoodsList?size!'0'}</span>件商品 合计：￥<span class="sp2"><#if item.totalPrice??>${item.totalPrice?string("0.00")}<#else>0.00</#if></span></div>
					<div class="li_pay">
						<a href="#" title="" class="a2">取消订单</a>
						<a href="#" title="" class="a3">去支付</a>
						<#if item.statusId??>
							<#switch item.statusId>
								<#case 2>
									<a href="#" title="" class="a2">查看详情</a>
									<a href="#" title="" class="a2">取消订单</a>
									<a href="#" title="" class="a3">去支付</a>
								<#break>
								<#case 3>
									<a href="#" title="" class="a2">查看详情</a>
									<a href="#" title="" class="a2">取消订单</a>
									<a href="#" title="" class="a2">取消订单</a>
								<#break>
								<#case 4>
									<a href="#" title="" class="a2">查看详情</a>
									<a href="#" title="" class="a2">取消订单</a>
									<a href="#" title="" class="a3">确认收货</a>
								<#break>
								<#case 5>
									<a href="#" title="" class="a2">查看详情</a>
									<a href="#" title="" class="a2">取消订单</a>
									<a href="#" title="" class="a3">去评价</a>
								<#break>
								<#case 6>
									<a href="#" title="" class="a2">查看详情</a>
								<#break>
								<#case 7>
									<a href="#" title="" class="a2">查看详情</a>
								<#break>
							</#switch>
						</#if>
					</div>
				</li>
			</#if>
		</#list>
	</#if>
</ul>
<!-- 我的订单-产品分类 -->
<div class="wait">
	<div class="wait_box">
		<img alt="<#if setting??>${setting.title!''}-</#if>加载数据" src="/touch/images/wait.png"/>
		<span>正在加载...</span>
	</div>
</div>
</body>
</html>
