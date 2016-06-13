<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>用户资料</title>
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
<body class="gray_body">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>个人信息</span>
	</div>
</section>
<!-- header_top end -->
<!-- personage  -->
<form action="/touch/user/info/save" method="post">
	<ul class="personage">
		<li class="li01">
			<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>头像">
				<label>头像</label>
				<div style="background:none;"></div>
				<span>
					<img alt="<#if setting??>${setting.title!''}-</#if>头像" src="<#if user??>${user.headImgUri!''}</#if>"/>
				</span>
			</a>
		</li>
		<li class="li02">
			<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>用户名">
				<label>用户名</label>
				<div style="background:none;"></div>
				<span class="username_span"><#if user??>${user.username!''}</#if></span>
			</a>
		</li>
		<li class="li02">
			<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>真实姓名">
				<label>姓名</label>
				<div style="background:none;"></div>
				<span class="username_span"><#if user??>${user.realName!''}</#if></span>
			</a>
		</li>
		<li class="li03">
			<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>性别">
				<label>性别</label>
				<div></div>
				<select style="-webkit-appearance:none;" name="sex" id="l_se">
					<option value="0">请选择</option>
					<option value="男" <#if user??&&user.sex??&&user.sex=="男">selected="selected"</#if>>男</option>
					<option value="女" <#if user??&&user.sex??&&user.sex=="女">selected="selected"</#if>>女</option>
				</select>
			</a>
		</li>
		<li class="li03">
			<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>性别">
				<label>城市</label>
				<div></div>
				<select style="-webkit-appearance:none;" name="cityId" id="l_se">
					<option value="0">请选择</option>
					<#if city_list??>
						<#list city_list as item>
							<#if item??>
								<option <#if user.cityId??&&user.cityId?c==item.id?c>selected="selected"</#if> value="${item.id?c}">${item.title!''}</option>
							</#if>
						</#list>
					</#if>
				</select>
			</a>
		</li>
	</ul>
	<div style="width:100%;height:0.9rem"></div>
	<input type="submit" style="-webkit-appearance:none;" value="保存" id="l_save">
</form>
<!-- personage end -->
</body>
</html>
