<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>商品列表</title>
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
<!-- js -->
<script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<style type="text/css">
.search_box {
  overflow: hidden;
  z-index: 99;
  position: absolute;
  right: 0px;
  top: 0px;
  height: 100%;
  width: 0px;
  overflow: hidden;
  -webkit-transition: 1s;
  border-radius: 0.32rem;
  margin-top: 0.19rem;
  height: 0.52rem;
}
.search_box input {
  float: left;
  height: 0.5rem;
}
.search_box .text {
  width: 70%;
  padding-left: 0.2rem;
  background: white;
}
.search_box .sub {
  float: right;
  width: 19%;
  background: url(/images/top_header_search.png) no-repeat center;
  background-size: 0.3rem 0.3rem;
}
</style>
<script type="text/javascript">
	$(function(){
		list_choice();
		
		(function(){
			var oBtn = $('.header_search');
			var oBox =  $('.search_box');
			var tBtn = $('.goods_list');
			oBtn.click(function(){
				$(this).hide();
				oBox.css({width:'80%',border:'#cc1421 1px solid'});
				return false;
			});
			tBtn.click(function(){
				oBtn.show(1000);
				oBox.css({width:'0px',border:'none'});
				return false;
			});
		})();
	});
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container">
	<div class="top_heater">
		<a href="/touch" title="" class="header_back"></a>
		<span>商品分类</span>
		<a class="header_search"></a>
		<form class="search_box">
			<input class="text" type="text" placeholder="请输入搜索内容" name="" id="" value="" />
			<input class="sub" type="submit" name="" id="" value="" />
		</form>
	</div>
</section>
<!-- header_top end -->
<!-- good list -->
<div class="list_select">
	<div class="list_box">
		<span class="name">产品分类</span>
		<label class="guide"></label>
		<dl class="choice_goods">
			<dt>
				<#if level_one??>
					<#list level_one as item>
						<#if item??>
							<span id="level_one_box${item.id?c}" <#if level_one_id??&&level_one_id?c==item.id?c>class="active_span"</#if>>${item.title!''}<label></label></span>
						</#if>
					</#list>					
				</#if>
			</dt>
			<dd>
				<#if level_one??>
					<#list level_one as item>
						<#if item??>
							<div id="level_two_box${item.id?c}">
								<#if ("level_two"+item_index)?eval??>
									<#list ("level_two"+item_index)?eval as sub_item>
										<#if sub_item??>
											<a href="/touch/goods?categoryId=${sub_item.id?c}<#if sort??>&sort=${sort!'0'}</#if>" <#if level_two_id??&&level_two_id?c==sub_item.id?c>style="color:#e64b49;"</#if> title="<#if setting??>${setting.title!''}-${sub_item.seoTitle!''}</#if>">${sub_item.title!''}<label></label></a>
										</#if>
									</#list>
								</#if>
							</div>
						</#if>
					</#list>
				</#if>
			</dd>
		</dl>
	</div>
	<div class="list_box" onclick="window.location.href='/touch/goods?sort=<#if sort??&&sort=0>1<#else>0</#if><#if level_two_id??>&categoryId=${level_two_id?c}</#if>'">
		<span class="name">价格排序</span>
		<label class="guide <#if sort??&&sort==0>up</#if>"></label>
	</div>
</div>
<ul class="goods_list">
	<#if goods_list??>
		<#list goods_list as item>
			<#if item??>
				<li onclick="window.location.href='/touch/goods/detail/${item.id?c}'">
					<a title="<#if setting??>${setting.title!''}-</#if>${item.title!''}">
						<img alt="<#if setting??>${setting.title!''}-</#if>${item.title!''}" src="${item.coverImageUri!''}"/>
					</a>
					<p class="name">${item.title!''}</p>
					<label class="money">￥<#if item.salePrice??>${item.salePrice?string("0.00")}<#else>0.00</#if></label>
					<span class="paid">${item.soldNumber!'0'}人已买</span>
				</li>
			</#if>
		</#list>
	</#if>
</ul>
<!-- good list end -->
</body>
	<#if level_one_id??>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#level_one_box${level_one_id?c}").click();
			});
		</script>
	</#if>
</html>
