function addCart(){
    var token =localStorage.getItem("token");
    var id=localStorage.getItem("goodsId");
    var nums=localStorage.getItem("num");
    if(token==null){
        bootbox.alert({
            message:"抱歉,请先登录",
            size:'small',
            callback:function(){
                window.location.href="login.html";
            }
        });
    }else{
        $.ajax({
            type:"post",
            url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/addGoodsCart",
            data:{
                token:token,
                goodsId:id,
                num:nums
            },
            dataType:"json",
            success:function(){
                bootbox.setLocale("zh_CN");
                bootbox.alert({
                    message:"添加购物车成功！",
                    size:'small',
                    callback:function(){
                        window.location.href="shoppingCart.html";
                    }
                });
//                window.open("shoppingCart.html");
            }
        });
    }
}
