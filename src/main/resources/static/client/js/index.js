function chooseMoreDown(){
	var _arr = $(".choose_box menu");
	var _length = _arr.length;
	if(_length > 4){
		var _str = "<a href='javascript:chooseMoreUp();'>����ѡ��<img src='images/content/arrow01.png' /></a>";
		$(".choose_more").html(_str);
		var _time = 200;
		for(var i=4;i<_length;i++){
			_time += 100;
			_arr.eq(i).slideDown(_time);
			}
	}
}

function chooseMoreUp(){
	var _arr = $(".choose_box menu");
	var _length = _arr.length;
	if(_length > 4){
		var _str = "<a href='javascript:chooseMoreDown();'>����������ѡ��<img src='images/content/arrow01.png' /></a>";
		$(".choose_more").html(_str);
		var _time = 200;
		for(var i=4;i<_length;i++){
			_time += 100;
			_arr.eq(i).slideUp(_time);
			}
	}
}
