//common js
$(function(){
	pageSize();//页面计算 rem
	html_height();//页面高度计算
});


//math for page width
function pageSize()
{
	$(document).ready(function(){
		setRootSize();
	});
	function setRootSize() {
		var deviceWidth = document.documentElement.clientWidth; 
		if(deviceWidth>640){deviceWidth = 640;}  
		document.documentElement.style.fontSize = deviceWidth / 6.4 + 'px';
	}
	setRootSize();
	window.addEventListener('resize', function () {
	    setRootSize();
	}, false);
};
//math for html height
function html_height(){
	var oHtml = document.getElementsByTagName('html')[0];
	var win_hi =document.documentElement.clientHeight;
	var doc_hi =document.documentElement.offsetHeight;
//	console.log(doc_hi);
//	console.log(win_hi);
	if(doc_hi>=win_hi){
		oHtml.style.height = doc_hi + 'px';
	}else{
		oHtml.style.height = win_hi + 'px';
	};
}

//////////////////////////////////////////////
function list_choice()
{
    var aBtn = $('.choice_goods dt span');
    var aBox = $('.choice_goods dd div');
    var oOut = $('.list_select .list_box');
    var oBox = $('.choice_goods');
    $.each(oOut, function(i) {
    	oOut.eq(i).click(function(){
    		oOut.eq(i).find('.guide').toggleClass('up');
	    	if(i == 0){
	    		oBox.slideToggle(400);
	    	};
    	});
    });
    $.each(aBtn, function(i) {
    	aBtn.eq(i).click(function(){
    		aBtn.removeClass('active_span');
    		$(this).addClass('active_span');
    		aBox.hide();
    		aBox.eq(i).show();
    		return false;
    	});
    });
};




















