<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>积分中心</title>
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
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>积分中心</span>
	</div>
</section>
<!-- header_top end -->
<!-- 我的积分 -->
<!-- 我的积分-结束 -->
<div class="l_score">
	<div class="l_s1">
		<p class="p1"><#if user??&&user.point??>${user.point?string("0")}<#else>0</#if></p>
		<p class="p2">我的积分</p>
	</div>
</div>
<div class="l_s2">
	<a href="/touch/user/point/detail" title="<#if setting??>${setting.title!''}-</#if>积分明细" class="a1">积分明细</a>
	<a href="/touch/user/point/exchange" title="<#if setting??>${setting.title!''}-</#if>兑换记录" class="a2">兑换记录</a>
</div>
<!-- 积分产品 -->
<#if goods_list??&&goods_list?size gt 0>
	<#list goods_list as goods>
		<#if goods??>
			<dl class="l_score_goods">
				<dt><a href="/touch/goods/point/detail/${goods.id?c}" title="<#if setting??>${setting.title!''}-</#if>${goods.title!''}"><img src="${goods.coverImageUri!''}"" alt=""></a></dt>
				<dd>
					<p class="p1">${goods.title!''}</p>
					<p class="p2">所需积分<span><#if goods.pointLimited??>${goods.pointLimited?string("0")}<#else>0</#if></span></p>
					<p class="p3"><span>${goods.soldNumber!'0'}</span>人已兑换</p>
					<p class="p4"><a href="javascript:exchange(${goods.id?c})" title="<#if setting??>${setting.title!''}-</#if>立即兑换">立即兑换</a></p>
				</dd>
			</dl>
		</#if>
	</#list>
</#if>
<!-- 积分产品-结束 -->
</body>
<script>
	function exchange(goodsId){
		<#-- 发送异步请求，确认用户是否登录，生成兑换单 -->
		$.ajax({
			url : "/touch/goods/point/create",
			type : "POST",
			data : {
				goodsId : goodsId
			},
			success:function(res){
				if(0 !== res.status){
					alert(res.message);
					if(-2 === res.status){
						window.location.href = "/touch/login";
					}
				}else{
					window.location.href = "/touch/pay?orderId=" + res.orderId;
				}
			}
		});
	}
</script>
</html>
