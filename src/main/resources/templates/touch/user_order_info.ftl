<#if order_page??&&order_page.content?size gt 0>
	<#list order_page.content as item>
		<#if item??>
			<li>
				<div class="li_title">
					<label class="lab1">订单号：<span>${item.orderNumber!''}</span></label>
					<#if item.statusId??>
						<label class="lab2">
							<#switch item.statusId>
								<#case 2>待付款<#break>
								<#case 3>待发货<#break>
								<#case 4>待收货<#break>
								<#case 5>待评价<#break>
								<#case 6>已完成<#break>
								<#case 7>已取消<#break>
							</#switch>
						</label>
					</#if>
				</div>
				<#if item.orderGoodsList??&&item.orderGoodsList?size gt 0>
					<#list item.orderGoodsList as goods>
						<#if goods??>
							<dl>
								<dt><a href="/touch/goods/detail?id=${goods.goodsId?c}" title="<#if setting??>${setting.title!''}-</#if>${goods.goodsTitle!''}"><img src="${goods.goodsCoverImageUri!''}" alt="<#if setting??>${setting.title!''}-</#if>${goods.goodsTitle!''}"></a></dt>
								<dd class="dd1">${goods.goodsTitle!''}</dd>
								<dd class="dd2">
									<p class="p1">￥<#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></p>
									<p class="p2">x${goods.quantity!'0'}</p>
								</dd>
							</dl>
						</#if>
					</#list>
				</#if>
				<div class="li_all">共<span>${item.orderGoodsList?size!'0'}</span>件商品 合计：￥<span class="sp2"><#if item.totalPrice??>${item.totalPrice?string("0.00")}<#else>0.00</#if></span></div>
				<div class="li_pay">
					<a href="/touch/order/detail/${item.id?c}" title="<#if setting??>${setting.title!''}-</#if>查看详情" class="a2">查看详情</a>
					<#if item.statusId??>
							<#switch item.statusId>
								<#case 1>
									<a href="/touch/pay?orderId=${item.id?c}" title="<#if setting??>${setting.title!''}-</#if>兑换奖品" class="a3">兑换奖品</a>
								<#break>
								<#case 2>
									<#if item.orderNumber??&&!item.orderNumber?contains("CJDD")>
										<a href="javascript:cancelOrder(${item.id?c})" title="<#if setting??>${setting.title!''}-</#if>取消订单" class="a2">取消订单</a>
									</#if>
									<a href="/touch/pay?orderId=${item.id?c}" title="<#if setting??>${setting.title!''}-</#if>去支付" class="a3">去支付</a>
								<#break>
								<#case 3>
									<#--
										<a href="javascript:cancelOrder(${item.id?c})" title="<#if setting??>${setting.title!''}-</#if>取消订单" class="a2">取消订单</a>
									-->
								<#break>
								<#case 4>
									<a href="javascript:signOrder(${item.id?c})" title="<#if setting??>${setting.title!''}-</#if>确认收货" class="a3">确认收货</a>
								<#break>
								<#case 5>
									<a href="/touch/user/order/comment?orderId=${item.id?c}" title="<#if setting??>${setting.title!''}-</#if>" class="a3">去评价</a>
								<#break>
								<#case 6>
									<a href="/touch/user/order/comment?orderId=${item.id?c}" title="<#if setting??>${setting.title!''}-</#if>" class="a3">查看评价</a>
								<#break>
							</#switch>
						</#if>
				</div>
			</li>
		</#if>
	</#list>
</#if>