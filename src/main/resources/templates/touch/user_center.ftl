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
<link href="/touch/css/add.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<script type="text/javascript">
	$(function(){
	});
</script>
<body class="body_gray">
<!-- header_top -->
<dl class="l_ltitle">
	<dt><a href="javascript:changeHeads();" title="<#if setting??>${setting.title!''}-</#if>用户中心"><img src="${user.headImgUri!''}" alt="<#if setting??>${setting.title!''}-</#if>用户中心"></a></dt>
	<dd class="dd1">${user.username!''}</dd>
	<#-- 触屏版用户头像上传 -->
	<form id="uploadImgForm" enctype="multipart/form-data" action="/touch/user/center/headImage" method="post">
           <input style="display:none" name="Filedata" type="file"  capture="camera" accept="image/*" onchange="getFile();" id="filebutton">
    </from>
    <script>
	    function changeHeads(){
	        var filebutton = document.getElementById("filebutton");
	        filebutton.click();
	    }
	    function getFile(){
	        document.getElementById("uploadImgForm").submit();
	        
	    }
    </script>
</dl>
<!-- header_top end -->
<!-- 全部订单 -->
<div class="l_all_orders">
	<a href="/touch/user/order/list?type=0" title="<#if setting??>${setting.title!''}-</#if>全部订单" class="a1"><p>全部订单</p></a>
	<a href="/touch/user/order/list?type=2" title="<#if setting??>${setting.title!''}-</#if>待付款订单" class="a2">
		<p>待付款</p>
		<#if unpay_number??&&unpay_number!=0>
			<span>${unpay_number!'0'}</span>
		</#if>
	</a>
	<a href="/touch/user/order/list?type=3" title="<#if setting??>${setting.title!''}-</#if>待发货订单" class="a2">
		<p>待发货</p>
		<#if uncomment_number??&&uncomment_number!=0>
			<span>${uncomment_number!'0'}</span>
		</#if>
	</a>
	<a href="/touch/user/order/list?type=4" title="<#if setting??>${setting.title!''}-</#if>待收货订单" class="a2">
		<p>待收货</p>
		<#if unsign_number??&&unsign_number!=0>
			<span>${unsign_number!'0'}</span>
		</#if>
	</a>
</div>
<!-- 全部订单-结束 -->
<!-- 分类1 -->
<div class="l_classify1">
	<a href="/touch/user/point" title="<#if setting??>${setting.title!''}-</#if>积分中心"><p>积分中心</p></a>
	<a href="/touch/user/collect" title="<#if setting??>${setting.title!''}-</#if>我的收藏"><p>我的收藏</p></a>
	<a href="/touch/user/visited" title="<#if setting??>${setting.title!''}-</#if>历史记录"><p>历史记录</p></a>
</div>
<!-- 分类1-结束 -->
<!-- 分类2 -->
<div class="l_classify2">
	<a href="/touch/user/info" title="<#if setting??>${setting.title!''}-</#if>个人信息"><p>个人信息</p></a>
	<a href="/touch/user/password" title="<#if setting??>${setting.title!''}-</#if>修改密码"><p>修改密码</p></a>
	<a href="/touch/user/address" title="<#if setting??>${setting.title!''}-</#if>收货地址"><p>收货地址</p></a>
	<a href="/touch/lottery" title="<#if setting??>${setting.title!''}-</#if>积分抽奖"><p>积分抽奖</p></a>
	<a href="/touch/user/advice" title="<#if setting??>${setting.title!''}-</#if>意见反馈"><p>意见反馈</p></a>
	<a <#if setting??>href="tel:${setting.telephone!''}"</#if> title="<#if setting??>${setting.title!''}-</#if>客服中心"><p>客服中心</p></a>
</div>
<!-- 分类2-结束 -->
<a href="/touch/logout" style="width:100%;height:1.8rem;color:#ffffff;background:#da251e;display:block;font-size:.3rem;text-align:center;line-height:0.8rem;margin-top:.2rem;">退出登录</a>
<!-- footer -->
<section class="footer">
	<nav>
		<a href="/touch" title="<#if setting??>${setting.title!''}-</#if>首页">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>首页" src="/touch/images/footer_icon01.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>首页" src="/touch/images/footer_icon11.png"/>
			</span>
			<label>首页</label>
		</a>
		<a href="/touch/goods" title="<#if setting??>${setting.title!''}-</#if>分类">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>分类" src="/touch/images/footer_icon02.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>分类" src="/touch/images/footer_icon22.png"/>
			</span>
			<label>分类</label>
		</a>
		<a href="/touch/cart" title="<#if setting??>${setting.title!''}-</#if>购物车">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>购物车" src="/touch/images/footer_icon03.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>购物车" src="/touch/images/footer_icon33.png"/>
			</span>
			<label>购物车</label>
		</a>
		<a href="/touch/lottery" title="<#if setting??>${setting.title!''}-</#if>抽奖">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>抽奖" src="/touch/images/footer_icon04.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>抽奖" src="/touch/images/footer_icon44.png"/>
			</span>
			<label>抽奖</label>
		</a>
		<a href="/touch/user" title="<#if setting??>${setting.title!''}-</#if>我">
			<span>
				<img class="active_img" alt="<#if setting??>${setting.title!''}-</#if>我" src="/touch/images/footer_icon05.png"/>
				<img class="disable_img" alt="<#if setting??>${setting.title!''}-</#if>我" src="/touch/images/footer_icon55.png"/>
			</span>
			<label class="active_label">我</label>
		</a>
	</nav>
</section>
<!-- footer end -->
</body>
</html>
