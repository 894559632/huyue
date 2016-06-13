//common js
$(function() {
    pageSize();// 页面计算 rem
    html_height();// 页面高度计算
});

// math for page width
function pageSize() {
    $(document).ready(function() {
	setRootSize();
    });
    function setRootSize() {
	var deviceWidth = document.documentElement.clientWidth;
	if (deviceWidth > 640) {
	    deviceWidth = 640;
	}
	document.documentElement.style.fontSize = deviceWidth / 6.4 + 'px';
    }
    setRootSize();
    window.addEventListener('resize', function() {
	setRootSize();
	html_height();// 页面高度计算
    }, false);
};
// math for html height
function html_height() {
    var oHtml = document.getElementsByTagName('html')[0];
    var win_hi = document.documentElement.clientHeight;
    var doc_hi = document.documentElement.offsetHeight;
    // console.log(doc_hi);
    // console.log(win_hi);
    if (doc_hi >= win_hi) {
	oHtml.style.height = doc_hi + 'px';
    } else {
	oHtml.style.height = win_hi + 'px';
    }
    ;
}

// ////////////////////////////////////////////
function list_choice() {
    var aBtn = $('.choice_goods dt span');
    var aBox = $('.choice_goods dd div');
    var oOut = $('.list_select .list_box');
    var oBox = $('.choice_goods');
    $.each(oOut, function(i) {
	oOut.eq(i).click(function() {
	    oOut.eq(i).find('.guide').toggleClass('up');
	    if (i == 0) {
		oBox.slideToggle(400);
	    }
	    ;
	});
    });
    $.each(aBtn, function(i) {
	aBtn.eq(i).click(function() {
	    aBtn.removeClass('active_span');
	    $(this).addClass('active_span');
	    aBox.hide();
	    aBox.eq(i).show();
	    return false;
	});
    });
};

// 转盘 工具写法
function Turntable() {
    this.oBtn = document.getElementById("guide");
    this.oOUt = document.getElementById("turn_out");
    this.oDeg = 0;
    this.timer = null;
    this.onOff = false;
    this.setting = { // default data
	tableData : [ '一等奖', '2等奖', '3等奖', '4等奖', '5等奖', '6等奖', '7等奖', '8等奖',
		'9等奖', '没有奖' ],
	loop : '8',
	rotateTime : '4'
    };
};
Turntable.prototype.init = function(target, yes, opt) {
    var that = this;
    extend(this.setting, opt);
    var tableDataNum = this.setting.tableData.length; // 10
    tableDefaltDeg = (360 / tableDataNum); // 36
    tableDataDeg = [];
    for (var i = 0; i < tableDataNum; i++) {
	tableDataDeg.push(i * tableDefaltDeg + tableDefaltDeg / 2);
    }
    ;
    this.oBtn.onclick = function() {
	var count = document.getElementById("count").innerHTML;
	if (count > 0) {
	    document.getElementById("count").innerHTML = count - 1;
	} else {
	    return;
	}
	$.ajax({
	    url : "/touch/lottery/check",
	    type : "POST",
	    success : function(res) {
		if (0 !== Number(res.status)) {
		    alert(res.message);
		    if (-2 === res.status) {
			window.location.href = "/touch/login";
		    }
		    return;
		}
	    }
	});
	that.oDeg = Math.floor(tableDataDeg[target - 1] + Math.random()
		* tableDefaltDeg + that.setting.loop * 360);
	this.style.webkitTransition = that.setting.rotateTime + 's';
	this.style.webkitTransform = 'rotate(' + that.oDeg + 'deg)';
	that.timer = setTimeout(function() {
	    for (var i = 0; i < yes.length; i++) {
		if (target == yes[i]) {
		    that.onOff = true;
		}
	    }
	    if (that.onOff) {
		that.oOUt.style.display = 'block';
		that.oOUt.children[0].style.display = 'block';
		that.onOff = false;
	    } else {
		that.oOUt.style.display = 'block';
		that.oOUt.children[1].style.display = 'block';
		// that.oOUt.children[1].style.opacity
		// = 1;
	    }
	    ;
	}, that.setting.rotateTime * 1000);
    }
};

// set extend function
function extend(a, b) {
    for ( var attr in b) {
	a[attr] = b[attr];
    }
    ;
};
// 拖拽删除
function drge(obj) {
    var oBox = $(obj);
    $.each(oBox, function(i) {
	drgeGo(oBox.eq(i));
    });
    function drgeGo(obj) {
	var dele = obj.find('.dele');
	var onOff = true;
	var disX = 0;
	myX = 0;
	firstX = 0;
	lastX = 0;
	obj.on('touchstart', function(ev) {
	    obj.css({
		WebkitTransition : '0.6s'
	    });
	    firstX = ev.originalEvent.changedTouches[0].clientX;
	    disX = ev.originalEvent.changedTouches[0].clientX
		    - obj.position().left;
	    $(document).on('touchmove', function(ev) {
		myX = ev.originalEvent.changedTouches[0].clientX - disX;
		if (myX > 0) {
		    myX = 0;
		} else if (myX < -dele.width()) {
		    myX = -dele.width();
		}
		;
		obj.css({
		    left : myX
		});
	    });
	    $(document).on('touchend', function(ev) {
		lastX = ev.originalEvent.changedTouches[0].clientX;
		$(document).unbind('touchend');
		$(document).unbind('touchmove');
		if ((firstX - lastX) / dele.width() > 0.5) {
		    myX = -dele.width();
		} else if ((firstX - lastX) / dele.width() < 0.5) {
		    myX = 0;
		}
		;
		obj.css({
		    WebkitTransition : '0.6s',
		    left : myX
		});
	    });
	});
    }
    ;
};
// ///////////////////////////////////////星星设置默认

function set_star(obj, num) {
    var oBox = $(obj).get()[0];
    var aStar = oBox.children;
    var wi = aStar[0].offsetWidth;
    for (var i = 0; i < aStar.length; i++) {
	aStar[i].children[1].style.left = -wi + 'px';
    }
    ;
    for (var i = 0; i < aStar.length; i++) {
	if (i == num) {
	    break;
	}
	;
	aStar[i].children[1].style.left = '0px';
    }
    ;
};
// ///////////////收藏效果
function collectFly() {
    var oStar = $('#yes').get()[0];
    var oBtn = $('#yes_star').get()[0];
    var winWidth = document.documentElement.clientWidth;
    var winHeight = document.documentElement.clientHeight;
    var ranNum = Math.pow(-1, Math.ceil(Math.random() * 100));
    var onOff = true;

    if (ranNum < 0) {
	oStar.style.left = winWidth + 'px';
    } else {
	oStar.style.left = -winWidth + 'px';
    }
    ;

    oBtn.onclick = function() {
	if (onOff) {
	    oStar.style.WebkitTransition = '0.4s ease';
	    oStar.style.bottom = '0rem';
	    oStar.style.left = '0rem';
	} else {
	    if (ranNum < 0) {
		oStar.style.left = -winWidth + 'px';
	    } else {
		oStar.style.left = winWidth + 'px';
	    }
	    ;
	}
	;
	onOff = !onOff;
    };
};
// //单独切换class
function choice_pay(obj, sty) {
    var oBox = $(obj);
    oBox.click(function() {
	$(this).toggleClass(sty);
    });
};
// 多个切换class
function change_all(obj, sty) {
    var oBox = $(obj);
    oBox.click(function() {
	oBox.removeClass(sty);
	$(this).toggleClass(sty);
    });
};
// //购物车 全选
function choice_all() {
    var oBox = $('.pay_now .all_choice');
    oBox.click(function() {
	oBox.find('font').toggleClass('font_active');
    });
};

