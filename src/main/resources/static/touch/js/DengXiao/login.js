/**
 * 跟触屏版登录相关的命名空间
 * 
 * @author DengXiao
 */
base.login = {
	// 验证用户名和密码是否合法的方法
	baseCheck : function(username, password) {
		var pattern = /^1\d{10}$/;
		if (username) {
			// 验证用户名是否合法
			if (!pattern.test(username)) {
				alert("您输入的手机号不正确");
				return false;
			}
		} else {
			// 验证用户名是否为空
			alert("请输入您的手机号码");
			return false;
		}

		if (password) {
			// 验证密码的长度
			if (password.length < 6) {
				alert("密码的最小长度为6");
				return false;
			}
			if (password.length > 20) {
				alert("密码的最大长度为20");
				return false;
			}
		} else {
			// 验证密码是否为空
			alert("请输入您的密码");
			return false;
		}
		return true;
	},
	// 验证用户名和密码是否正确的方法
	check : function(username, password) {
		$.ajax({
			url : "/touch/login/confirm",
			type : "post",
			timeout : 30000,
			data : {
				username : username,
				password : password
			},
			error : function() {
				alert("网络错误，请稍后重试");
			},
			success : function(result) {
				if (0 === result.status) {
					window.location.href = "/touch/user"
				} else {
					alert(result.message);
				}
			}
		});
	},
	// 提交表单的方法
	loginSubmit : function() {
		// 获取用户名和密码
		var username = base.getE("username").value;
		var password = base.getE("password").value;

		// 基础验证
		if (!this.baseCheck(username, password)) {
			return false;
		}

		// ajax验证账号密码是否匹配
		this.check(username, password);

		return false;
	}
}