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
		<a href="#" title="" class="header_back"></a>
		<span>确认订单</span>
	</div>
</section>
<!-- header_top end -->
<!-- sure_order_title -->
<div class="sure_order_title">
	<#--
		<div style="overflow:hidden;">
			<p>收货人: 周大江</p>
			<label>电话：1388***5583</label>
		</div>
		<a class="address" href="#" title="">dddddd</a>
	-->
		<a class="address" style="line-height:0.9rem;text-align:center;background:#cc1421;color:#fff;padding:0px;border-radius:5px;" href="#" title="">请添加收货地址</a>
</div>
<div class="color_bd"></div>
<!-- sure_order_title end -->
<!-- buy_car  -->
<ul class="buy_car">
	<li class="sure_order">
		<div class="test">
			<div class="box">
				<img class="history" alt="" src="/touch/images/good02.png"/>
				<div class="text">
					<p>蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B蚊钉枪P622-B</p>
					<span>￥25.26<font>X2</font></span>
				</div>
			</div>
		</div>
	</li>
	<li class="sure_order">
		<div class="test">
			<div class="box">
				<img class="history" alt="" src="/touch/images/good03.png"/>
				<div class="text">
					<p>蚊钉枪P622-B</p>
					<span>￥25.26<font>X2</font></span>
				</div>
			</div>
		</div>
	</li>
	<li class="sure_order">
		<div class="test">
			<div class="box">
				<img class="history" alt="" src="/touch/images/good01.png"/>
				<div class="text">
					<p>蚊钉枪P622-B</p>
					<span>￥25.26<font>X2</font></span>
				</div>
			</div>
		</div>
	</li>
</ul>
<!-- buy_car end -->
<!-- pay -->
<div class="sure_order_pay">
	<div class="all_choice">
		<p style="line-height:0.8rem;"><label>商品金额：</label><span>￥504.00</span></p>
	</div>
	<div class="all_math">实付款：￥25.26</div>
	<a href="#" title="">去结算</a>
</div>
<!-- pay end -->
</body>
</html>
