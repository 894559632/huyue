<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <title><#if setting??>${setting.title!''}-</#if>找回密码</title>
    <meta name="keywords" content="<#if setting??>${setting.title!''}-${setting.seoKeywords!''}</#if>">
    <meta name="description" content="<#if setting??>${setting.title!''}-${setting.seoDescription!''}</#if>">
    <meta name="copyright" content="<#if setting??>${setting.title!''}-${setting.copyright!''}</#if>" />
    <meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <!-- css -->
    <link href="/touch/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="/touch/css/main.css" rel="stylesheet" type="text/css"/>
    <!-- js -->
    <script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/touch/js/common.js"></script>
    <style>
        .dialog {
            display: none;
            width: 100%;
            height: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 4;
            background: rgba(126, 126, 126, 0.5);
        }

        .dialog .tips {
            float: left;
            width: 100%;
            text-align: center;
            position: absolute;
            top: 45%;
            left: 0;
            font-size: 0.26rem;
            color: #fff;
        }
    </style>
	<#--
    <script>
        $(function () {
            $('input[type="text"],input[type="password"]').validate();

        });
        $.fn.validate = function () {
            $(this).blur(function () {
                var tips = $(this).attr('tips') == undefined || $(this).attr('tips').length == 0 ? "请输入信息！" : $(this).attr('tips');
                var dialogDom = $("#dialog");
                dialogDom.find(".tips").html(tips);

                dialogDom.fadeIn(function () {
                    setTimeout(function () {
                        dialogDom.find(".tips").html();
                        dialogDom.fadeOut();
                    }, 800);
                });
            });
        }
    </script>
    -->
</head>
<body class="onload_body">
<!-- header_top -->
<section class="container onload">
    <div class="top_heater">
        <a href="#" title="" class="onload_back"></a>
        <span class="onload_header">找回密码</span>
    </div>
</section>
<!-- header_top end -->
<!-- onload  -->
<form id="resetForm" class="onload_box" action="/touch/reset/password/save" method="post">
    <input class="text" type="text" placeholder="手机号" name="phone" id="phone" />

    <div class="mark_test">
        <input class="text" type="text" placeholder="验证码" name="code" id="code" />
        <span class="mark_btn" onclick="getSms();" id="getSms">获取验证码</span>
    </div>
    <input class="text" type="password" placeholder="新密码" name="password" id="password" />
    <input class="sub" type="button" onclick="resetPwd();" value="确定"/>
</form>
<!-- onload end -->
<a href="/touch/login" style="color:#f2f2f2;width:100%;display:block;text-align:center;height:1rem;margin-top:4rem">返回登录</a>
</body>
<script>
<#-- 定义倒计时 -->
var smsTime = 60;

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
		url : "/code/reset/password",
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

<#-- 找回密码的方法 -->
function resetPwd(){
	var phone = document.getElementById("phone").value;

	var pattern = /^1\d{10}$/;
	if (!pattern.test(phone)) {
	    alert("请输入正确的手机号码");
	    return;
	}

	<#-- 验证手机号码 -->
	$.ajax({
		url : "/touch/resetpassword/check",
		type : "POST",
		data : {
			phone : phone
		},
		success:function(res){
			if(0 !== res.status){
				alert("该手机号未注册");
			}else{
				<#-- 验证验证码长度是否正确 -->
				var code = document.getElementById("code").value;
				if(code && code.length === 4){
					<#-- 验证码是否输入正确 -->
					$.ajax({
						url : "/touch/resetpassword/code/check",
						type : "POST",
						data : {
							code : code
						},
						success:function(res){
							if(0 !== res.status){
								alert("您输入的验证码不正确");
								return;
							}else{
								<#-- 验证密码的长度 -->
								var password = document.getElementById("password").value;
								if(password && password.length >=6 && password.length <= 20){
									document.getElementById("resetForm").submit();
								}else{
									alert("密码的长度为6-20位");
									return;
								}
							}
						}
					})
				}else{
					alert("您输入的验证码不正确");
					return;
				}
			}
		}
	});
	
}
</script>
</html>
