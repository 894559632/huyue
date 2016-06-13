<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <title><#if setting??>${setting.title!''}-</#if>中奖记录</title>
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
<style type="text/css">
	.rule {
	  position: absolute;
	  top: 0px;
	  width: 1rem;
	  height: 0.88rem;
	  line-height: 0.88rem;
	  display: block;
	  right: 0.3rem;
	  font-size: 0.25rem;
	  color: #da251e;
	}
	.rule_box{
		width: 100%;
	}
	.rule_box li{
		height: 0.8rem;
		width: 5.8rem;
		padding: 0 0.3rem;
		line-height: 0.4rem;
		margin-top: 0.1rem;
		background: white;
	}
	.rule_box li .left{
		float: left;
	}
	.rule_box li .right{
		float: right;
		color: #da251e;
	}
	.wait{
		width: 100%;
		height: 0px;
		background: rgba(0,0,0,0.8);
		position: absolute;
		left: 0px;
		bottom: 0px;
		color: white;
	}
	.wait_box{
		width: 2rem;
		margin: auto;
		overflow: hidden;
	}
	.wait img{
		margin-top: 0.12rem;
		float: left;
		-webkit-transition:30s linear;
	}
	.wait .wait_img{
		width:0.36rem;
		height: 0.36rem;
		float: left;
		margin-top: 0.16rem;
	}
	.wait span{
		font-size: 0.12rem;
		margin-top: 14px;
		padding-left: 20px;
		float: left;
	}
</style>
<#--
<script type="text/javascript">
$(function(){
	//最小高度
	var mh = $(window).height() - $('.top_heater').height();
	$('.rule_box').css({minHeight:mh});
	
	
	goodsIn();
	function goodsIn(){
		$(document).bind('scroll',ajaxFn);
		function ajaxFn(){
			if($(document).scrollTop() >= $(document).height()-$(window).height()){
				$(document).ajaxStart(function(){
					$('.wait').animate({height:'50px'});
					$('.wait img').css({WebkitTransform:'rotate(-10000deg)'});
				});
				
				var page = document.getElementById("page").value;
				
			    $.ajax({
			    	type:'POST',
			    	url:'/touch/user/point/detail/get',
			    	data:{
			    		page:page
			    	},
			    	success:function(data){
				    	if("" !== data){
				    		var detail = document.getElementById("detail");
				    		var html = detail.innerHTML;
				    		detail.innerHTML = html + data;
				    	
				    		document.getElementById("page").value = page + 1;
				    	}
			    		//恢复高度(保留恢复高度)
			    		$('html').height($(document).height());
			    	}
			    });
			    //解除监听
	    		$(document).unbind('scroll',ajaxFn);
	    		//加载成功后恢复监听
	    		$(document).ajaxSuccess(function(){
	    			$(document).bind('scroll',ajaxFn);
	    			$('.wait').animate({height:'0px'});
	    			$('.wait img').css({WebkitTransform:'rotate(0deg)'});
	    		});
			};
		};
	};
});
</script>
-->
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>中奖记录</span>
	</div>
</section>
<#if log_list??&&log_list?size gt 0>
	<ul class="rule_box" id="detail">
		<#list log_list as item>
			<#if item??>
				<li>
					<p>${item.awardTitle!''}</p>
					<span style="float:right;color:green;" >数量：1</span>
					<div class="left"><#if item.lotteryTime??>${item.lotteryTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
				</li>
			</#if>
		</#list>
	</ul>
	<!-- header_top end -->
	<!-- 我的积分 -->
	<#--
	<div class="wait">
		<div class="wait_box">
			<div class="wait_img">
				<img alt="" src="/touch/images/wait.png"/>
			</div>
			<span>正在加载...</span>
		</div>
	</div>
	<input type="hidden" id="page" value="0">
	-->
</#if>
</body>
</html>
