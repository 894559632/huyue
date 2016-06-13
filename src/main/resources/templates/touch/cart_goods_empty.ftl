<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>购物车</title>
<meta name="keywords" content="<#if setting??>${setting.seoKeywords!''}</#if>">
<meta name="description" content="<#if setting??>${setting.seoDescription!''}</#if>">
<meta name="copyright" content="<#if setting??>${setting.copyright!''}</#if>" />
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

</script>
<body class="gray_body">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>购物车</span>
	</div>
</section>
<!-- header_top end -->
<!-- buycar_empty  -->
<div class="buycar_empty">
	<img alt="" src="/touch/images/buycar_empty.png"/>
	<p>您的购物车暂无商品</p>
	<a href="/touch/goods" title="<#if setting??>${setting.title!''}-</#if>随便逛逛">随便逛逛~</a>
</div>
<!-- buycar_empty end -->
<!-- footer -->
<section class="footer">
	<nav>
		<a href="/touch" title="<#if setting??>${setting.title!''}-</#if>首页">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>首页" src="/touch/images/footer_icon01.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>首页" src="/touch/images/footer_icon11.png"/>
			</span>
			<label>首页</label>
		</a>
		<a href="/touch/goods" title="<#if setting??>${setting.title!''}-</#if>分类">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>分类" src="/touch/images/footer_icon02.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>分类" src="/touch/images/footer_icon22.png"/>
			</span>
			<label>分类</label>
		</a>
		<a href="/touch/cart" title="<#if setting??>${setting.title!''}-</#if>购物车">
			<span>
				<img class="active_img" alt="<#if setting??>${setting.title!''}-</#if>购物车" src="/touch/images/footer_icon03.png"/>
				<img class="disable_img" alt="<#if setting??>${setting.title!''}-</#if>购物车" src="/touch/images/footer_icon33.png"/>
			</span>
			<label class="active_label">购物车</label>
		</a>
		<a href="/touch/lottery" title="<#if setting??>${setting.title!''}-</#if>抽奖">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>抽奖" src="/touch/images/footer_icon04.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>抽奖" src="/touch/images/footer_icon44.png"/>
			</span>
			<label>抽奖</label>
		</a>
		<a href="/touch/user" title="<#if setting??>${setting.title!''}-</#if>我">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>我" src="/touch/images/footer_icon05.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>我" src="/touch/images/footer_icon55.png"/>
			</span>
			<label>我</label>
		</a>
	</nav>
</section>
<!-- footer end -->
</body>
</html>
