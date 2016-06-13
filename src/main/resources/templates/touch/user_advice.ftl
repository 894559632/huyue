<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>意见反馈</title>
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
<script type="text/javascript">

</script>
<body class="gray_body" style="height: 100%;">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="/touch/user" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>意见反馈</span>
	</div>
</section>
<!-- header_top end -->
<!-- advice  -->
<div class="give_advice">
	<p>送货、退换货及咨询请联系<a <#if setting??>href="tel:${setting.qq!''}"</#if>>联系客服</a></p>
	<div class="box">
		<h3>我们存在哪些不足</h3>
		<textarea name="content" id="content"></textarea>
		<h3>您的联系电话</h3>
		<input type="text" name="phone" id="phone" value="${username!''}" />
	</div>
	<input style="-webkit-appearance:none;width: 100%;height: 0.74rem;position: fixed;left: 0px;bottom: 0px;border: none;outline-color: none;color: white;background: #da251e;font-size: 0.28rem;" type="button" onclick="base.user.advice.save();" value="提交" />
</div>
<!-- advice end -->
</body>
</html>
