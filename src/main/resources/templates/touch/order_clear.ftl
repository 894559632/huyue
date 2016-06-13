<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>选择支付方式</title>
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
		choice_pay('.pay_way .box label');//支付选择
	});
</script>
<body class="gray_body" style="background: #f3f4f6;">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1)" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>支付方式选择</span>
	</div>
</section>
<!-- header_top end -->
<!-- pay_way  -->
<ul class="pay_way">
	<li class="title">
		<span>订单金额</span>
		<label>￥<#if price??>${price?string("0.00")}<#else>0.00</#if></label>
	</li>
	<#if pay_list??>
		<#list pay_list as item>
			<#if item??>
				<a href="javascript:clear('${item.title!''}',${orderId?c})" title="<#if setting??>${setting.title!''}-</#if>${item.title!''}">
					<li class="box">
						<img alt="<#if setting??>${setting.title!''}-</#if>${item.title!''}" src="${item.coverImageUri!''}"/>
						<span>${item.title!''}</span>
						<p>${item.info!''}</p>
						<#--<label></label>-->
					</li>
				</a>
			</#if>
		</#list>
	</#if>
</ul>
<!-- pay_way end -->
</body>
<script>
	function clear(title,orderId){
		if(title&&orderId){
			switch(title){
				case "支付宝":
					window.location.href = "/touch/pay/alipay?orderId=" + orderId;
				break;
			}
			switch(title){
                case "微信":
                    window.location.href = "/touch/pay/wechat?orderId=" + orderId;
                break;
            }
		}
	}
</script>
</html>
