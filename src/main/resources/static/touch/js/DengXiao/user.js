/**
 * 跟触屏版用户模块相关的命名空间
 */
base.user = {};
base.user.visit = {};
base.user.city = {};
// 清空用户收藏的方法
// base.user.visit.clear = function() {
// $.ajax({
// url : "/touch/user/"
// type : "post"
// })
// }

// 添加收货城市的方法
base.user.city.save = function() {
	var name = base.getE("receive_name").value;
	var phone = base.getE("receive_phone").value;
	var cityId = base.getE("receive_city_id").value;
	var detail = base.getE("receive_detail").value;

	// 验证信息是否完善
	if (!name) {
		alert("请输入收货人的姓名");
		return;
	}

	// 验证收货人的电话
	if (!name) {
		alert("请输入收货人的电话");
		return;
	} else {
		var pattern = /^1\d{10}$/;
		if (!pattern.test(phone)) {
			alert("请输入正确的手机号码");
			return;
		}
	}

	// 验证是否选择城市
	if (!(cityId && 0 !== Number(cityId))) {
		alert("请选择收货地区");
		return;
	}

	// 验证是否输入详细地址
	if (!detail) {
		alert("请输入收货人详细收货地址");
		return;
	}

	$.ajax({
		url : "/touch/user/address/save",
		type : "post",
		data : {
			name : name,
			phone : phone,
			cityId : cityId,
			detail : detail
		},
		success : function(res) {
			if (0 !== res.status) {
				alert(res.message);
				if (-2 === res.status) {
					window.location.herf = "/touch/login";
				}
			} else {
				window.location.href = "/touch/user/address";
			}
		}
	});
}

// 与用户注册相关的命名空间
base.user.regist = {};

// 重新获取验证码的时间
base.user.regist.smsTime = 60;

// 获取短信验证码倒计时的方法
base.user.regist.reSmsTime = function() {
	if (0 !== base.user.regist.reSmsTime) {
		setTimeout(function() {
			base.user.regist.smsTime -= 1;
			base.getE("getSms").innerHTML = "等待（" + base.user.regist.smsTime
					+ ")";
			console.log(0 === base.user.regist.smsTime);
			if (0 === base.user.regist.smsTime) {
				base.getE("getSms").innerHTML = "获取验证码";
				base.user.regist.smsTime = 60;
			} else {
				base.user.regist.reSmsTime();
			}
		}, 1000);
	}
}

// 用户注册获取短信验证码的方法
base.user.regist.getSms = function() {
	if (60 === base.user.regist.smsTime) {
		var imgSms = base.getE("imgSms").value;
		if (!imgSms) {
			alert("请输入正确的图形验证码");
			return res;
		}

		console.log(imgSms);

		$.ajax({
			url : "/code/check",
			type : "post",
			data : {
				data : imgSms
			},
			success : function(res) {
				if (0 !== res.status) {
					alert("请输入正确的图形验证码");
				} else {
					base.user.regist.reSmsTime();
				}
			}
		})
	}
}