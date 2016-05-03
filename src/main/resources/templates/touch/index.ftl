<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>首页</title>
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
<body style="background: #f2f2f2;">
<!-- swiper////////////////////////////////////////////////////////////////////// -->			
<div class="index_banner">
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
	 		<#if touch_top_ad??>
	 			<#list touch_top_ad as item>
	 				<#if item??>
					    <div class="swiper-slide blue-slide">
					    	<a href="${item.linkUri!''}">	    		
						    	<img src="${item.fileUri!''}"/>
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
<!-- search -->
<section class="search">
<form>
	<input type="" name="" id="" value="" placeholder="商品关键字" />
</form>
</section>
<!-- search end-->
<!-- integral -->
<section class="integral">
	<ul class="box">
		<li class="index_title">
			<label></label>
			<a href="#" title=""></a>
			<span>天天疯抢</span>
		</li>
		<li class="main_box">
			<div class="swiper-container">
		        <div class="swiper-wrapper">
		        	<#if indexRecommend_point_page??&&indexRecommend_point_page.content??&&indexRecommend_point_page.content?size gt 0>
			        	<#list indexRecommend_point_page.content as item>
			        		<#if item??>
					            <div class="swiper-slide">
					            	<a class="recommend_good_box" href="/touch/goods/detail/${item.id?c}">
						            	<div>
						            		<img alt="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}" src="${item.coverImageUri!''}"/>
						            	</div>
					            		<p>${item.title!''}</p>
					            	</a>
					            </div>
				            </#if>
			            </#list>
		            </#if>
		        </div>				     
		        <!-- Add Pagination -->
		        <!-- Add Arrows -->
		    </div>
		    <!--<div class="swiper-pagination"></div>-->
		    <!-- Swiper JS -->
		
		    <!-- Initialize Swiper -->
		    <script>
		    var swiper = new Swiper('.integral .swiper-container', {
		        pagination: '.integral .swiper-pagination',
		        slidesPerView:4,
		        paginationClickable: true,
		        spaceBetween: 30
	
		    });
		    </script>
		</li>
	</ul>
</section>
<!-- integral end -->
<!-- index_nav -->
<section class="index_nav">
	<ul class="box">
		<li>
			<a href="#" title="">
				<img alt="" src="/touch/images/nav_icon01.png"/>
			</a>
			<p>积分</p>
		</li>
		<li>
			<a href="#" title="">
				<img alt="" src="/touch/images/nav_icon02.png"/>
			</a>
			<p>抽奖</p>
		</li>
		<li>
			<a href="/touch/user/collect" title="<#if setting??>${setting.title!''}-</#if>收藏">
				<img alt="<#if setting??>${setting.title!''}-</#if>收藏" src="/touch/images/nav_icon03.png"/>
			</a>
			<p>收藏</p>
		</li>
		<li>
			<a href="#" title="">
				<img alt="" src="/touch/images/nav_icon04.png"/>
			</a>
			<p>分类</p>
		</li>
	</ul>
</section>
<!-- index_nav end -->
<!-- best_goods -->
<section class="best_goods">
	<ul class="box">
		<li class="index_title">
			<label></label>
			<a href="#" title=""></a>
			<span>更多商品</span>
		</li>
		<li class="main_box">
			<div class="left">
				<#if indexRecommend_goods_page??&&indexRecommend_goods_page.content??&&indexRecommend_goods_page.content?size gt 0>
					<#list indexRecommend_goods_page.content as item>
						<#if item??&&item_index==0>
							<p>${item.title!''}</p>
							<label>¥${item.salePrice?string("0.00")}</label>
							<a href="/touch/goods/detail/${item.id?c}" title="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}">
								<img alt="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}" src="${item.coverImageUri!''}"/>
							</a>
						</#if>
					</#list>
				</#if>
			</div>
			<div class="right">
				<#if indexRecommend_goods_page??&&indexRecommend_goods_page.content??&&indexRecommend_goods_page.content?size gt 0>
					<#list indexRecommend_goods_page.content as item>
						<#if item??&&item_index==1>
							<div>
								<p><span>${item.title!''}</span><label>¥${item.salePrice?string("0.00")}</label></p>
								<a href="/touch/goods/detail/${item.id?c}" title="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}">
									<img alt="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}" src="${item.coverImageUri!''}"/>
								</a>
							</div>
						</#if>
						<#if item??&&item_index==2>
							<div>
								<p><span>${item.title!''}</span><label>¥${item.salePrice?string("0.00")}</label></p>
								<a href="/touch/goods/detail/${item.id?c}" title="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}">
									<img alt="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}" src="${item.coverImageUri!''}"/>
								</a>
							</div>
						</#if>
					</#list>
				</#if>
			</div>
		</li>
	</ul>
</section>
<!-- best_goods end -->
<!-- adv -->
<#if touch_middle_ad??>
	<a href="${touch_middle_ad.linkUri!''}">
		<section class="adv">
			<img alt="" src="${touch_middle_ad.fileUri!''}"/>
		</section>
	</a>
</#if>
<!-- adv box -->
<!-- hot_goods -->
<section class="hot_goods">
	<ul class="box">
		<li class="index_title">
			<label></label>
			<a href="#" title=""></a>
			<span>更多商品</span>
		</li>
		<li class="main_box">
			<#if hot_goods_page??&&hot_goods_page.content??&&hot_goods_page.content?size gt 0>
				<#list hot_goods_page.content as item>
					<#if item??>
						<div>
							<a href="/touch/goods/detail/${item.id?c}" title="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}">
								<img alt="<#if setting??>${setting.seoTitle!''}-</#if>${item.seoTitle!''}" src="${item.coverImageUri!''}"/>
							</a>
							<label>¥${item.salePrice?string("0.00")}</label>
							<p>${item.title!''}</p>
						</div>
					</#if>
				</#list>
			</#if>
		</li>
	</ul>
</section>
<!-- hot_goods end -->
<div class="footer_size"></div>
<!-- footer -->
<section class="footer">
	<nav>
		<a href="javascript:void(0);" title="<#if setting??>${setting.title!''}-</#if>首页">
			<span>
				<img class="active_img" alt="<#if setting??>${setting.title!''}-</#if>首页" src="/touch/images/footer_icon01.png"/>
				<img class="disable_img" alt="<#if setting??>${setting.title!''}-</#if>首页" src="/touch/images/footer_icon11.png"/>
			</span>
			<label class="active_label">首页</label>
		</a>
		<a href="/touch/goods" title="<#if setting??>${setting.title!''}-</#if>分类">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>分类" src="/touch/images/footer_icon02.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>分类" src="/touch/images/footer_icon22.png"/>
			</span>
			<label>分类</label>
		</a>
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>购物车">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>购物车" src="/touch/images/footer_icon03.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>购物车" src="/touch/images/footer_icon33.png"/>
			</span>
			<label>购物车</label>
		</a>
		<a href="#" title="<#if setting??>${setting.title!''}-</#if>抽奖">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>抽奖" src="/touch/images/footer_icon04.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>抽奖" src="/touch/images/footer_icon44.png"/>
			</span>
			<label>抽奖</label>
		</a>
		<a href="/touch/user" title="<#if setting??>${setting.title!''}-</#if>我">
			<span>
				<img alt="<#if setting??>${setting.title!''}-</#if>我" src="/touch/images/footer_icon05.png"/>
				<img alt="<#if setting??>${setting.title!''}-</#if>我" src="/touch/images/footer_icon55.png"/>
			</span>
			<label>我</label>
		</a>
	</nav>
</section>
<!-- footer end -->
</body>
</html>
