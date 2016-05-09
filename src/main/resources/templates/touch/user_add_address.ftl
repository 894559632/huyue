<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>新增收货地址</title>
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
<script type="text/javascript" src="/touch/js/DengXiao/user.js"></script>
</head>
<body class="gray_body">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>新增收货地址</span>
	</div>
</section>
<!-- header_top end -->
<!-- personage  -->
<ul class="personage">
	<li class="li02">
		<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>收货人姓名">
			<label>收货人姓名：</label>
			<input type="text" name="receive_name" id="receive_name" placeholder="请输入收货人的姓名" <#if address??>value="${address.receiveName!''}"</#if>/>
		</a>
	</li>
	<li class="li02">
		<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>电话" <#if address??>value="${address.receivePhone!''}"</#if>>
			<label>电话：</label>
			<input type="text" name="receive_phone" id="receive_phone" placeholder="请输入收货人电话" />
		</a>
	</li>
	<li class="li03">
		<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>地区">
			<label>地区：</label>
			<select class="dx_user_address_select"  name="receive_city_id" id="receive_city_id">
				<option value="0">请选择收货地区</option>
				<#if city_list??>
					<#list city_list as item>
						<#if item??>
							<option value="${item.id?c}">${item.title!''}</option>
						</#if>
					</#list>
				</#if>
			</select>
		</a>
	</li>
	<li class="li02">
		<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>详细地址">
			<label>详细地址：</label>
			<input type="text" name="receive_detail" id="receive_detail" placeholder="请输入收货人详细地址" <#if address??>value="${address.receiveDetail!''}"</#if>/>
		</a>
	</li>
</ul>
<input class="footer_btn" type="button" onclick="base.user.city.save();" value="保 存" />
<!-- personage end -->
</body>
</html>
