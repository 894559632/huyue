<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>用户收藏</title>
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
		drge('.test');//删除拖拽
		change_all('.collect_nav span','span_active');//nav 切换
	});
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container collect_header">
	<div class="top_heater">
		<a href="/touch" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>我的收藏</span>
	</div>
</section>
<!-- header_top end -->
<!-- collect -->
<div class="collect_nav">
	<span <#if param=="0-0"||param=="0-1">class="span_active"</#if>><a href="/touch/user/collect?param=0-<#if param=="0-0">1<#else>0</#if>">默认</a></span>
	<span <#if param=="1-0"||param=="1-1">class="span_active"</#if>><a href="/touch/user/collect?param=1-<#if param=="1-0">1<#else>0</#if>">销量</a></span>
	<span <#if param=="2-0"||param=="2-1">class="span_active"</#if>><a href="/touch/user/collect?param=2-<#if param=="2-0">1<#else>0</#if>">价格</a></span>
</div>
<!-- collect end -->
<!-- buy_car  -->
<ul class="buy_car">
	<#if user_collect_page??&&user_collect_page.content?size gt 0>
		<#list user_collect_page.content as item>
			<#if item??>
				<li class="history_box">
					<div class="test">
						<div class="box">
							<img class="history" alt="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}" src="${item.goodsCoverImageUri!''}"/>
							<div class="text">
								<p>${item.goodsTitle!''}</p>
								<span>￥<#if item.price??>${item.price?string("0.00")}<#else>0.00</#if></span>
							</div>
						</div>
						<div class="dele"></div>
					</div>
				</li>
			</#if>
		</#list>
	</#if>
</ul>
<!-- buy_car end -->

</body>
</html>
