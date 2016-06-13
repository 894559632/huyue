/**
 * 跟触屏版用户模块相关的命名空间
 */
base.user = {};
base.user.visit = {};
base.user.city = {};

// 清空用户收藏的方法
base.user.visit.clear = function() {
    $.ajax({
	url : "/touch/user/visited/clear",
	type : "post",
	success : function(res) {
	    if (0 === res.status) {
		window.location.reload();
	    } else {
		alert(res.message);
		if (-2 === res.status) {
		    window.location.href = "/touch/login";
		}
	    }
	}
    });
}

// 添加收货城市的方法
base.user.city.save = function() {
    var addressId = base.getE("addressId").value;
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
	    id : addressId,
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
    if (0 !== base.user.regist.smsTime) {
	setTimeout(function() {
	    base.user.regist.smsTime -= 1;
	    base.getE("getSms").innerHTML = "等待（" + base.user.regist.smsTime
		    + ")";
	    if (0 === base.user.regist.smsTime) {
		base.getE("getSms").innerHTML = "获取验证码";
		base.user.regist.smsTime = 60;
		base.user.regist.openSms();
	    } else {
		base.user.regist.reSmsTime();
	    }
	}, 1000);
    }
}

// 用户注册获取短信验证码的方法
base.user.regist.getSms = function() {
    base.user.regist.closeSms();
    if (60 === base.user.regist.smsTime) {
	// 验证手机号码
	var phone = base.getE("phone").value;
	if (!phone) {
	    alert("请输入正确的手机号码");
	    base.user.regist.openSms();
	    return;
	}

	var pattern = /^1\d{10}$/;
	if (!pattern.test(phone)) {
	    alert("请输入正确的手机号码");
	    base.user.regist.openSms();
	    return;
	}

	// 验证图形验证码
	var imgSms = base.getE("imgSms").value;
	if (!imgSms) {
	    alert("请输入正确的图形验证码");
	    base.user.regist.openSms();
	    return;
	}

	$.ajax({
	    url : "/code/check",
	    type : "post",
	    data : {
		data : imgSms
	    },
	    success : function(res) {
		if (0 !== res.status) {
		    alert("请输入正确的图形验证码");
		    base.user.regist.openSms();
		} else {
		    base.user.regist.sendSms(phone);
		}
	    }
	})
    }
}

// 发送短信验证码的方法
base.user.regist.sendSms = function(phone) {
    $.ajax({
	url : "/code/send",
	type : "post",
	data : {
	    mobile : phone
	},
	success : function(res) {
	    if (000000 === Number(res.statusCode)) {
		base.user.regist.reSmsTime();
	    } else {
		alert(res.statusMsg);
		base.user.regist.openSms();
	    }
	}
    })
}

// 关闭获取验证码的方法
base.user.regist.closeSms = function() {
    var smsBtn = base.getE("getSms");
    smsBtn.removeEventListener("click", base.user.regist.getSms, false);
}

// 开启获取验证码的方法
base.user.regist.openSms = function() {
    var smsBtn = base.getE("getSms");
    smsBtn.addEventListener("click", base.user.regist.getSms, false);
}

// 注册提交的方法
base.user.regist.submit = function() {
    // 获取参数
    var cityId = base.getE("cityId").value;
    var phone = base.getE("phone").value;
    var password = base.getE("password").value;
    var repassword = base.getE("repassword").value;
    var realName = base.getE("realName").value;
    var imgSms = base.getE("imgSms").value;
    var smsCode = base.getE("smsCode").value;

    // 验证城市
    if (0 === Number(cityId)) {
	alert("请选择您所在的城市");
	return res;
    }

    // 验证手机号码
    if (!phone) {
	alert("请输入手机号码");
	return;
    }
    var pattern = /^1\d{10}$/;
    if (!pattern.test(phone)) {
	alert("请输入正确的手机号码");
	base.user.regist.openSms();
	return;
    }

    // 验证密码
    if (!password) {
	alert("请输入密码");
	return;
    }
    if (password.length > 20 || password.length < 6) {
	alert("请输入长度大于6位小于20位的密码");
	return;
    }

    // 验证确认密码
    if (String(password) !== String(repassword)) {
	alert("两次输入的密码不一致");
	return;
    }

    // 验证真实姓名
    if (!realName) {
	alert("请输入您的真实姓名");
	return;
    }

    // 验证图形验证码
    if (!imgSms) {
	alert("请输入图形验证码");
	return;
    }

    if (!smsCode) {
	alert("请输入短信验证码");
	return;
    }

    $.ajax({
	url : "/touch/regist/confirm",
	type : "post",
	data : {
	    cityId : cityId,
	    phone : phone,
	    password : password,
	    realName : realName,
	    imgSms : imgSms,
	    smsCode : smsCode
	},
	success : function(res) {
	    if (0 !== res.status) {
		alert(res.message);
	    } else {
		window.location.href = "/touch/user";
	    }
	}
    });
}

// 创建一个命名空间，包含关于用户收藏的方法
base.user.collect = {};
// 删除某件收藏商品的方法
base.user.collect.deleteCollect = function(id) {
    if (id) {
	$.ajax({
	    url : "/touch/user/collect/delete",
	    type : "post",
	    data : {
		id : id
	    },
	    success : function(res) {
		if (0 === res.status) {
		    var container = base.getE("container" + id);
		    container.parentNode.removeChild(container);
		} else {
		    alert(res.message);
		}
	    }
	});
    }
}

// 创建一个命名空间，包含关于用户收货地址的方法
base.user.address = {}

// 设置指定收货地址为默认的方法
base.user.address.setDefault = function(id) {
    console.log(123);
    if (id) {
	$.ajax({
	    url : "/touch/user/address/default",
	    typr : "post",
	    data : {
		id : id
	    },
	    success : function(res) {
		if (0 !== res.status) {
		    alert(res.message);
		    if (-2 === res.status) {
			window.location.href = "/touch/login";
		    }
		} else {
		    window.location.reload();
		}
	    }
	})
    }
}

// 删除指定收货地址的方法
base.user.address.remove = function(id) {
    if (id) {
	$.ajax({
	    url : "/touch/user/address/remove",
	    type : "post",
	    data : {
		id : id
	    },
	    success : function(res) {
		if (0 === res.status) {
		    var box = base.getE("box" + id);
		    box.parentNode.removeChild(box);
		} else {
		    alert(res.message);
		}
	    }
	});
    }
}

// 修改指定收货地址的方法
base.user.address.edit = function(id) {
    if (id) {
	window.location.href = "/touch/user/add/address?id=" + id;
    }
}

// 创建一个命名空间，其与用户的意见反馈相关
base.user.advice = {};
base.user.advice.save = function() {
    var content = base.getE("content").value;
    var phone = base.getE("phone").value;

    if (!content) {
	alert("请输入您的意见");
	return;
    }

    var pattern = /^1\d{10}$/;
    if (!pattern.test(phone)) {
	alert("请输入正确的联系电话");
	return;
    }

    $.ajax({
	url : "/touch/user/advice/save",
	type : "post",
	data : {
	    content : content,
	    phone : phone
	},
	success : function(res) {
	    if (0 === res.status) {
		alert("您的意见已经提交，我们会尽快跟您联系");
		window.location.href = "/touch/user";
	    } else {
		alert(res.message);
	    }
	}
    })
}