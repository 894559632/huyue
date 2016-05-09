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
		<a href="/touch/user" title="" class="header_back"></a>
		<span>地址管理</span>
	</div>
</section>
<!-- header_top end -->
<!-- address_add_box -->
<#if address_list ??>
	<#list address_list as item>
		<#if item??>
			<div class="sure_order_title address_add_box">
				<div class="name">
					<p>
						<span>${item.receiveName!''}</span>
						<span>${item.receivePhone!''}</span>
					</p>
				</div>
				<a class="address_add" href="#" title="">${item.detail!''}</a>
				<section>
					<span class="default <#if item.isDefault??&&item.isDefault>span_active</#if>">默认地址</span>
					<span class="delete">删除</span>
					<span class="fixed">修改 </span>
				</section>
			</div>
		</#if>
	</#list>
</#if>

<!-- address_add_box end -->
<div style="width: 100%;height: 1rem;float: left;"></div>
<a class="footer_btn address_btn" href="/touch/user/add/address" title="<#if setting??>${setting.title!''}-</#if>新增收货地址">新增收货地址</a>
</body>
</html>
