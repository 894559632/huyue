<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if setting??>${setting.title!''}-</#if>幸运大转盘</title>
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
<style>
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
<!-- js -->
<script type="text/javascript" src="/touch/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/touch/js/common.js"></script>
</head>
<script type="text/javascript">
	$(function(){
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
	
	
		<#-- 5和7代表中奖 -->
		var num = ${num!'1'};
		var turn = new Turntable();
			turn.init(num,[0,1,2,3,4,6,8,9],{
				tableData:['这是什么','对不起没中奖','3等奖','一等奖','好的','3等奖','一等奖','好的','3等奖'] 
			});
		<#--
		$('.turn_out span').click(function(){
			$('.turn_out').hide();
		});
		-->
	});
	
	function getAward(){
		$.ajax({
			url : "/touch/lottery/get/award",
			type : "POST",
			data : {
				award : ${num!''}
			},
			success:function(res){
				if(0 !== res.status){
					alert(res.message);
					if(-2 === res.status){
						window.location.href = "/touch/login"; 
					}
				}else{
					window.location.href = "/touch/pay?orderId=" + res.orderId;
				}
			}
		});
	} 
</script>
<body>
<!-- get and miss win out -->
<div class="turn_out" id="turn_out">
	<div class="miss">
		<img alt="" src="/touch/images/miss_turn.png"/>
		<span onclick="window.location.reload();">再来一次</span>
	</div>
	<div class="get">
		<img alt="" src="/touch/images/get_turn.png"/>
		<span onclick="getAward();">点击领取</span>
	</div>
</div>
<!-- get and miss win out -->
<!-- header_top -->
<section class="container turn_header">
	<div class="top_heater">
		<a href="javascript:history.go(-1)" title="<#if setting??>${setting.title!''}-</#if>返回" class="header_back" style="background:url(/touch/images/turn_back.png)no-repeat center"></a>
		<span class="onload_header">抽奖</span>
	</div>
</section>
<!-- header_top end -->
<!-- turn table -->
<a class="active_detail a-show" title="<#if setting??>${setting.title!''}-</#if>活动详情">活动详情</a>
<div class="turn_table">
	<div class="turn_box">
		<div id="guide">
			<img alt="" src="/touch/images/guide.png"/>
		</div>
	</div>
	<div class="turn_num">
		<a href="/touch/lottery/log" title="<#if setting??>${setting.title!''}</#if>中奖记录">中奖记录</a>
		<p>抽奖次数：<span id="count"><#if user??>${user.lottery!'0'}</#if></span>次</p>
	</div>
</div>
<!-- turn table end -->
<!-- trun list -->

<#if lottery_page??&&lottery_page.content?size gt 0>
	<div class="turn_list">
		<h3>获奖名单：</h3>
		<#list lottery_page.content as item>
			<#if item??>
				<p>用户<span>${item.username?substring(0,3)}****${item.username?substring(7)}</span>抽得<label>${item.awardTitle!''}</label>一件</p>
			</#if>
		</#list>
	</div>
<#else>
	<div style="width:100%;min-height:3rem;background:#d02d48;margin-top:-0.1rem;">&nbsp;</div>
</#if>
<!-- trun list end -->
<article class="pop-ups">
	<section class="pop-one">
    	<div>活动详情</div>
			<p>1.一次性消费100元以上即可获得一次抽奖机会；</p>
			<p>2.本活动长期有效；</p>
			<p>3.本活动最终解释权归虎跃工具所有。</p>
    <section>
</article>

</body>
</html>
