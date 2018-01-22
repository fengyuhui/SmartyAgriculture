/*--------------------------------------------

    Created by zhangqiang

    2016.8.23

------------------------------------------*/

// bootbox.setDefaults("locale", "zh_CN"); //设置bootbox弹出框显示中文格式信息


//tab标题栏的下方border的自适应改变

var j;

$(function() {
    $("#current").css("border-bottom", "0px solid #fff");
    $("#brand").show();
    $("#ecology").css("border-bottom", "0px solid #fff");
    $("#visit").css("border-bottom", "0px solid #fff");
    $("#life").css("border-bottom", "0px solid #fff");
    $("#health").css("border-bottom", "0px solid #fff");
    $("#game").css("border-bottom", "0px solid #fff");
    $("#product").css("border-bottom", "0px solid #fff");
})



/**
 * 获取搜索信息
 */
function setSearchinfo() {
    var search_info = jQuery(".search-field").val();
    if (search_info == "搜索") {
        return 0;
    }
    localStorage.setItem("search_info", search_info);
}


/**
 * 回车键事件 
 * 绑定键盘按下事件 
 */
$(document).keypress(function(e) {
    // 回车键事件  
    if (e.which == 13) {
        jQuery(".search-btn").click();
    }
});



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
    console.log("Token:" + token);
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



function setProductPage(pageNum) {

    jQuery('.pagination').jqPaginator({
        totalPages: Number(pageNum),
        visiblePages: 4,
        currentPage: 1,
        onPageChange: function(num, type) {
            // $('#text').html('当前第' + num + '页');
            jQuery.ajax({
                type: "POST",
                url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
                data: {
                    pageNum: num,
                    pageSize: 20,
                },
                dataType: "json",
                success: function(data) {
                    console.log("新加载页面：" + data);
                    jQuery("#contentDetailInfo").empty();
                    if (1 < data.content.goodsList.length < 5) {
                        jQuery("#contentDetailInfo_div").height(650);
                    }
                    jQuery.each(data.content.goodsList, function(i, val) {
                        var string = "";

                        string += "<li onmouse onclick='getProductDetail(" + val['id'] + ")'> <div  id='imgDiv'><img src=' " + val['picture'] + " ' class='productImg' /></div>";
                        string += "<p id ='productInfo'><span id = 'productInfo_span' title='" + val['name'] + "'> " + val['name'].substr(0, 16) + "</span></p>";
                        string += "<p id='productTitle' title='" + val['title'] + "'>" + val['title'].substr(0, 15) + "</p>";
                        string += "<p id='buyNum'>" + val['buyNum'] + "  人已购买</p>";
                        string += "<p id='price'><span>&nbsp;¥&nbsp;</span><span id='priceright'>" + val['price'] + "&nbsp;</span></p>";
                        string += " </li>";

                        jQuery("#contentDetailInfo").append(string);
                    });

                },
            });
        }
    });
}






/**
 * 查看商品二级分类
 */
function createProductData() {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        data: {
            pageNum: 1,
            pageSize: 20,
        },
        dataType: "json",
        success: function(data) {
            var str = JSON.stringify(data['content']['goodsList']);
            var allPages = data.content.allPages;
            localStorage.setItem("pageNum", allPages);
            localStorage.setItem("productListSecond", str);
            setProductPage(data.content.allPages);
        },
    });
}


function getProductDetail(id) {
    console.log("进入跳转详情页面函数中。");
    jQuery.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsDetail",
        data: {
            goodsId: id,
        },
        dataType: "json",
        success: function(data) {
            var str = JSON.stringify(data);
            localStorage.setItem("goodsId", id);
            localStorage.setItem("productDetail", str);
            window.open("detail-buy.html");
        }
    })
}


function productLoad() {
    navigationJudge();
    createProductData();
    var productList = localStorage.getItem("productListSecond");
    productList = JSON.parse(productList);
    var pageNum = localStorage.getItem("pageNum");
    // var id = localStorage.getItem('id');
    // console.log("获取的id：" + id);
    var len = productList.length;
    // setPage(pageNum);
    if (1 < len < 5) {
        jQuery("#contentDetailInfo_div").height(600);
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
}


function signOut() {
    localStorage.clear();
    window.location.reload();
}



function setPage(pageNum) {
    var pageAll = pageNum;
    console.log(pageNum);
    var str = '';
    if (pageNum > 1 && j - 1 <= 1) {
        str += '<li><a onclick="loadData(' + j - 1 + ')">&laquo;</a></li>';
    } else {
        str += '<li><a onclick="loadData(' + 1 + ')">&laquo;</a></li>';
    }
    str += '<li><a onclick="loadData(' + 1 + ')">首页</a></li>';
    for (var i = 1; i <= pageNum; i++) {

        if (i === 1) {
            str += '<li><a onclick="loadData(' + i + ')">' + i + '</a></li>';
            continue;
        }
        if (pageNum === 1) {
            str += '<li class="active"><a onclick="loadData(' + i + ')">1</a></li>';
            break;
        }
        str += '<li><a onclick="loadData(' + i + ')">' + i + '</a></li>';
    }
    str += '<li><a onclick="loadData(' + pageNum + ')">尾页</a></li>';
    if (j <= pageNum) {
        str += '<li"><a onclick="loadData(' + i + ')>&raquo;</a></li>';
    } else {
        str += '<li><a onclick="loadData(' + pageNum + ')">&raquo;</a></li>';
    }

    str += '<li style="display: inline-block; padding-top: 7px; padding-left: 10px; font-weight:bold; cursor:default !important;">共 ' + pageNum + ' 页</span></li>';
    jQuery(".pagination").append(str);
}



function loadData(index) {
    var elements = jQuery(".pagination>li");
    console.log(elements);
    for (var i = 1; i <= elements.length; i++) {
        if (i === index) {
            console.log(i);
            console.log(elements[i]);
            $(elements[i + 1]).addClass("active");
            j = index;
            // alert("j="+j);
        }
        if (i != index) {
            $(elements[i + 1]).removeClass('active');
        }

    }
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        data: {
            pageNum: index,
            pageSize: 10,
        },
        dataType: "json",
        success: function(data) {
            console.log("点击分页：");
            console.log(data);
            var len = data.content.goodsList.length;
            console.log("页数：");
            console.log(data.content.allPages);
            console.log("商品个数：");
            console.log(len);
            if (1 < len < 5) {
                jQuery("#contentDetailInfo_div").height(600);
            }
            jQuery('#contentDetailInfo').empty();
            jQuery.each(data.content.goodsList, function(i, val) {
                var string = "";

                string += "<li onmouse onclick='getProductDetail(" + val['id'] + ")'> <div  id='imgDiv'><img src=' " + val['picture'] + " ' class='productImg' /></div>";
                string += "<p id ='productInfo'><span id = 'productInfo_span' title='" + val['name'] + "'> " + val['name'].substr(0, 10) + "</span></p>";
                string += "<p id='productTitle' title='" + val['title'] + "'>" + val['title'].substr(0, 10) + "</p>";
                string += "<p id='buyNum'>" + val['buyNum'] + " 人已购买</p>";
                string += "<p id='price'><span>&nbsp;¥&nbsp;</span><span id='priceright'>" + val['price'] + "&nbsp;</span></p>";
                string += " </li>";

                jQuery("#contentDetailInfo").append(string);
            });

        },
    });

}
