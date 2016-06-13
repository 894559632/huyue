<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>修改密码</title>
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
<body class="gray_body" style="background: #f3f4f6;">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>修改密码</span>
	</div>
</section>
<!-- header_top end -->
<!-- personage  -->
<ul class="personage">
	<li class="change_secret">
		<a title="<#if setting??>${setting.title!''}-</#if>输入手机号码">
			<label>输入手机号：</label>
			<input type="text" name="phone" value="${username!''}" readOnly="true" id="phone" placeholder="请输入您的手机号" />
			<span id="getSms" class="get_mark" onclick="getSms();">获取验证码</span>
		</a>
	</li>
	<li class="change_secret">
		<a title="<#if setting??>${setting.title!''}-</#if>输入短信验证码">
			<label>短信验证码：</label>
			<input type="text" name="smscode" id="smscode" placeholder="请输入短信验证码" />
		</a>
	</li>
	<li class="change_secret">
		<a title="<#if setting??>${setting.title!''}-</#if>输入新密码">
			<label>新密码：</label>
			<input type="password" name="password" id="password" placeholder="请输入您的新密码" />
		</a>
	</li>
	<li class="change_secret">
		<a title="<#if setting??>${setting.title!''}-</#if>确认新密码">
			<label>确认密码：</label>
			<input type="password" name="repassword" id="repassword" placeholder="请再次输入新密码" />
		</a>
	</li>
</ul>
<input class="footer_btn" type="button" style="-webkit-appearance:none;" onclick="changePassword();" value="确认修改" />
<!-- personage end -->
</body>
<script>
<#-- 定义倒计时 -->
var smsTime = 60;

<#-- 获取验证码的方法 -->
function getSms(){
	closeSms();
	var phone = document.getElementById("phone").value;
	var smsBtn = document.getElementById("getSms");
	
	<#-- 验证手机号码是否正确 -->
	var pattern = /^1\d{10}$/;
	if (!pattern.test(phone)) {
	    alert("请输入正确的手机号码");
	    return;
	}
	
	$.ajax({
		url : "/code/change/password",
	    type : "post",
	    data : {
			mobile : phone
	    },
	    success : function(res) {
	    	console.log(res);
			if ("000000" !== String(res.statusCode)) {
			    openSms();
			    alert(res.statusMsg);
			} else {
				reSmsTime();
			}
	    }
	});
}

<#-- 关闭验证码的方法 -->
function closeSms(){
    var smsBtn = document.getElementById("getSms");
    smsBtn.removeAttribute("onclick");
}

<#-- 开启验证码的方法 -->
function openSms() {
    var smsBtn = document.getElementById("getSms");
    smsBtn.setAttribute("onclick","getSms()");
}

<#-- 定义倒计时方法 -->
function reSmsTime() {
    if (0 !== smsTime) {
		setTimeout(function() {
		    smsTime -= 1;
		    document.getElementById("getSms").innerHTML = "等待（" + smsTime
			    + ")";
		    if (0 === smsTime) {
				document.getElementById("getSms").innerHTML = "获取验证码";
				smsTime = 60;
				openSms();
	    	} else {
				reSmsTime();
		    }
		}, 1000);
    }
}

<#-- 修改密码的方法 -->
function changePassword(){
	var phone = document.getElementById("phone").value;
	var smscode = document.getElementById("smscode").value;
	var password = document.getElementById("password").value;
	var repassword = document.getElementById("repassword").value;
	
	<#-- 验证手机号码 -->
	var pattern = /^1\d{10}$/;
	if (!pattern.test(phone)) {
	    alert("请输入正确的手机号码");
	    return;
	}
	
	<#-- 验证验证码 -->
	if(!(smscode && smscode.length === 4)){
		alert("验证码的长度不正确")
		return;
	}
	
	<#-- 验证密码的长度 -->
	if(!(password && password.length >= 6 && password.length <= 20)){
		alert("密码的长度为6-20位");
		return;
	}
	
	<#-- 验证两次输入的密码是否一致 -->
	if(!(password === repassword)){
		alert("两次输入的密码不一致")
		return;
	}
	
	$.ajax({
		url : "/touch/user/password/change",
		type : "POST",
		data : {
			phone : phone,
			smscode : smscode,
			password : password
		},
		success:function(res){
			if(0 === res.status){
				alert("操作成功");
				history.go(-1);
			}else{
				alert(res.message);
			}
		}
	})
}
</script>
</html>
