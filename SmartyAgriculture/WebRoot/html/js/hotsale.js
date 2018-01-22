/*
 * 根据不同的id（salesStatus），判断执行不同的专题商品（热卖、限时抢购等）
 */
function getHotProduct(id) {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        data: {
          	saleStatus: id,
            pageNum: 1,
            pageSize: 12,
        },
        dataType: "json",
        success: function (data) {
            var str = JSON.stringify(data['content']['goodsList']);
            localStorage.setItem("productList", str);
            if(id==0){
				window.open("hotsale.html");
			}else if(id==2){
				window.open("promotions.html");
			}else if(id==1){
                window.open("specialOffer.html");
            }else if(id==3){
                window.open("newSale.html");
            }
        },
    });
}

//页面打开时加载的函数
function HotProductOnload(){
    navigationJudge();//navigation
	var productList = localStorage.getItem("productList");
    //重新转换为对象
    productList = JSON.parse(productList);
    jQuery.each(productList, function (i, val) {
		var proId=val['id'];
    	var string ='<div class="isotope-item" id="">'
    		+'<div class="product-wrap">'
    		+'<div class="productImg-wrap">'
    		+'<a href="#" class="productImg">'
    		+'<img src="'
    		+val['picture']+'"'+'onclick="getProductDetail('+proId+')" '+'alt=" '+val['name']+'"/>'
    		+'</a>'
    		+'</div>'
    		+'<p class="productName">'
    		+val['name']+'</p>'
    		+'<p class="product-info">'
    		+val['title']+'</p>'
    		+'<span class="productStatus"><em>'+val['buyNum']+'</em>'
    		+'人已购买'
    		+'</span>'
    		+'<div class="product-price"><em title="'+val['price']+'">'
    		+'<b>￥</b>'+val['price']+'</em>'
    		+'</div></div></div>';
        jQuery(".search-container").append(string);
    });
}
