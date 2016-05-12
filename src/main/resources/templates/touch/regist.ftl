<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <title><#if setting??>${setting.title!''}-</#if>注册</title>
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
    <script type="text/javascript" src="/touch/js/DengXiao/base.js"></script>
    <script type="text/javascript" src="/touch/js/DengXiao/user.js"></script>

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
        <span class="onload_header">注册</span>
    </div>
</section>
<!-- header_top end -->
<!-- onload  -->
<form class="onload_box">
    <input class="text" type="text" placeholder="手机号" name="" id="" value=""/>

    <div class="mark_test">
        <input class="text" type="text" placeholder="手机验证码" name="" id="" value=""/>
        <span class="mark_btn" id="getSms" onclick="base.user.regist.getSms();">获取验证码</span>
    </div>
    <input class="text" type="text" placeholder="密码" name="" id="" value=""/>
    <input class="text" type="text" placeholder="真实姓名" name="" id="" value=""/>
    <div class="mark_test">
        <input class="text" type="text" placeholder="图形验证码" name="" id="imgSms" value=""/>
        <span class="mark_btn"><img src="/code" height="40" onclick="this.src = '/code?date='+Math.random();"/></span>
    </div>
    <input class="sub" type="submit" name="" id="" value="注册"/>

    <p class="forget_secret"></p>

    <div class="have_account"></div>
    <label class="ifaccount">已经拥有账号</label>
    <a class="register" href="/touch/login" title="<#if setting??>${setting.title!''}-</#if>登陆">登录</a>
</form>
<!-- onload end -->

<div class="dialog" id="dialog">
    <span class="tips">请输入正确手机号码！</span>
</div>
</body>
</html>
