<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Language" content="zh-CN">
    <title><#if setting??>${setting.title!''}-</#if>积分明细</title>
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
	  width: 1.2rem;
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
	/*pop-ups*/
	.pop-ups {
		position: absolute;
		left: 0;
		top: 0;
		z-index: 11;
		display: none;
		width: 100%;
		height: 100%;
		background: rgba(0,0,0,0.6);
	}
	.pop-ups .pop-one {
		margin: 0 auto;
		margin-top: 40%;
		padding: .8rem 0;
		width: 6rem;
		min-height: 2.3rem;
		text-align: center;
		background-color: #fff;
		border-radius: .05rem
	}
	.pop-ups .pop-one img {
		display: block;
		margin: 0 auto;
		margin-bottom: .2rem;
		width: .8rem;
		height: .8rem;
	}
	.pop-ups .pop-one div {
		line-height: .6rem;
		font-size: .34rem;
		color: #666;
		display: inline-block;
	}
	.pop-ups .pop-one p{font-size: .25rem;margin: 0.2rem 0 0 0.4rem;float: left; text-align: left;}


/*pop-ups end*/
</style>
<script type="text/javascript">
var isAjax = true;
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
				
				if(isAjax){
					isAjax = false;
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
				    		isAjax = true;
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
			}
		};
	};
	$('.a-show').click(function(e){
		$('.pop-ups').show();
		e.stopPropagation();  //阻止冒泡事件
	});
    $('.pop-ups').click(function(){
    	$('.pop-ups').hide();
	});
    $(".pop-ups .pop-one").click(function(e){
        e.stopPropagation();
    });
	
});
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span>积分明细</span>
		<a class="rule a-show">积分规则</a>
	</div>
</section>
<#if point_page??&&point_page.content?size gt 0>
	<ul class="rule_box" id="detail">
		<#list point_page.content as item>
			<#if item??>
				<li>
					<p>
						<#switch item.type>
							<#case 1>消费增加积分<#break>
							<#case 2>管理员修改积分<#break>
							<#case 3>兑换使用积分<#break>
						</#switch>
						<span style="float:right;color:green;" ><#if item.fee??&&item.fee gt 0>+</#if><#if item.fee??>${item.fee?string("0.00")}<#else>0.00</#if>积分</span>
					</p>
					<div class="left"><#if item.changeTime??>${item.changeTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
					<div class="right">剩余：<#if item.changedPoint??>${item.changedPoint?string("0.00")}<#else>0.00</#if>积分</div>
				</li>
			</#if>
		</#list>
	</ul>
	<!-- header_top end -->
	<!-- 我的积分 -->
	<div class="wait">
		<div class="wait_box">
			<div class="wait_img">
				<img alt="" src="/touch/images/wait.png"/>
			</div>
			<span>正在加载...</span>
		</div>
	</div>
	<input type="hidden" id="page" value="0">
</#if>
<article class="pop-ups">
	<section class="pop-one" style="margin-top: 30%;">
		<div>积分规则
			<p>1.凡在虎跃商城购买商品的用户，每消费1元<br>&nbsp;&nbsp;&nbsp;即可获得1个商城积分；</p>
			<p>2.商城积分上不封顶；</p>
			<p>3.商城积分仅用于礼品兑换；</p>
			<p>4.商城积分最终解释权归虎跃工具所有。</p>
		</div>
	<section>
</article>
</body>
</html>
