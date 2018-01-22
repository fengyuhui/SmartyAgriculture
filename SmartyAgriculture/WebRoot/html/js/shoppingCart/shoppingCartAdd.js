
/*---------------------------------------------------
  					添加购物车方法
  --------------------------------------------------*/
//function addToShoppingCart(id,token,num){
//	jQuery.ajax({
//		type:"post",
//		url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/addGoodsCart",
//		data:{
//			"token":token,
//			"goodsId":id,
//			"num":num,		
//		},
//		dataType:"json",
//		success:function(){
//			$('.success-shoppingCart').fadeIn("fast");
//		}
//	});
//}
/*---------------------------------------------------
  					添加购物车动作
  --------------------------------------------------*/
function addToCartAction(){
	var buyNum=sessionStorage.getItem("num");
	var proId=sessionStorage.getItem("goodsId");
	var token =sessionStorage.getItem("token");
	alert(buyNum+"&nbsp;"+proId+"&nbsp;"+token);
//	addToCartAction(goodsId,token,buyNum);
	jQuery.ajax({
		type:"post",
		url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/addGoodsCart",
		data:{
			token:token,
			goodsId:proId,
			num:buyNum,		
		},
		dataType:"json",
		success:function(){
			$('.success-shoppingCart').fadeIn("fast");
		}
	});
}
/*------------------------------------------------------
 * -------------商品购买量----添加+减少按钮--------------
 ------------------------------------------------------*/
$(document).ready(function(){
  	$(".action-minus").click(function(){
		if($('.purchase-number-font').html()==1){
//			$('.purchase-number-font').html(1);
			return;
		}else{
			var num=$('.purchase-number-font').html();
			$('.purchase-number-font').html(num-1);//将改变后的数量储存起来！！！
		}		
	});	
	$(".action-plus").click(function(){
		var num =Number($('.purchase-number-font').html());
		$('.purchase-number-font').html(num+1);
		num=$('.purchase-number-font').text();
    	sessionStorage.setItem("num", num);//将改变后的数量储存起来！！！		
	});
	$(".close-btn").click(function(){
		$(".success-shoppingCart").fadeOut("fast");
	});
	$(".go-cart").click(function(){
		window.location.href="mine/shoppingCart.html";
	});
});
