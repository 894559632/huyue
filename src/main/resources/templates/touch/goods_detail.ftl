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
</head>
<script type="text/javascript">
	$(function(){
		//试例
		var starId = ['set_star','set_star01'];
		var starNum = ['3','4']
		for(var i=0;i<starId.length;i++)
		{
			set_star('#'+starId[i],starNum[i]);//id + 默认显示个数
		};
		
		//collect fly 收藏效果
		collectFly();
	});
</script>
<body class="gray_body">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="" class="header_back"></a>
		<div class="header_nav">
			<div>
				<a class="active" href="#" title="">商品详情</a>
				<a href="#" title="">商品参数</a>
				<a href="#" title="">商品评价</a>
			</div>
		</div>
		<a href="#" title="" class="header_car"></a>
	</div>
</section>
<!-- header_top end -->
<!-- index_banner  -->
<div class="index_banner">
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
	 		<#if goods??&&goods.showPictures??>
	 			<#list goods.showPictures?split(",") as item>
				    <div class="swiper-slide blue-slide">
				    	<a href="#">	    		
					    	<img src="${item}"/>
				    	</a>
				    </div>
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
		<div class="etail_data">
			<h3>商品参数</h3>
			<div>${goods.paramDetail!''}</div>
		</div>
	</#if>
	<div class="detail_evaluta">
		<h3>商品评价</h3>
		<#if comment_page??>
			<#list comment_page.content as item>
				<#if item??>
					<div class="box">
						<div class="top">
							<span>
								<img alt="<#if setting??>${setting.title!''}-</#if>${item.username!''}" src="${item.userHeadUri}"/>
							</span>
							<label>${item.username!''}</label>
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
							<div id="set_star" class="rich_star">
								<span>
									<img alt="" src="images/star_no.png">
									<img alt="" src="images/star_yes.png">
								</span>
								<span>
									<img alt="" src="images/star_no.png">
									<img alt="" src="images/star_yes.png">
								</span>
								<span>
									<img alt="" src="images/star_no.png">
									<img alt="" src="images/star_yes.png">
								</span>
								<span>
									<img alt="" src="images/star_no.png">
									<img alt="" src="images/star_yes.png">
								</span>
								<span>
									<img alt="" src="images/star_no.png">
									<img alt="" src="images/star_yes.png">
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
<div class="detail_img"><#if goods??>${goods.detail!''}</#if></div>
<!-- detail_img -->
<!-- buy_now -->
<div style="float: left;width:100%;height:0.94rem ;"></div>
<div class="buy_now">
	<a class="tel one" title="">
		<div>
			<span>
				<img alt="" src="images/call.png"/>
			</span>
			<label>客服</label>
		</div>
		<div id="yes_star">
			<span>
				<img alt="" src="images/star_no.png"/>
				<img class="yes" id="yes" alt="" src="images/star_yes.png"/>
			</span>
			<label>收藏</label>
		</div>
	</a>
	<a class="buy two" href="#" title="">立刻购买</a>
	<a class="car two" href="#" title="">加入购物车</a>
</div>
<!-- buy_now end-->
</body>
</html>
