/*-------------------------
    created by zhangqiang
    at 2016.10.13
------------------------- */
bootbox.setDefaults("locale", "zh_CN"); //设置bootbox弹出框显示中文格式信息

function getCollectionProduct(token) {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getCollectGoodsList",
        dataType: "json",
        data: {
            "token": token,
        },
        success: function(data) {
            console.log(data);
            setHtmlCollectionProduct(data);
        }
    });
}

function setHtmlCollectionProduct(data) {
    cancelDeleteManage();
    jQuery("#contentDetailInfo").html("");
    var listdata = data.content.collectList;
    console.log(listdata);
    var dataArrayLength = data.content.collectList.length;
    if (dataArrayLength == 0) {
        jQuery("#collection-table").hide();
        jQuery("#contentDetailInfo_div").hide();
        jQuery(".empty-cart").show();
    }
    console.log("收藏商品的个数:" + dataArrayLength);
    jQuery(".allnum").text(dataArrayLength);
    jQuery(".collectionNum").text(dataArrayLength);
    var Data = new Array();
    var dataLen = 0;
    for (var i = 0; i < dataArrayLength; i++) {
        Data[dataLen++] = data['content']['collectList'][i];
    }
    console.log("收藏商品个数:" + Data.length);
    var len = Data.length;
    var str = "";
    jQuery.each(Data, function(len, val) {
        str += '<li onclick="selected()"><div id="imgDiv"><img src=" ' + Data[len].picture + ' " class="productImg"/></div>';
        str += '<div id="am-checkbox"><input class = "check" type = "checkbox" name = "subCheck"  value="' + val["goodsId"] + '"/></div><p id="productInfo" title="' + Data[len].name + '">' + Data[len].name.substr(0, 10) + '</p>';
        str += '<p id="productPacket">500 g/' + Data[len].unit + '</p><p id="price">¥&nbsp;' + Data[len].price + '</p></li>';
    });
    jQuery("#contentDetailInfo").html(str);
}

jQuery(document).on("click", ".deleteConfirm", function(e) {
    console.log("进入删除时，提醒是不是要确定删除的js方法中");
    bootbox.confirm({
        size: 'middle', //尺寸大小   middle  large small
        message: "你确定要删除已选中的收藏商品吗？",
        callback: function(result) {
            /* 回调函数 */
            if (result) {
                var data;
                var goodsId = getAllchecked();
                console.log("批量删除收藏商品列表:" + goodsId);
                var User_token = "";
                User_token = localStorage.getItem("token");
                console.log(User_token);
                jQuery.ajax({
                    type: "POST",
                    url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/deleteCollectGoodsBatch",
                    dataType: "json",
                    data: {
                        "token": User_token,
                        "collectIdList": goodsId
                    },
                    success: function() {
                        console.log(User_token);
                        getCollectionProduct(User_token);
                    }
                });
            }
        }
    });
});

function tishiInfo() {
    console.log("进入未登录提示信息");
    bootbox.alert({
        size: 'middle', //尺寸大小   middle  large
        message: "您还未登录,去首页看看吧",
        callback: function(result) {
            if (result) {
                window.location.href = "../index.html";
            }
        }
    });
}
