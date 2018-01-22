/*--------------------------------------------

    Created by zhangqiang

    2016.8.23

------------------------------------------*/
// bootbox.setDefaults("locale", "zh_CN"); //设置bootbox弹出框显示中文格式信息



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
    if (jQuery.trim(token) != "") {
        return token;
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
        success: function(data) {
            if (data['result'] == 1) {
                dataInfo = data;
            }
        }
    });
    return dataInfo;
}

function getPersonInfo() {
    var token = getToken();
    var data = getUserInfo(token);
    jQuery("#username").text(data['content']['name']);
    jQuery("#score").text(data['content']['phone']);
    jQuery("#person_portrait").attr("src", data['content']['portrait']);
    //设置header中的用户名
    jQuery("#header_username").text(data['content']['name']);
}

/*----------------------------------
--------判断是否登陆，显示适当navigation-
-----------------------------------*/

function navigationJudge() {
    var token = localStorage.getItem("token");
//  console.log("Token:" + token);
    if (token == null) {
        var navString = '<a href="#"><i class="icon-user"></i></a><a href="login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="register.html"><span>注册</span></a>';
        jQuery('.login-info').append(navString);

    } else {

        jQuery.ajax({
            type: "post",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
            data: {
                token: token,
            },
            dataType: "JSON",
            success: function(data) {
                if (data.result == 1) {
                    localStorage.setItem("userInfo", data.content)
                    var navString = '<span class="login-success"><a>欢迎您!&nbsp;</a>' +
                        '<a href="personInfo/editUserInfo.html" class="user-name user-nick">' + data.content.name +
                        '</a></span><span class="logon-success">' +
                        '<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
                    jQuery('.login-info').append(navString);
                } else {
                    var navString = '<a href="#"><i class="icon-user"></i></a><a href="login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="register.html"><span>注册</span></a>';
                    jQuery('.login-info').append(navString);
                }
            }
        });
    }
}


function createList(data) {
    jQuery("#contentDetailInfo").empty();
    console.log(data);
    var productList = data['content']['goodsList'];
    console.log(productList);
    var i = 0;
    var MaxLength = 0;
    Maxlength = productList.length;
    console.log("goodlist长度:" + Maxlength);
    var string = "";
    if (Maxlength > 0) {
        if (1 < Maxlength < 5) {
            jQuery("#content-icon-table").show();
            jQuery("#contentDetailInfo_div").height(600);
            jQuery(".searchNullImg").hide();
        }
        jQuery.each(productList, function(i, val) {
            var string = "";
            string += "<li onmouse onclick='getProductDetail(" + val['id'] + ")'> <div  id='imgDiv'><img src=' " + val['picture'] + " ' class='productImg' /></div>";
            string += "<p id ='productInfo'><span id = 'productInfo_span' title='" + val['name'] + "'> " + val['name'].substr(0, 16) + "</span></p>";
            string += "<p id='productTitle' title='" + val['title'] + "'>" + val['title'].substr(0, 15) + "</p>";
            string += "<p id='buyNum'>" + val['buyNum'] + "  人已购买</p>";
            string += "<p id='price'><span>&nbsp;¥&nbsp;</span><span id='priceright'>" + val['price'] + "&nbsp;</span></p>";
            string += " </li>";
            jQuery("#contentDetailInfo").append(string);
        });
    } else {
        jQuery("#content-icon-table").hide();
        jQuery(".searchNullImg").show();
        string = "";
    }
}


function getProductDetail(id){
    console.log("进入");
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





function searchClick() {
    var searchfield = jQuery(".search-field").val();
    console.log("商品名称：" + searchfield);
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        dataType: "json",
        data: {
            name: searchfield, //此处是传递给服务器需要查询的商品名称
            pageNum: 1,
            pageSize: 10,
        },
        success: function(data) {
            createList(data);
        },
    });
}


$(document).keypress(function(e) {
    //回车键事件
    if (e.which == 13) {
        jQuery(".search-btn").click();
    }

});


function loads() {
    navigationJudge();
    var screenHeight = jQuery(window).height();
    var screenWidth = jQuery(window).width();
    jQuery("#container").css("height", screenHeight);
    jQuery("#container").css("width", screenWidth);
    var searchfield = localStorage.getItem("search_info");
    console.log(searchfield);
    jQuery(".search-field").val(searchfield);
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        dataType: "json",
        data: {
            name: searchfield, //此处是传递给服务器需要查询的商品名称
            pageNum: 1,
            pageSize: 2
        },
        success: function(data) {
            createList(data);
        },
    });
}

function signOut() {
    localStorage.clear();
    window.location.reload();
}
