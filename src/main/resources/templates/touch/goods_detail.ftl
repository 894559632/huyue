<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>商品详情</title>
<meta name="keywords" content="<#if setting??>${setting.seoKeywords!''}</#if>">
<meta name="description" content="<#if setting??>${setting.seoDescription!''}</#if>">
<meta name="copyright" content="<#if setting??>${setting.copyright!''}</#if>" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/main.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/my_swiper.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/touch/js/swiper.min.js"></script>
<script type="text/javascript" src="/touch/js/common.js"></script>
<script type="text/javascript" src="/touch/js/DengXiao/base.js"></script>
<script type="text/javascript" src="/touch/js/DengXiao/goods.js"></script>
<style type="text/css">
	.container{
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		z-index: 99;
	}
</style>
</head>
<script type="text/javascript">
	$(function(){
		var starId = [
			<#if comment_page??>
				<#list comment_page.content as item>
					"set_star${item_index}"				
					<#if (item_index+1)!=comment_page.content?size>
						,
					</#if>
				</#list>
			</#if>
		]
		var starNum = [
			<#if comment_page??>
				<#list comment_page.content as item>
					"${item.stars!'0'}"				
					<#if (item_index+1)!=comment_page.content?size>
						,
					</#if>
				</#list>
			</#if>
		]
		for(var i=0;i<starId.length;i++)
		{
			set_star('#'+starId[i],starNum[i]);//id + 默认显示个数
		};
		<#--
			collectFly();
		-->
		tabChange();
		function tabChange(){
		    $('.header_nav a').each(function(i){
		    	$('.header_nav a').eq(i).click(function(){
		    		$('.header_nav a').removeClass('active');
		    		$(this).addClass('active');
		    		switch(i){
		    			case 0:
		    			$(document).scrollTop($('#detail').offset().top-50);
		    			break;
		    			case 1:
		    			$(document).scrollTop($('#etail').offset().top-50);
		    			break;
		    			case 2:
		    			$(document).scrollTop($('#evaluta').offset().top-50);
		    			break;
		    		};
		    	});
		    });
		};
	});
</script>
<body class="gray_body">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1)" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<div class="header_nav">
			<div>
				<a class="active" title="<#if setting??>${setting.title!''}-</#if>商品详情">商品详情</a>
				<a title="<#if setting??>${setting.title!''}-</#if>商品参数">商品参数</a>
				<a title="<#if setting??>${setting.title!''}-</#if>商品评价">商品评价</a>
			</div>
		</div>
		<a href="/touch/cart" title="<#if setting??>${setting.title!''}-</#if>购物车" class="header_car"></a>
	</div>
</section>
<div style="width: 100%;height: 0.88rem;clear: both;"></div>
<!-- header_top end -->
<!-- index_banner  -->
<div class="index_banner">
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
	 		<#if goods??&&goods.showPictures??>
	 			<#list goods.showPictures?split(",") as item>
	 				<#if item??&&""!=item>
					    <div class="swiper-slide blue-slide">
					    	<a href="#">	    		
						    	<img src="${item}"/>
					    	</a>
					    </div>
				    </#if>
			    </#list>
		    </#if>
	  	</div>
	  	<div class="swiper-pagination"></div>
	</div>
	
	<script type="text/javascript">
	  var mySwiper = new Swiper('.index_banner .swiper-container',{
	    loop: true,
		autoplay: 3000,
		pagination : '.index_banner .swiper-pagination'
	  });	
	</script>
</div>		
<!-- swiper////////////////////////////////////////////////////////////////////// -->
<!-- index_banner end -->
<!-- goods_detail -->
<div class="goods_detail">
	<div class="title">
		<#if goods??>
			<h3>${goods.title!''}</h3>
			<p>${goods.subTitle!''}</p>
			<span>￥<#if goods.salePrice??>${goods.salePrice?string("0.00")}<#else>0.00</#if></span>
			<#--
				<label>快递：20元</label>
			-->
		</#if>
	</div>
	<#if goods.paramDetail??>
		<div class="etail_data" id="etail">
			<h3>商品参数</h3>
			<div>${goods.paramDetail!''}</div>
		</div>
	</#if>
	<div class="detail_evaluta" id="evaluta">
		<h3>商品评价</h3>
		<#if comment_page??>
			<#list comment_page.content as item>
				<#if item??>
					<div class="box">
						<div class="top">
							<span>
								<img alt="<#if setting??>${setting.title!''}-</#if>头像" src="${item.userHeadUri!'/touch/images/head.png'}"/>
							</span>
							<label>${item.username?substring(0,3)}****${item.username?substring(7)}</label>
						</div>
						<div class="center">${item.content!''}</div>
						<#if item.showPictures??&&item.showPictures!="">
							<div class="detail_evaluta_img">
								<#list item.showPictures?split(",") as pic>
									<#if pic??>
										<span>
											<img alt="<#if setting??>${setting.title!''}-</#if>评论图片" src="${pic!''}"/>
										</span>
									</#if>
								</#list>
							</div>
						</#if>
						<div class="store_stare_box">
							<div id="set_star${item_index}" class="rich_star">
								<span>
									<img alt="" src="/touch/images/star_no.png">
									<img alt="" src="/touch/images/star_yes.png">
								</span>
								<span>
									<img alt="" src="/touch/images/star_no.png">
									<img alt="" src="/touch/images/star_yes.png">
								</span>
								<span>
									<img alt="" src="/touch/images/star_no.png">
									<img alt="" src="/touch/images/star_yes.png">
								</span>
								<span>
									<img alt="" src="/touch/images/star_no.png">
									<img alt="" src="/touch/images/star_yes.png">
								</span>
								<span>
									<img alt="" src="/touch/images/star_no.png">
									<img alt="" src="/touch/images/star_yes.png">
								</span>
							</div>
							<time><#if item.commentTime??>${item.commentTime!''}</#if></time>
						</div>
					</div>
				</#if>
			</#list>
		</#if>
	</div>
</div>
<!-- goods_detail end -->
<!-- detail_img -->
	<div class="detail_evaluta" id="detail">
		<h3 style="line-height:40px;">详细描述</h3>
		<#if goods??>${goods.detail!''}</#if>
	</div>
<!-- detail_img -->
<!-- buy_now -->
<div style="float: left;width:100%;height:0.94rem ;"></div>
<div class="buy_now">
	<a class="tel one" title="<#if setting??>${setting.title!''}-</#if>操作">
		<div>
			<span <#if setting??>onclick="window.location.href='tel:${setting.telephone!''}'"</#if>>
				<img alt="<#if setting??>${setting.title!''}-</#if>客服中心" src="/touch/images/call.png"/>
			</span>
			<label>客服</label>
		</div>
		<div id="yes_star">
			<span onclick="base.goods.collect(${goodsId?c});">
				<img id="collect_no" alt="<#if setting??>${setting.title!''}-</#if>收藏" src="/touch/images/star_no.png" style="<#if isCollect??&&isCollect>display:none<#else>display:block;</#if>"/>
				<img class="yes"  id="collect_yes" alt="<#if setting??>${setting.title!''}-</#if>收藏" src="/touch/images/star_yes.png" style="<#if isCollect??&&!isCollect>display:none;<#else>display:block;</#if>"/>
			</span>
			<label>收藏</label>
		</div>
	</a>
	<a class="buy two" href="javascript:base.goods.buyNow(${goodsId?c});" title="<#if setting??>${setting.title!''}-</#if>立即购买">立刻购买</a>
	<a class="car two" href="javascript:base.goods.addCart(${goodsId?c});" title="<#if setting??>${setting.title!''}-</#if>加入购物车">加入购物车</a>
</div>
<!-- buy_now end-->
</body>
</html>
