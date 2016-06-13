<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>收货地址</title>
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
<script type="text/javascript" src="/touch/js/DengXiao/base.js"></script>
<script type="text/javascript" src="/touch/js/DengXiao/order.js"></script>
</head>
<script type="text/javascript">
	$(function(){
		choice_pay('.sure_order_title section .default','span_active');
	});
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="/touch/pay?orderId=${orderId!'0'}" title="<#if setting??>${setting.title!""}-</#if>返回" class="header_back"></a>
		<span>地址管理</span>
	</div>
</section>
<!-- header_top end -->
<!-- address_add_box -->
<#if address_list ??>
	<#list address_list as item>
		<#if item??>
			<div class="sure_order_title address_add_box" id="box${item.id?c}">
				<div class="name">
					<p>
						<span>${item.receiveName!''}</span>
						<span>${item.receivePhone!''}</span>
					</p>
				</div>
				<a class="address_add" href="javascript:base.order.address.selected(${item.id?c});" title="<#if setting??>${setting.title!''}-</#if>修改收货地址">${item.cityTitle!''}-${item.detail!''}</a>
				<section>
					<span class="fixed" onclick="base.order.address.selected(${item.id?c});">选择 </span>
				</section>
			</div>
		</#if>
	</#list>
</#if>

<!-- address_add_box end -->
<input type="hidden" id="orderId" value="${orderId!'0'}">
<#--
	<a class="footer_btn address_btn" href="/touch/user/add/address" title="<#if setting??>${setting.title!''}-</#if>新增收货地址">新增收货地址</a>
-->
</body>
</html>
