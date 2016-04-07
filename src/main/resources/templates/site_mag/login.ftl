<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>虎跃后台管理系统</title>
<!-- css -->
<link href="/mag/module/easy-ui/themes/bootstrap/easyui.css" rel="stylesheet" type="text/css" />
<link href="/mag/module/easy-ui/themes/icon.css" rel="stylesheet" type="text/css" />
<link href="/mag/css/login.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/mag/module/easy-ui/jquery.min.js"></script>
<script type="text/javascript" src="/mag/module/easy-ui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/mag/module/easy-ui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/mag/js/manage.js"></script>
<script type="text/javascript" src="/mag/js/login.js"></script>

</head>
<body style="background:url(/mag/images/back.jpg)">
	<div id="loginWindow" class="easyui-window login_window" title="后台管理系统"
		collapsible="false" minimizable="false" maximizable="false" closable="false" resizable="false">
		<div>
			<form id="loginForm" method="post" novalidate>
				<div class="login_item">
					<label for="username">账号：</label>
					<input class="easyui-validatebox login_input" type="text" name="username" data-options="required:true" placeholder="请输入您的账号">
				</div>
				<div class="login_item">
					<label for="password">密码：</label>
					<input class="easyui-validatebox login_input" type="password" name="password" data-options="required:true" placeholder="请输入您的密码">
				</div>
				<div class="login_button">
					<a href="javascript:manage.login.submit();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">登录</a>
					<a href="javascript:manage.login.clear();" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">清空</a>
				</div>
			</form>
		</div>
	</div>
</body>
<script>
	$("#loginForm").form({
		url : "/Verwalter/login/check",
		success:function(data){
			var res = JSON.parse(data);
			if("y" === res.status){
				window.location.href = "/Verwalter";
				return;
			}
			if("n" === res.status){
				$.messager.alert("提示", res.message);
			}
		}
	});
</script>
</html>