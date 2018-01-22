// 页面进入的时候，检查商品是否已收藏，收藏就显示”已收藏“的图标，否则就显示”未收藏“的图标！

// (function getAllCollectProduct(){
//     var token_User = localStorage.getItem("token");
//     var goodsId = localStorage.getItem("goodsId");
//     var tap = 0;
//     $.ajax({
//         type:"post",
//         url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getCollectGoodsList",
//         data:{
//             token:token_User
//         },
//         dataType:"json",
//         success:function(data){
//             console.log("收藏商品列表：");
//             console.log(data.content.collectList);
//             data.content.collectList.forEach(function(value,index,arr){
//                 if(arr[value].goodsId == goodsId ){
//                     tap = 1;
//                     $("#collect").attr("src") = "images/collect.svg";
//                     break;
//                 }
//             });
//             if(tap == 0){
//                 return 0 ;
//             }
//         }
//     });
// });



function favorite_photo_click() {
    var token_User = localStorage.getItem("token");
    console.log("用户token");
    console.log(token_User);
    var goodsId = localStorage.getItem("goodsId");
    console.log(goodsId);
    if (token_User == null) {
        alert("请登录查看");
        return 0;
    }
    if ($("#collect").attr("src") == "images/noncollect.svg") {
        flag = 1;
    } else {
        flag = 0;
    }
    if (flag) {
        $.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/addCollectGoods",
            dataType: "json",
            data: {
                "token": token_User,
                "goodsId": goodsId
            },
            success: function() {
                $("#collect").attr("src", "images/collect.svg");
                setTimeout($(bootbox.alert({
                    message: "添加收藏商品成功！",
                    size: 'small'
                })).hide(),1000);
                
            },
            error: function() {
                // alert("添加收藏商品失败");
                bootbox.alert({
                    message: "添加收藏商品失败！",
                    size: 'small'
                });
            }
        });

    } else {
        $.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/deleteCollectGoods",
            dataType: "json",
            data: {
                "token": token_User,
                "goodsId": goodsId
            },
            success: function() {
                $("#collect").attr("src", "images/noncollect.svg");
                setTimeout($(bootbox.alert({
                    message: "删除收藏商品成功！",
                    size: 'small'
                })).hide(),1000);
                
            },
            error: function() {
                // alert("删除收藏商品失败");
                setTimeout($(bootbox.alert({
                    message: "删除收藏商品失败！",
                    size: 'small'
                })).hide(),1000);
            }
        });
    }

}


// bootbox.setDefaults("locale", "zh_CN"); //设置bootbox弹出框显示中文格式信息
