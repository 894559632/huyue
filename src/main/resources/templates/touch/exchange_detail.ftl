<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>兑换记录</title>
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
	.l_goods dl{
		background: white;
		margin-top: 0.1rem;
	}
	.l_goods li{
		background: none;
	}
	.l_goods li .li_title{
		background: white;
		border-bottom: #DDDDDD 0.01rem solid;
	}
	.l_goods dl dt a img{
		border: #DDDDDD 1px solid;
	}
	.l_goods li .li_title .lab1{
		color: #999999;
	}
	.l_goods li .li_title .lab2{
		color: #444444;
	}
	.l_goods dl .dd2 .p2{
		position: absolute;
		right: 0.25rem;
		bottom: 0px;
	}
	.l_goods dl .dd1 p{
		position: absolute;
		left: 0rem;
		bottom: 0px;
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
	$('.l_goods').css({minHeight:mh});
	
	
	
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
				    	url:'/touch/user/point/exchange/detail/get',
				    	data:{
				    		page:page
				    	},
				    	success:function(data){
				    		isAjax = true;
				    	
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
			    }
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
		<span>兑换记录</span>
		<a class="rule a-show">积分规则</a>
	</div>
</section>
<!-- header_top end -->
<!-- 我的订单-产品分类 -->
<#if exchange_page??&&exchange_page.content?size gt 0>
	<ul class="l_goods" style="padding-bottom: 0px;" id="detail">
		<#list exchange_page.content as item>
			<#if item??>
				<li>
					<div class="li_title">
						<label class="lab1"><#if item.exchangeTime??>${item.exchangeTime?string("yyyy-MM-dd HH:mm:ss")}</#if></label>
					</div>
					<dl>
						<dt>
							<a href="/touch/goods/point/detail/${item.id?c}" title="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}">
								<img src="${item.goodsCoverImageUri!''}" alt="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}">
							</a>
						</dt>
						<dd class="dd1">
							${item.goodsTitle!''}
							<p class="p1">积分：<#if item.pointUsed??>${item.pointUsed?string("0.00")}<#else>0.00</#if></p>
						</dd>
						<dd class="dd2">
							<p class="p2">x${item.quantity!'0'}</p>
						</dd>
					</dl>
				</li>
			</#if>
		</#list>
	</ul>
	<!-- 我的订单-产品分类 -->
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
</body>
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
</html>
