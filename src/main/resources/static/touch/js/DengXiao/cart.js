/**
 * 创建一个命名空间，其与购物车相关
 */
base.cart = {};
base.cart.changeQuantity = function(id, operate) {
	if (id) {
		// 获取数量输入框DOM
		var quantityE = base.getE("quantity" + id);
		var number = quantityE.value;
		if (0 === operate) {
			if (Number(number) === 1) {
				return;
			}
		}

		$.ajax({
			url : "/touch/cart/change",
			type : "post",
			data : {
				id : id,
				operate : operate
			},
			success : function(res) {
				if (0 !== res.status) {
					alert(res.message);
					if (-2 === res.status) {
						window.location.href = "/touch/login";
					}
				} else {
					if (0 === operate) {
						quantityE.value = Number(number) - 1;
					} else {
						quantityE.value = Number(number) + 1;
					}
					base.cart.countTotal();
				}
			}
		})
	}
}

// 定义一个数据变量，用于表示被选中的购物车项的id
base.cart.selected = [];

// 计算已选中商品的总金额的方法
base.cart.countTotal = function() {
	var selected = base.cart.selected;
	if (selected) {
		var total = 0.00;
		for (var i = 0; i < selected.length; i++) {
			// 获取已选商品的id
			var id = selected[i];
			var quantity = base.getE("quantity" + id).value;
			var unit = base.getE("unit" + id).value;

			// 计算总价
			total += (quantity * unit);
		}
		base.getE("totalPrice").innerHTML = total;
		base.getE("selectedNumber").innerHTML = selected.length;
	}
}

// 选中商品的方法
base.cart.selectCart = function(cartId) {
	if (cartId) {
		var selected = base.cart.selected;
		var new_selected = [];
		if (selected) {
			var isAdd = true;
			for (var i = 0; i < selected.length; i++) {
				var id = selected[i];
				if (id && "" !== id && id === cartId) {
					isAdd = false;
				} else {
					new_selected.push(id);
				}
			}

			if (isAdd) {
				new_selected.push(cartId);
			}
		}
		base.cart.selected = new_selected;
	}
	base.cart.countTotal();
	base.cart.isAllSelected();
}

// 全选的方法
base.cart.allSelect = function() {
	var labels = base.getC("cartLabel");
	var isAllSelected = true;
	base.cart.selected = [];
	for (var i = 0; i < labels.length; i++) {
		var label = labels[i];
		var className = label.className;
		if (!(className.indexOf("label_active") > 0)) {
			isAllSelected = false;
		}
	}

	if (isAllSelected) {
		for (var i = 0; i < labels.length; i++) {
			labels[i].className = "cartLabel";
		}
	} else {
		for (var i = 0; i < labels.length; i++) {
			labels[i].className = "cartLabel label_active";
			base.cart.selected.push(labels[i].id);
		}
	}

	base.cart.countTotal();
}

// 判断是否是全选的方法
base.cart.isAllSelected = function() {
	var isAll = true;
	var selected = base.cart.selected;
	var labels = base.getC("cartLabel");
	for (var i = 0; i < labels.length; i++) {
		var label = labels[i];
		if (!base.contains(selected, label.id)) {
			isAll = false;
			break;
		}
	}

	if (isAll) {
		base.getE("all").className = "font_active";
	} else {
		base.getE("all").className = "";
	}
}