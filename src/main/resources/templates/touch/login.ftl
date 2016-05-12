<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>登录</title>
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
<script type="text/javascript" src="/touch/js/DengXiao/login.js"></script>
</head>
<body class="onload_body">
<!-- header_top -->
<section class="container onload">
	<div class="top_heater">
		<span class="onload_header">登录</span>
	</div>
</section>
<!-- header_top end -->
<!-- onload  -->
<form class="onload_box" name="loginForm" onsubmit="return base.login.loginSubmit();">
	<input class="text" type="text" placeholder="手机号" name="username" id="username" />
	<input class="text" type="password" placeholder="密码" name="password" id="password" />
	<input class="sub" type="submit" name="" id="" value="登录" />
	<p class="forget_secret">忘记密码 ?</p>
	<div class="other_onload">
		<div>
			<span class="left_line"></span>其他方式登录<span class="right_line"></span>
		</div>
		<a class="qq" href="#" title=""></a>
		<a class="wei" href="#" title=""></a>
		<a class="zhi" href="#" title=""></a>
	</div>
	<label class="ifaccount">还没有账号</label>
	<a class="register" href="/touch/regist" title="<#if setting??>${setting.title!''}-</#if>注册">注册</a>
</form>
<!-- onload end -->
</body>
</html>
