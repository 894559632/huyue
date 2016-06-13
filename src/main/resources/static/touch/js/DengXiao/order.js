base.order = {};
base.order.address = {};
base.order.address.save = function() {
    var addressId = base.getE("addressId").value;
    var name = base.getE("receive_name").value;
    var phone = base.getE("receive_phone").value;
    var cityId = base.getE("receive_city_id").value;
    var detail = base.getE("receive_detail").value;
    var orderId = base.getE("orderId").value;

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
	url : "/touch/order/address/save",
	type : "post",
	data : {
	    id : addressId,
	    name : name,
	    phone : phone,
	    cityId : cityId,
	    detail : detail,
	    orderId : orderId
	},
	success : function(res) {
	    if (0 !== res.status) {
		alert(res.message);
		if (-2 === res.status) {
		    window.location.herf = "/touch/login";
		}
	    } else {
		window.location.href = "/touch/pay?orderId=" + orderId;
	    }
	}
    });
}

base.order.address.selected = function(id) {
    if (id) {
	var orderId = base.getE("orderId").value;
	$.ajax({
	    url : "/touch/order/address/selected",
	    type : "post",
	    data : {
		orderId : orderId,
		id : id
	    },
	    success : function(res) {
		if (0 !== res.status) {
		    alert(res.message);
		    if (-2 == res.status) {
			window.location.href = "/touch/login";
		    }
		} else {
		    window.location.href = "/touch/pay?orderId=" + orderId;
		}
	    }
	})
    }
}