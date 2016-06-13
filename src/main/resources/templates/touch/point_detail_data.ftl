<#if point_page??&&point_page.content?size gt 0>
	<#list point_page.content as item>
		<#if item??>
			<li>
				<p>
					<#switch item.type>
						<#case 1>消费增加积分<#break>
						<#case 2>管理员修改积分<#break>
						<#case 3>兑换使用积分<#break>
					</#switch>
					<span style="float:right;color:green;" ><#if item.fee??&&item.fee gt 0>+</#if><#if item.fee??>${item.fee?string("0.00")}<#else>0.00</#if>积分</span>
				</p>
				<div class="left"><#if item.changeTime??>${item.changeTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
				<div class="right">剩余：<#if item.changedPoint??>${item.changedPoint?string("0.00")}<#else>0.00</#if>积分</div>
			</li>
		</#if>
	</#list>
</#if>