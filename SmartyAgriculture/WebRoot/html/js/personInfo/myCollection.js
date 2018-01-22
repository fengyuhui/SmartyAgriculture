/****************************
 
 * created by zhangqiang at 2016/10/9

 ***************************/


/*-----------------------
    title JS start
-------------------------*/



/**
 * 获取用户信息
 */
function setUserInfo() {
    getPersonInfo();
    var token = getToken();
    var data = getUserInfo(token);
    jQuery("#edit_username").val(data['content']['name']);
    jQuery(".portrait").attr("src", data['content']['portrait']);
}


/**
 * 返回token
 */
function getToken() {
    var token = localStorage.getItem("token");
    //如果没有获取到token，则跳转到登陆界面
    console.log(token);
    if (jQuery.trim(token) != " ") {
        return token;
    } else {
        tishiInfo();
    }
}

/**
 * 获取个人信息
 */
function getUserInfo(token) {
    var dataInfo;
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
        data: {
            token: token,
        },
        dataType: "json",
        success: function (data) {
            if (data['result'] == 1) {
                dataInfo = data;
            }
        }
    });
    return dataInfo;
}

function getPersonInfo() {
    var token = getToken();
    console.log(token);
    var data = getUserInfo(token);
    jQuery("#username").text(data['content']['name']);
    jQuery("#score").text(data['content']['phone']);
    jQuery("#person_portrait").attr("src", data['content']['portrait']);
    //设置header中的用户名
    jQuery("#header_username").text(data['content']['name']);
}


/**
 * 退出
 */
function signOut() {
    //token
    var token = getToken();
    jQuery.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
        data: {
            token: token,
        },
        dataType: "JSON",
        success: function (data) {
            if (data.result == 1) {
                localStorage.clear();
                window.location.href = "../index.html";
            }
        }
    });
}

/**
 * 获取搜索信息
 */
// jQuery().ready(function setSearchinfo() {
//     var search_info = jQuery(".search-field").val();
//     if (search_info == "搜索") {
//         return 0;
//     }
//     localStorage.setItem("search_info", search_info);
//     // alert(search_info);
// });

function setSearchinfo(){
    var search_info = jQuery(".search-field").val();
    if(search_info=="搜索"){
        return 0;
    }
    localStorage.setItem("search_info",search_info);
}




/**
 * 回车键事件 
 * 绑定键盘按下事件 
 */
$(document).keypress(function (e) {
    // 回车键事件  
    if (e.which == 13) {
        jQuery(".search-btn").click();
    }
});



function createGetLikeList(data) {
    var likeListDataContent = data.content;
    console.log("猜你喜欢商品信息:");
    console.log("likeListDataContent");
    var likeListDataContent_length = data.content.length;
    console.log("猜你喜欢商品的个数:" + likeListDataContent_length);
    likeData = new Array();
    var likedataLen = 0;
    for (var i = 0; i < likeListDataContent_length; i++) {
        likeData[likedataLen++] = data['content'][i];
    }
    console.log("猜你喜欢商品的个数:" + likeData.length);
    var len = likeData.length;
    jQuery.each(likeData, function (len, val) {
        var str = "";
        if (len < 6) {
            str += '<li  onclick="getProductDetail('+val["id"]+')" class=" likelist' + len + '"><div id="imgDiv"><img src=" ' + likeData[len].picture + ' " class="productImg"/></div>';
            str += '<div><input class = "check" type = "checkbox" name="likeListSubCheck"  value="' + val["goodsId"] + '"/></div><p id="productInfo" title="' + likeData[len].title + '">' + likeData[len].name.substr(0, 10) + '</p>';
            str += '<p id="productPacket">500 g/' + likeData[len].unit + '&nbsp;&nbsp;<span id="price_command">¥&nbsp;' + likeData[len].price + '</span></p>';
            str += '<p id="infoCommand">根据浏览过的”<span>' + likeData[len].typeParent + '</span>“推荐</p>';
            jQuery("#guesscontent").append(str);
        } else {
            return 0;
        }
    });
}


jQuery().ready(function () {
    var token = getToken();
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getLikeGoodsList",
        dataType: "json",
        data: {
            "token": token,
        },
        success: function (data) {
            console.log("猜你喜欢（ready方式加载)");
            console.log(data.content.collectList);
            createGetLikeList(data);
        },
    });
})





function deleteManage() {
    jQuery(".span-delete").show();
    jQuery(".span-allchecked").show();
    jQuery(".span-quxiaoguanli").show();
    jQuery(".span-guanli").hide();
    jQuery("#am-checkbox input").show();
    // jQuery("#contentDetailInfo li").unbind();

}

function cancelDeleteManage() {
    jQuery(".span-delete").hide();
    jQuery(".span-allchecked").hide();
    jQuery(".span-quxiaoguanli").hide();
    jQuery(".span-allNotChecked").hide();
    jQuery(".span-guanli").show();
    jQuery("#am-checkbox input").hide();
}

function allChecked() {
    jQuery(":checkbox").prop("checked", true);
    jQuery(".span-allchecked").hide();
    jQuery(".span-allNotChecked").show();
}

function allNotChecked() {
    jQuery(":checkbox").prop("checked", false);
    jQuery(".span-allchecked").show();
    jQuery(".span-allNotChecked").hide();
}


function getProductDetail(id) {
    
    $.ajax({
        type:"post",
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsDetail",
        data:{
            goodsId:id
        },
        dataType:"json",
        success:function(data){
            var str=JSON.stringify(data);
            localStorage.setItem("goodsId",id);
			localStorage.setItem("productDetail",str);
            window.open("../detail-buy.html");
        }
    });

}

//获取所有勾选的商品id
function getAllchecked() {
    var goodsId = "";
    jQuery('input:checkbox[name=subCheck]:checked').each(function (i) {
        if (i == 0) {
            goodsId = jQuery(this).val();
        } else {
            goodsId += ("," + jQuery(this).val());
        }
    });
    var goodsIdArray = goodsId.split(",");
    return goodsId;
}



function deleteSelect() {
    var goodsId = getAllchecked();
    console.log("批量删除收藏商品列表:" + goodsId);
    var token = "";
    token = localStorage.getItem("token");
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/deleteCollectGoodsBatch",
        dataType: "json",
        data: {
            "token": token,
            "collectIdList": goodsId
        },
        success: function (data) {
            /*
           //这是全局刷新，这种方式用户体验不好 
            window.location.reload();
            */
        },
        error: function (data) {

        }
    });
}




/*----------------------------------
--------判断是否登陆，显示适当navigation-
-----------------------------------*/
function navigationJudge() {
    var token = localStorage.getItem("token");
    console.log(token);
    if (token == null) {
        var navString = '<a href="../login.html"><i class="icon-user"></i></a><a href="../login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="../register.html"><span>注册</span></a>';
        jQuery('.login-info').append(navString);
        console.log(navString);
    } else {
        console.log("123");
        jQuery.ajax({
            type: "post",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
            data: {
                token: token,
            },
            dataType: "JSON",
            success: function (data) {
                if (data.result == 1) {
                    localStorage.setItem("userInfo", data.content)
                    console.log(data.content);
                    var navString = '<span class="login-success"><a>欢迎您!&nbsp;</a>' +
                        '<a href="../personInfo/editUserInfo.html" class="user-name user-nick">' + data.content.name +
                        '</a></span><span class="logon-success">' +
                        '<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
                    jQuery('.login-info').append(navString);
                    console.log(navString);
                }
                else{
                     var navString = '<a href="#"><i class="icon-user"></i></a><a href="../../login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="../../register.html"><span>注册</span></a>';
                    jQuery('.login-info').append(navString);
                }
            }
        });
    }
}

$(function(){
     setUserInfo();
    
    console.log("ready方式加载。");
});


/*-------------------------
    title JS end
---------------------------- */

/*初始化 “我的收藏界面 myCollection.html” 商品信息   */
function myCollectionload() {
    navigationJudge();
    var screenHeight = jQuery(window).height();
    var screenWidth = jQuery(window).width();
    jQuery("#container").css("height", screenHeight);
    jQuery("#container").css("width", screenWidth);
    var token = "";
    token = localStorage.getItem("token");
    console.log(token);
    if (token != null) {
        //判断用户是否登录
        jQuery.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getCollectGoodsList",
            dataType: "json",
            data: {
                "token": token,
            },
            success: function (data) {
                console.log(data.content.collectList);
                createCollectionList(data);
            },
        });
    }
}




//初始化“我的收藏夹”的数据
function createCollectionList(data) {
    // console.log(data);
    var listdata = data.content.collectList;
    // console.log(listdata);
    var dataArrayLength = data.content.collectList.length;
    if (dataArrayLength == 0) {
        jQuery("#collection-table").hide();
        jQuery("#contentDetailInfo_div").hide();
        jQuery(".empty-cart").show();
    }
    jQuery(".allnum").text(dataArrayLength);
    jQuery(".collectionNum").text(dataArrayLength);
    var Data = new Array();
    var dataLen = 0;
    for (var i = 0; i < dataArrayLength; i++) {
        Data[dataLen++] = data['content']['collectList'][i];
    }
    // console.log("收藏商品个数:" + Data.length);
    var len = Data.length;
    jQuery.each(Data, function (len, val) {
        var str = "";
        str += '<li><div id="imgDiv"><img src=" ' + Data[len].picture + ' " class="productImg"  onclick="getProductDetail('+val["goodsId"]+')" /></div>';
        str += '<div id="am-checkbox"><input class = "check" type = "checkbox" name = "subCheck"  value="' + val["goodsId"] + '"/></div><p id="productInfo" title="' + Data[len].name + '">' + Data[len].name.substr(0, 10) + '</p>';
        str += '<p id="productPacket">500 g/' + Data[len].unit + '</p><p id="price">¥&nbsp;' + Data[len].price + '</p></li>';
        jQuery("#contentDetailInfo").append(str);
    });
}
