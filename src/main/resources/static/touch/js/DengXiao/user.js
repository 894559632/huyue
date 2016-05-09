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
			if(0 !== res.status){
				alert(res.message);
				if(-2 === res.status){
					window.location.herf = "/touch/login";
				}
			}else{
				window.location.href = "/touch/user/address";
			}
		}
	});

}