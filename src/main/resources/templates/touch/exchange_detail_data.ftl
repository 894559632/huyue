<#if exchange_page??&&exchange_page.content?size gt 0>
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
</#if>