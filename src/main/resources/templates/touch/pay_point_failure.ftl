<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
	<title><#if setting??>${setting.title!''}-</#if>兑换失败</title>
	<meta name="keywords" content="<#if setting??>${setting.title!''}-${setting.seoKeywords!''}</#if>">
	<meta name="description" content="<#if setting??>${setting.title!''}-${setting.seoDescription!''}</#if>">
	<meta name="copyright" content="<#if setting??>${setting.title!''}-${setting.copyright!''}</#if>" />
    <meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>创客APP</title>
    <!-- css -->
    <link href="/touch/css/common.css" rel="stylesheet" type="text/css" />
    <link href="/touch/css/main.css" rel="stylesheet" type="text/css" />
    <!-- js -->
    <script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<body class="body_gray">

<!-- header_top -->
<div class="top_heater whitehead">
    <a href="/touch" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
    <span>兑换失败</span>
</div>
<!-- header_top end -->

<!-- Center Start -->
<section class="container">
    <div class="buytips">
        <i class="fail"></i>
        <span class="txttips">对不起！兑换积分商品失败！</span>
        <span class="othertip">请确认您拥有足够的积分</span>
		<a style="line-height:50px;" href="/touch">返回首页</a>
    </div>
</section>
<!-- Center End -->

</body>
</html>