<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>订单评价</title>
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
	<#if !readOnly>
		$(function(){
			var oul=document.getElementById('l_value');
			var oSec=oul.getElementsByTagName('section');
			for(i=0;i<oSec.length;i++){
				getStar(oSec[i]);
			}
			function getStar(obj){
				var oa=obj.getElementsByTagName('a');
				for(j=0;j<oa.length;j++){
					oa[j].index=j;
					oa[j].onclick=function(){
						for(i=0;i<oa.length;i++){
							oa[i].className='';
						}
						for(i=0;i<=this.index;i++){
							oa[i].className='active';
						}
					}
				}
			}
		});
	</#if>
</script>
<body class="body_gray">
<!-- header_top -->
<section class="container sure_order">
	<div class="top_heater">
		<a href="javascript:history.go(-1);" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back"></a>
		<span><#if readOnly>查看评论<#else>发表评论</#if></span>
	</div>
</section>
<!-- header_top end -->
<!-- 我的订单-产品分类 -->
<form>
	<#if orderGoodsList??>
		<ul class="l_value" id="l_value">
			<#list orderGoodsList as item>
				<#if item??>
					<li>
						<div class="div1">
							<i><img src="${item.goodsCoverImageUri!''}" alt="<#if setting??>${setting.title!''}-</#if>${item.goodsTitle!''}"></i>
							<textarea id="comment${item.goodsId?c}" <#if readOnly>readOnly="true"</#if>><#if ("comment"+item.goodsId?c)?eval??>${("comment"+item.goodsId?c)?eval.content!''}<#else>好评！</#if></textarea>
						</div>
						<div class="div3">
							<label class="lab1">商品星级</label>
							<section class="star" id="stars${item.goodsId?c}">
								<#if ("comment"+item.goodsId?c)?eval??&&("comment"+item.goodsId?c)?eval.stars??>
									<#list (1..("comment"+item.goodsId?c)?eval.stars) as item>
										<a class="active"></a>
									</#list>
									<#list (1..(5-("comment"+item.goodsId?c)?eval.stars)) as item>
										<a class=""></a>
									</#list>
								<#else>
									<a class="active"></a>
									<a class="active"></a>
									<a class="active"></a>
									<a class="active"></a>
									<a class="active"></a>
								</#if>
							</section>
						</div>
					</li>
				</#if>
			</#list>
		</ul>
	
		<!-- 我的订单-产品分类 -->
		<!-- 发表 -->
		<div style="width:100%;height:0.98rem"></div>
		<#if !readOnly>
			<div class="l_publish"><input type="button" style="-webkit-appearance:none;" onclick="userComment();" value="发表评价"></div>
		</#if>
		<!-- 发表 -->
	</#if>
</form>
</body>
<script>

	<#-- 定义一个用于按钮开关的布尔值变量 -->
	var isOpen = true;

	<#-- 定义一个数组，保存该订单下所有的商品id -->
	var goods_array = [
		<#if orderGoodsList??>
			<#list orderGoodsList as item>
				<#if item??>
					${item.goodsId?c}
				</#if>
				<#if (item_index + 1)!=orderGoodsList?size>
					,
				</#if>
			</#list>
		</#if>
	];
	
	<#-- 定义一个数组存储所有的评价 -->
	var comment_array = [];
	<#-- 定义一个数组存储所有的星级 -->
	var stars_array = [];
	
	<#-- 获取所有评价的方法 -->
	function getComment(){
		for(var i = 0; i < goods_array.length; i++){
			var goodsId = goods_array[i];
			if(goodsId){
				var comment = document.getElementById("comment" + goodsId);
				if(comment){
					comment_array.push(comment.value);
				}
			}
		}
	}
	
	<#-- 获取所有星级的方法 -->
	function getStars(){
		for(var i = 0; i < goods_array.length; i++){
			var goodsId = goods_array[i];
			if(goodsId){
				var stars = document.getElementById("stars" + goodsId);
				var a_tags = stars.children;
				<#-- 定义一个变量用于表示评价的星级 -->
				var rank = 0;
				for(var j = 0; j < a_tags.length; j++){
					var a = a_tags[j];
					var className = a.className;
					if(className === "active"){
						rank++;
					}
				}
			}
			stars_array.push(Number(rank));
		}
	}
	
	<#-- 发表评价的方法 -->
	function userComment(){
		if(isOpen){
			isOpen = false;
			
			getComment();
			getStars();
			
			$.ajax({
				url : "/touch/user/order/comment/save",
				method : "post",
				traditional : true,
				data : {
					orderNumber : "${orderNumber!''}",
					comments : comment_array,
					stars : stars_array
				},
				success:function(res){
					if(0 !== res.status){
						isOpen = true;
						alert(res.message);
						if(-2 === res.status){
							window.location.href = "/touch/login";
						}
					}else{
						alert("评价成功");
						window.location.href = "/touch/user/order/list?type=0";
					}
				}
			});
		}
	}
</script>
</html>
