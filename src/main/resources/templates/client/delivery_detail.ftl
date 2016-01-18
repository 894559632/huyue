<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
<head>
<meta charset="UTF-8">
<meta name="keywords" content="">
<meta name="copyright" content="" />
<meta name="description" content="">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>乐易装</title>
<!-- css -->
<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
<link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
<link rel="stylesheet" type="text/css" href="/client/css/x_gu_sales.css"/>
<!-- js -->
<script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bdd6b0736678f88ed49be498bff86754"></script>
<script type="text/javascript">

var map, geolocation;

//加载地图，调用浏览器定位服务
map = new AMap.Map('container');

setInterval("timer()", 1000 * 60 * 60);
    
function timer() {
    map.plugin('AMap.Geolocation', function() {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 2000          //超过10秒后停止定位，默认：无穷大
        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
    });
    
    var geocoder;
    
    //解析定位结果
    function onComplete(data) {
    	AMap.service('AMap.Geocoder',function(){//回调函数
	        //实例化Geocoder
	        geocoder = new AMap.Geocoder({
	            city: "010" //城市，默认：“全国”
	        });
	        
			var lnglatXY=[data.position.getLng(), data.position.getLat()];//地图上所标点的坐标
			
	        geocoder.getAddress(lnglatXY, function(status, result) {
	            if (status === 'complete' && result.info === 'OK') {
	               //获得了有效的地址信息:
	               warning(result.regeocode.formattedAddress);
	               
	               $.ajax({ 
						url: "/delivery/geo/submit", 
						type: "post",
						dataType: "json",
						data: 
						{
							"longitude": data.position.getLng(), 
							"latitude": data.position.getLat(),
							"accuracy": data.accuracy,
							"isConverted": data.isConverted,
							"formattedAddress" : result.regeocode.formattedAddress
						},
						success: function(data)
						{
				        	if (data.code != 0)
				        	{
				        		warning(data.message);
				        	}
				  		}
					});
	            }else{
	               //获取地址失败
	            }
	        });
	    })
    }
}
</script>
<script>
// 确认送达
function submitDelivery(id)
{
	if (null == id)
	{
		warning("ID不能为空");
		return;
	}
	
	$.ajax({ 
		url: "/delivery/submitDelivery", 
		type: "post",
		dataType: "json",
		data: {"id": id},
		success: function(data)
		{
        	if (data.code == 0)
        	{
        		warning("确认成功");
        		window.location.reload();
        	}
        	else
        	{
        		warning(data.message);
        	}
  		}
	});
}

function submitOwnMoney()
{
	var payed = document.getElementById("payed").value;
	var owned = document.getElementById("owned").value;
	
	if (null == payed || null == owned || "" == payed || "" == owned)
	{
		warning("请输入正确的金额");
		return;
	}
	
	$.ajax({ 
		url: "/delivery/submitOwnMoney/1", 
		type: "post",
		dataType: "json",
		data: {"payed": payed, "owned": owned},
		success: function(data)
		{
        	if (data.code == 0)
        	{
        		warning("申请成功");
        		window.location.reload();
        	}
        	else
        	{
        		warning(data.message);
        	}
  		}
	});
}
</script>
</head>
<body class="bgc-f3f4f6">
<div id='container'></div>
<div id="tip"></div>
<#include "/client/common_warn.ftl" />
  <!--弹窗-->
  <div id="bg"></div>
  <div id="arreabox">
    <form>
      <div class="title">申请欠款</div>   
      <div class="text1">已交款<input type="text" id="payed" value="0">元</div>
      <div class="text1">欠款&nbsp;&nbsp;<input type="text" id="owned" value="0">元</div>
      <div class="button-group">
        <a class="sure" href="#" onclick="pupclose()">关闭</a>
        <a class="cancle" href="#" onclick="submitOwnMoney()">提交</a>
      </div> 
    </form>
  </div>
  <script type="text/javascript">
    // $("#bg").height($(window).height());
    function pupopen(){
      document.getElementById("bg").style.display="block";
      document.getElementById("arreabox").style.display="block" ;
    }
    function pupclose(){
      document.getElementById("bg").style.display="none";
      document.getElementById("arreabox").style.display="none" ;
    }
  </script>
  <!--弹窗 END-->

  <!-- 头部 -->
    <#-- 引入警告提示样式 -->
    <#include "/client/common_warn.ftl">
    <#-- 引入等待提示样式 -->
    <#include "/client/common_wait.ftl">   
      <header>
        <a class="back" href="/delivery"></a>
        <p>详情产看</p>
      </header>
      <!-- 头部 END -->

	<#if td_order??>
  <!-- 详情查看 -->
  <article class="look-details">
    <!-- 配送详情 -->
    <section>
      <div class="title">配送详情</div>
      <div class="content">
      	<#if td_order.statusId==4 || td_order.statusId==3>
      		<div class="mesg">预计送达时间：${td_order.deliveryDate!''}</div>
      	<#elseif td_order.statusId==5 || td_order.statusId==6>
      		<div class="mesg">送达时间：${td_order.deliveryTime!''}</div>
      	</#if>
        <div class="mesg">收货人姓名：${td_order.shippingName!''}</div>
        <div class="mesg">手机号码：${td_order.shippingPhone!''}</div>
        <div class="mesg">收货地址：${td_order.shippingAddress!''}</div>
        <div class="mesg">配送信息：由乐易装专送提供高品质配送服务</div>
      </div>
    </section>
    <!-- 订单详情 -->
    <section>
      <div class="title">订单详情</div>
      <div class="content">
        <div class="mesg">订单号码：${td_order.orderNumber!''}</div>
        <#if td_order.orderGoodsList??>
        	<#list td_order.orderGoodsList as item>
        		<div class="mesg">产品：${item.goodsTitle!''} * ${item.quantity!'0'}</div>
        	</#list>
        </#if>
        <#if td_order.presentedList??>
        	<#list td_order.presentedList as item>
        		<div class="mesg">活动赠品：${item.goodsTitle!''} * ${item.quantity!'0'}</div>
        	</#list>
        </#if>
        <#if td_order.giftGoodsList??>
        	<#list td_order.giftGoodsList as item>
        		<div class="mesg">赠品：${item.goodsTitle!''} * ${item.quantity!'0'}</div>
        	</#list>
        </#if>
        <div class="mesg">支付方式：${td_order.payTypeTitle!''}</div>
      </div>
    </section>
    <!-- 申请欠款 -->
    <section>
      <div class="title">申请欠款</div>
      <div class="content">
        <div class="mesg">已交款：${td_order.actualPay!'0'}元</div>
        <div class="mesg">欠款：<#if td_order.totalPrice?? && td_order.actualPay??>${td_order.totalPrice-td_order.actualPay}<#else>0</#if>元</div>
      </div>
    </section>
    <#if td_order.statusId == 4>
    <a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="submitDelivery(1)">确认送达</a>
    <a class="btn-submit-save bgc-ff8e08" <#if td_order.photo??>href="javascript:;" style="background:#999"<#else>href="javascript:photo();"</#if> >拍照上传</a>
    </#if>
    <#if td_order.statusId != 6>
    <a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="pupopen()">申请欠款</a>
    </#if>
    <div style="display:none">
        <form id="imgUpload" action="/delivery/img" enctype="multipart/form-data" method="post">
            <input type="file" onchange="upload()" name="Filedata" id="clickFile">
            <input type="text" name="orderNumber"  value="${td_order.orderNumber!''}">
            <input type="text" name="id" value="<#if id??>${id?c}<#else>0</#if>">
        </form>
    </div>
    <script>
        $(function(){
            var msg = "${msg!''}";
            if("" != msg){
                if(0 != msg){
                    warning("上传失败");
                }else if(0 == msg){
                    warning("上传成功")
                }
            }
        });
    
        function photo(){
            var ele = document.getElementById("clickFile");
            ele.click();
        }
        
        function upload(){
            var form = document.getElementById("imgUpload");
            wait();
            form.submit();
        }
    </script>
  </article>
  <!-- 详情查看 END -->
  </#if>

  <div class="clear h66"></div>

</body>
</html>