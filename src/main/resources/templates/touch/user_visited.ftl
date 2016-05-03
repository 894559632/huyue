<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>历史记录</title>
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
<body class="body_gray">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="" class="header_back"></a>
		<span>历史记录</span>
		<div class="clear_all" onclick="visited">清空</div>
	</div>
</section>
<!-- header_top end -->
<!-- buy_car  -->
<ul class="buy_car">
	<#if visit_list??>
		<#list visit_list as item>
			<#if item??>
				<li class="history_box">
					<div class="test">
						<div class="box">
							<img class="history" alt="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}" src="${item.goodsCoverImageUri!''}"/>
							<div class="text">
								<p>${item.goodsTitle!''}</p>
								<span>￥<#if item.goodsSalePrice??>${item.goodsSalePrice?string("0.00")}<#else>0.00</#if></span>
							</div>
						</div>
					</div>
				</li>
			</#if>
		</#list>
	</#if>
</ul>
<!-- buy_car end -->


</body>
</html>
