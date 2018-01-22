function getProductDetail(id){
//	alert(id);
	jQuery.ajax({
		type:"post",
		url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsDetail",
		data:{
			goodsId:id,			
		},
		dataType:"json",
		success: function(data){
			var str=JSON.stringify(data);
            localStorage.setItem("goodsId",id);
			localStorage.setItem("productDetail",str);
            window.open("detail-buy.html");
		}
	})
}

//页面加载数据函数
function productDetailOnload(){
    navigationJudge();
	var productDetail=localStorage.getItem("productDetail");
	//重转换为对象
	productDetail=JSON.parse(productDetail);
	console.log(productDetail);
//将json内图片字段以逗号为标志，分开
	var str=productDetail.content.picture;
	var strs=new Array();
	strs=str.split(",");
//alert(productDetail.content.name);
//右侧商品详情加载
	var string='<div class="detail-hd"><h1>'
			+productDetail.content.name+'</h1></div>'
			+'<p class="newp">'+productDetail.content.title+'</p></div>'
			+'<div class="detail-bd"><dl class="detail-price"><dt class="dt-metatit">价格</dt><dd class="price-wrap"><div class="dd-price"><em class="pro-yen">￥</em>'
			+'<span class="pro-price">'+productDetail.content.price+'</span></div></dd></dt></dl>'
			+'<dl class="detail-num"><dt class="dt-metatit">规格</dt><dd id="pro-amount"><div class="dd-amount"><span class="amount-widget"><input type="text" checked="checked" oninput="onInput(event)" class="amount-input" value="'+1+'" maxlength="" title="请输入购买量" />'
			+'<span class="amount-unit">'+productDetail.content.unit+'</span></div></dd></dl>'
			+'<dl class="detail-addr"><dt class="dt-metatit">配送</dt><dd><div class="dt-postAge"><span class="delivery-from">至&nbsp;北京市</span>'
            +'<span class="addr-tri"><span class="addr-tri_1" role="button">海淀<i class="addr-icon">∨</i></span></span>'
            +'<div class="postAge-fee">'
            +'<p><span>快递：0.00</span>'
            +'</p></div></div></dd></dl>'
            +'<div class="dt-action"><div class="btn-sku">'
            +'<a href="javascript:void(0)" onclick="getOrderDetail()" id="" class="buy-now" role="button" title="点击此按钮，到下一步确认购买信息。">立即购买</a>'
            +'</div>'
            +'<div class="btn-sku"><a href="javascript:void(0)" onclick="addCart()" id="A1" class="cart-addTo" role="button" title="点击此按钮，到下一步确认购买信息。">加入购物袋</a></div>'
            +'</div></div></div>';
//图片加载
    var stringImg='<a href="javascript:void(0)">'
    		+'<span class="promo-icon">'
//  		+'<img  src="'+productDetail.content.picture+'"/>'
			+'<img src="" />'
    		+'</span>'
    		+'<span class="img-warp" ><img class="item-show" id="show-big"alt="Name-one" src="'+strs[0]+'"/>'
    		+'</span></a>';
    var themesIcon=null;
    switch(productDetail.content.saleStatus){
    	case 0:
    		themesIcon="images/themes/promotion/remaishangpin@3x.png";//热卖商品图标
    		break;
    	case 2:
    		themesIcon="images/themes/promotion/tehuishangpin@3x.png";//限时抢购图标
    		break;
    	default:
    		themesIcon=null;
    }
	jQuery(".detail-wrap").append(string);
	jQuery(".img-booth").append(stringImg);
    jQuery('span.promo-icon > img').attr("src",themesIcon);
    loadImgList(str);
//    getAllComment(productDetail.content.id,1);
}
function loadImgList(str){
    console.log(str);
    var imgArr=new Array();
    imgArr=str.split(',');
    console.log(imgArr);
    var ulImg=document.getElementById("imgList");//最多显示4张图片
    console.log(ulImg);
    if(imgArr.length<5){
        for(var i=0;i<imgArr.length;i++){
            var li=document.createElement('li');
            li.innerHTML='<a href="javascript:void(0)"><img class="zoomlist" src="'+imgArr[i]+'" onclick="showMe(src)" alt=""></a>'
            ulImg.appendChild(li);
        }
    }else{
        for(var i=0;i<5;i++){
            var li=document.createElement('li');
            li.innerHTML='<a href="javascript:void(0)"><img src="'+imgArr[i]+'" onclick="showMe(src)" alt=""></a>'
            ulImg.appendChild(li);
        }
    }
}
