/**
 * 创建一个命名空间，其与商品相关
 */
base.goods = {};
base.goods.collect = function(goodsId) {
    if (goodsId) {
	var collect_no = base.getE("collect_no").style.display;
	var collect_yes = base.getE("collect_yes").style.display;

	$.ajax({
	    url : "/touch/goods/collect",
	    type : "post",
	    data : {
		goodsId : goodsId
	    },
	    success : function(res) {
		if (0 !== res.status) {
		    alert(res.message);
		    if (-2 === res.status) {
			window.location.href = "/touch/login";
		    }
		} else {
		    if (collect_no === "block") {
			base.getE("collect_no").style.display = "none";
		    } else if (collect_no === "none") {
			base.getE("collect_no").style.display = "block";
		    }

		    if (collect_yes === "block") {
			base.getE("collect_yes").style.display = "none";
		    } else if (collect_yes === "none") {
			base.getE("collect_yes").style.display = "block";
		    }
		}
	    }
	});
    }
}

// 将商品加入购物车的方法
base.goods.addCart = function(goodsId) {
    if (goodsId && !isNaN(goodsId)) {
	$.ajax({
	    url : "/touch/goods/add/cart",
	    type : "post",
	    data : {
		goodsId : goodsId
	    },
	    success : function(res) {
		if (0 !== res.status) {
		    alert(res.message)
		    if (-2 === res.status) {
			window.location.href = "/touch/login";
		    }
		} else {
		    alert("已添加到购物车");
		}
	    }
	});
    }
}

// 立即购买的方法
base.goods.buyNow = function(goodsId) {
    if (goodsId && !isNaN(goodsId)) {
	$.ajax({
	    url : "/touch/goods/buynow",
	    type : "post",
	    data : {
		goodsId : goodsId
	    },
	    success : function(res) {
		if (0 !== res.status) {
		    alert(res.message)
		    if (-2 === res.status) {
			window.location.href = "/touch/login";
		    }
		} else {
		    window.location.href = "/touch/pay?orderId=" + res.orderId;
		}
	    }
	});
    }
}
