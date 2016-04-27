<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>个人中心</title>
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

</script>
<body class="gray_body">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="/touch" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>个人信息</span>
	</div>
</section>
<!-- header_top end -->
<!-- personage  -->
<ul class="personage">
	<li class="li01">
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>头像">
			<label>头像</label>
			<div></div>
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>头像" src="<#if user??>${user.headImgUri!''}</#if>"/>
			</span>
		</a>
	</li>
	<li class="li02">
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>用户名">
			<label>用户名</label>
			<div></div>
			<span><#if user??>${user.username!''}</#if></span>
		</a>
	</li>
	<li class="li03">
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>性别">
			<label>性别</label>
			<div></div>
			<span><#if user??>${user.sex!''}</#if></span>
		</a>
	</li>
</ul>
<!-- personage end -->
</body>
</html>
