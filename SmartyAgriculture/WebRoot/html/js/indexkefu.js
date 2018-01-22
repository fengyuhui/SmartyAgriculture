$(function(){
	var thisBox = $('.lanren');
	var defaultTop = thisBox.offset().top;
	var slide_min = $('.lanren .slide_min');
	var slide_box = $('.lanren .slide_box');
	var closed = $('.lanren .slide_box h2 img');
	slide_box.hide();
	slide_min.on('click',function(){
		$(this).hide();	
		slide_box.show();
	});
	closed.on('click',function(){
		slide_box.hide().hide();
		slide_min.show();
	});
	// 页面滚动的同时，悬浮框也跟着滚动
	$(window).on('scroll',function(){
		scro();
	});
	$(window).onload = scro();
	function scro(){
		var offsetTop = defaultTop + $(window).scrollTop()+'px';
		thisBox.animate({top:offsetTop},
		{	duration: 600,	//滑动速度
	     	queue: false    //此动画将不进入动画队列
	     });
	}
});
