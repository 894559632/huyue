<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>个人中心</title>
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
<script type="text/javascript">
	$(function(){
	});
</script>
<body class="body_gray">
<!-- header_top -->
<dl class="l_ltitle">
	<dt><a href="#" title="<#if setting??>${setting.title!''}-</#if>用户中心"><img src="${user.headImageUri!''}" alt="<#if setting??>${setting.title!''}-</#if>用户中心"></a></dt>
	<dd class="dd1">${user.username!''}</dd>
	<#--
	<dd class="dd2">152*****608</dd>
	<dd class="dd0"><a href="#" title=""></a></dd>
	-->
</dl>
<!-- header_top end -->
<!-- 全部订单 -->
<div class="l_all_orders">
	<a href="#" title="" class="a1"><p>全部订单</p></a>
	<a href="#" title="" class="a2">
		<p>待付款</p>
		<span>1</span>
	</a>
	<a href="#" title="" class="a3"><p>待收货</p></a>
	<a href="#" title="" class="a4"><p>待评价</p></a>
</div>
<!-- 全部订单-结束 -->
<!-- 分类1 -->
<div class="l_classify1">
	<a href="#" title=""><p>积分中心</p></a>
	<a href="/touch/user/collect" title="<#if setting??>${setting.title!''}-</#if>我的收藏"><p>我的收藏</p></a>
	<a href="#" title=""><p>历史记录</p></a>
</div>
<!-- 分类1-结束 -->
<!-- 分类2 -->
<div class="l_classify2">
	<a href="/touch/user/info" title="<#if setting??>${setting.title!''}-</#if>个人信息"><p>个人信息</p></a>
	<a href="#" title=""><p>修改密码</p></a>
	<a href="/touch/user/address" title="<#if setting??>${setting.title!''}-</#if>收货地址"><p>收货地址</p></a>
	<a href="#" title=""><p>积分抽奖</p></a>
	<a href="#" title=""><p>意见反馈</p></a>
	<a href="#" title=""><p>客服中心</p></a>
</div>
<!-- 分类2-结束 -->

<!-- footer -->
<section class="footer">
	<nav>
		<a href="/touch" title="<#if setting??>${setting.title!''}-</#if>首页">
			<span>
				<img alt="" src="/touch/images/footer_icon01.png"/>
				<img alt="" src="/touch/images/footer_icon11.png"/>
			</span>
			<label>首页</label>
		</a>
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>分类">
			<span>
				<img alt="" src="/touch/images/footer_icon02.png"/>
				<img alt="" src="/touch/images/footer_icon22.png"/>
			</span>
			<label>分类</label>
		</a>
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>购物车">
			<span>
				<img alt="" src="/touch/images/footer_icon03.png"/>
				<img alt="" src="/touch/images/footer_icon33.png"/>
			</span>
			<label>购物车</label>
		</a>
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>抽奖">
			<span>
				<img alt="" src="/touch/images/footer_icon04.png"/>
				<img alt="" src="/touch/images/footer_icon44.png"/>
			</span>
			<label>抽奖</label>
		</a>
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>我">
			<span>
				<img alt="" class="active_img" src="/touch/images/footer_icon05.png"/>
				<img alt="" class="disable_img" src="/touch/images/footer_icon55.png"/>
			</span>
			<label class="active_label">我</label>
		</a>
	</nav>
</section>
<!-- footer end -->
</body>
</html>
