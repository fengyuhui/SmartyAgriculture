/********************************


        created  by  zhangqiang
        Date:2016-9-25


********************************/
// bootbox.setDefaults("locale", "zh_CN");//设置bootbox弹出框显示中文格式信息

//tab标题栏的下方border的自适应改变
$(function() {
    $("#current").css("border-bottom", "0px solid #fff");
    $("#brand").css("border-bottom", "0px solid #fff");
    $("#ecology").css("border-bottom", "0px solid #fff");
    $("#visit").css("border-bottom", "0px solid #fff");
    $("#life").css("border-bottom", "0px solid #fff");
    $("#health").css("border-bottom", "0px solid #fff");
    $("#game").css("border-bottom", "0px solid #fff");
    $("#product").show();
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
        var navString = '<a href="#"><i class="icon-user"></i></a><a href="../login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="../register.html"><span>注册</span></a>';
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
                        '<a href="../personInfo/editUserInfo.html" class="user-name user-nick">' + data.content.name +
                        '</a></span><span class="logon-success">' +
                        '<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
                    jQuery('.login-info').append(navString);

                } else {
                    var navString = '<a href="#"><i class="icon-user"></i></a><a href="../login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="../register.html"><span>注册</span></a>';
                    jQuery('.login-info').append(navString);
                }
            }
        });
    }
}




//自加载时 加载的第二级分类的商品数据（蔬菜、水果等第一级商品的类别分类） 函数
function createListOnload(id, data) {
    // console.log(data);
	alert(data);
    localStorage.setItem("productID", id);
    var i = 0;
    var length = data.content.albumList.length;
    if (1 < length < 5) {
        jQuery("#contentDetailInfo_div").height(520);
    }
    // console.log("传输长度：" + length);
    var string = "";
    for (i = 0; i < length; i++) {
        string += "<li onclick='getSubChildType(" + data.content.albumList[i].id + "," + id + ")'> <div  id='imgDiv'><img src='" + data.content.albumList[i].picture + "' class='productImg' /></div>";
        string += "<p id ='productInfo'><span id = 'productInfo_span'> " + data.content.albumList[i].name + "</span></p>";
        string += " </li>";
    }
    if (id == 2 || id == 0) {
        jQuery(".vegetablelist").append(string);
    }
    if (id == 1) {
        jQuery(".fruitlist").append(string);
    }
    if (id == 26) {
        jQuery(".oillist").append(string);
    }
    setUserInfo();
}


/* 处理分页js */
function setProductPage(id, pageNum) {
    console.log("编号为 ：" + id);
    console.log("分页长度:" + pageNum);
    jQuery('.pagination').jqPaginator({
        totalPages: Number(pageNum),
        visiblePages: 4,
        currentPage: 1,
        onPageChange: function(num, type) {
            jQuery.ajax({
                type: "POST",
                url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
                data: {
                    "parentId": id,
                    "pageNum": num,
                    "pageSize": 20
                },
                dataType: "json",
                success: function(data) {
                    console.log("新加载页面：" + data);
                    var string = "";
                    for (var i = 0; i < data.content.albumList.length; i++) {
                        string += "<li onclick='getSubChildType(" + data.content.albumList[i].id + "," + id + ")'> <div  id='imgDiv'><img src='" + data.content.albumList[i].picture + "' class='productImg' /></div>";
                        string += "<p id ='productInfo'><span id = 'productInfo_span'> " + data.content.albumList[i].name + "</span></p>";
                        string += " </li>";
                    }
                    // jQuery("#contentDetailInfo_div").append(string);
                    if (id == 2 || id == 0) {
                        jQuery(".vegetablelist").empty();
                        jQuery(".vegetablelist").append(string);
                    }
                    if (id == 1) {
                        jQuery(".fruitlist").empty();
                        jQuery(".fruitlist").append(string);
                    }
                    if (id == 26) {
                        jQuery(".oillist").empty();
                        jQuery(".oillist").append(string);
                    }
                    if (id == 3) {
                        jQuery(".meatlist").empty();
                        jQuery(".meatlist").append(string);
                    }
                },
            });
        }
    });
}



/* 首先需要被加载的函数  */
function loaded() {
	alert("here");
    navigationJudge();
    var screenHeight = jQuery(window).height();
    var screenWidth = jQuery(window).width();
    jQuery("#container").css("height", screenHeight);
    jQuery("#container").css("width", screenWidth);
    // console.log("浏览器的搜索的参数：" + location.search);
    var id = Number(location.search.substring(1));
    if (id != 0) {
        if (id == 2) {
            jQuery(".vegetable_image").attr("src", "../images/productType/vegetable-huan.png");
            jQuery("#fruit_img").attr("src", "../images/productType/fruit.png");
            jQuery("#oil_img").attr("src", "../images/productType/liangyou.png");
            jQuery("#meat_img").attr("src", "../images/productType/meat.png");
            jQuery(".vegetablelist").show();
        }
        if (id == 1) {
            jQuery(".vegetable_image").attr("src", "../images/productType/vegetables.png");
            jQuery("#fruit_img").attr("src", "../images/productType/fruit-huan.png");
            jQuery("#meat_img").attr("src", "../images/productType/meat.png");
            jQuery("#oil_img").attr("src", "../images/productType/liangyou.png");
            jQuery(".fruitlist").show();
        }
        if (id == 26) {
            jQuery(".vegetable_image").attr("src", "../images/productType/vegetables.png");
            jQuery("#fruit_img").attr("src", "../images/productType/fruit.png")
            jQuery("#meat_img").attr("src", "../images/productType/meat.png")
            jQuery("#oil_img").attr("src", "../images/productType/liangyou-huan.png")
            jQuery(".oillist").show();
        }
        jQuery.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
            data: {
                "parentId": id,
                "pageNum": 1,
                "pageSize": 20
            },
            dataType: "json",
            success: function(data) {
                console.log("进入的时候加载的数据");
                setProductPage(id, data.content.allPages);
                // createListOnload(id, data);
            },
            error: function(data) {
                alert("加载数据失败！");
            }
        });

    } else {
        jQuery(".vegetable_image").attr("src", "../images/productType/vegetable-huan.png");
        jQuery(".vegetablelist").show();
        jQuery.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
            data: {
                "parentId": 2,
                "pageNum": 1,
                "pageSize": 20
            },
            dataType: "json",
            success: function(data) {
                setProductPage(2, data.content.allPages);
                // createListOnload(id, data);
            },
            error: function(data) {
                alert("加载数据失败！");
            }
        });

    }

}


function getProductType(id) {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        data: {
            "parentId": id,
            "pageNum": 1,
            "pageSize": 20
        },
        dataType: "json",
        success: function(data) {
            console.log(data);
            createList(data, id);
        },
        error: function(data) {
            alert("加载数据失败！");
        }
    });
}



/**
 * 查看商品二级分类
 */
function getSubChildType(id, type) {
    var productType = Number(type);
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        data: {
            typeIdChild: id,
            pageNum: 1,
            pageSize: 20,
        },
        dataType: "json",
        success: function(data) {
            console.log(data);
            var str = JSON.stringify(data['content']['goodsList']);
            var allPages = data.content.allPages;
            var currentPage = data.content.currentPage;
            console.log(str);
            localStorage.setItem("id", id);
            localStorage.setItem("pageNum", allPages);
            localStorage.setItem("currentPage", currentPage)
            localStorage.setItem("productListSecond", str);
            var urlString = "productTypeSecond.html?" + productType;
            window.location.href = urlString;
        },
    });
}



//自加载时 加载的第二级分类的商品数据（蔬菜、水果等第一级商品的类别分类） 函数
function createListOnload(id, data) {
    // console.log(data);
    localStorage.setItem("productID", id);
    var i = 0;
    var length = data.content.albumList.length;
    if (1 < length < 5) {
        jQuery("#contentDetailInfo_div").height(520);
    }
    // console.log("传输长度：" + length);
    var string = "";
    for (i = 0; i < length; i++) {
        string += "<li onclick='getSubChildType(" + data.content.albumList[i].id + "," + id + ")'> <div  id='imgDiv'><img src='" + data.content.albumList[i].picture + "' class='productImg' /></div>";
        string += "<p id ='productInfo'><span id = 'productInfo_span'> " + data.content.albumList[i].name + "</span></p>";
        string += " </li>";
    }
    if (id == 2 || id == 0) {
        jQuery(".vegetablelist").append(string);
    }
    if (id == 1 || id == 184) {
        jQuery(".fruitlist").append(string);
    }
    if (id == 26 || id == 154) {
        jQuery(".oillist").append(string);
    }
    setUserInfo();
}



function createProductList(data, type) {
    var string = "";
    var length = data.content.albumList.length;
    for (var i = 0; i < length; i++) {
        console.log(i);
        string += "<li onclick='getSubChildType(" + data.content.albumList[i].id + "," + type + ")'> <div  id='imgDiv'><img src='" + data.content.albumList[i].picture + "' class='productImg' /></div>";
        // string += "<img class='picture' src='" + data.content[i].picture + "' />";
        string += "<p id ='productInfo'>" + data.content.albumList[i].name + "</p>";
        string += " </li>";
    }
    switch (type) {
        case 1:
            jQuery(".fruitlist").append(string);
            break;
        case 2:
            jQuery(".vegetablelist").append(string);
            break;
        case 3:
            jQuery(".meatlist").append(string);
            break;
        case 26:
            jQuery(".oillist").append(string);
            break;
        case 44:
            jQuery(".flourlist").append(string);
            break;
        case 97:
            jQuery(".juhualist").append(string);
            break;
    }
}



function Vegetablelist() {
	alert("here");
    jQuery(".vegetable_image").attr("src", "../images/productType/vegetable-huan.png");
    jQuery("#fruit_img").attr("src", "../images/productType/fruit.png");
    jQuery("#oil_img").attr("src", "../images/productType/liangyou.png");
    jQuery("#meat_img").attr("src", "../images/productType/meat.png");
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        dataType: "json",
        data: {
            "parentId": 2,
            "pageNum": 1,
            "pageSize": 20
        },
        success: function(data) {
            jQuery(".vegetablelist").empty();
            jQuery(".oillist").empty();
            jQuery(".fruitlist").empty();
            jQuery(".meatlist").empty();
            console.log("蔬菜: ");
            console.log(data);
            setProductPage(2, data.content.allPages);
        },
        error: function(data) {
            alert("加载数据失败！");
        }
    });
}

function Meatlist() {
    jQuery(".vegetable_image").attr("src", "../images/productType/vegetables.png");
    jQuery("#fruit_img").attr("src", "../images/productType/fruit.png");
    jQuery("#oil_img").attr("src", "../images/productType/liangyou.png");
    jQuery("#meat_img").attr("src", "../images/productType/meat-huan.png");
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        dataType: "json",
        data: {
            "parentId": 3,
            "pageNum": 1,
            "pageSize": 20
        },
        success: function(data) {
            jQuery(".meatlist").empty();
            jQuery(".oillist").empty();
            jQuery(".vegetablelist").empty();
            jQuery(".fruitlist").empty();
            console.log("肉类: ");
            console.log(data);
            setProductPage(3, data.content.allPages);
        },
        error: function(data) {
            alert("加载数据失败！");
        }
    });
}

function Fruitlist() {
    jQuery(".vegetable_image").attr("src", "../images/productType/vegetables.png");
    jQuery("#fruit_img").attr("src", "../images/productType/fruit-huan.png");
    jQuery("#meat_img").attr("src", "../images/productType/meat.png");
    jQuery("#oil_img").attr("src", "../images/productType/liangyou.png");
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        dataType: "json",
        data: {
            "parentId": 1,
            "pageNum": 1,
            "pageSize": 20
        },
        success: function(data) {
            jQuery(".fruitlist").empty();
            jQuery(".oillist").empty();
            jQuery(".vegetablelist").empty();
            jQuery(".meatlist").empty();
            console.log("水果: ");
            console.log(data);
            setProductPage(1, data.content.allPages);
        },
        error: function(data) {
            alert("加载数据失败！");
        }
    });
}

function Oillist() {
    jQuery(".vegetable_image").attr("src", "../images/productType/vegetables.png");
    jQuery("#fruit_img").attr("src", "../images/productType/fruit.png")
    jQuery("#meat_img").attr("src", "../images/productType/meat.png")
    jQuery("#oil_img").attr("src", "../images/productType/liangyou-huan.png")
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        dataType: "json",
        data: {
            "parentId": 26,
            "pageNum": 1,
            "pageSize": 20
        },
        success: function(data) {
            jQuery(".oillist").empty();
            jQuery(".vegetablelist").empty();
            jQuery(".fruitlist").empty();
            jQuery(".meatlist").empty();
            console.log("粮油: ");
            console.log(data);
            // createProductList(data, 26);
            setProductPage(26, data.content.allPages);
        },
        error: function(data) {
            alert("加载数据失败！");
        }
    });
}

function titleShoppingCard() {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        dataType: "json",
        data: {
            "parentId": 26,
            "pageNum": 1,
            "pageSize": 20
        },
        success: function(data) {
            jQuery(".oillist").empty();
            jQuery(".vegetablelist").empty();
            jQuery(".fruitlist").empty();
            jQuery(".meatlist").empty();
            console.log("购物卡: " + data);
            // createProductList(data, 26);
            setProductPage(26, data.content.allPages);
        },
        error: function(data) {
            alert("加载数据失败！");
        }
    });
}



function productDetailListLoaded() {
    var screenHeight = jQuery(window).height();
    var screenWidth = jQuery(window).width();
    jQuery("#container").css("height", screenHeight);
    jQuery("#container").css("width", screenWidth);
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getChildGoodsTypeList",
        data: {
            "parentId": 2,
            "pageNum": 1,
            "pageSize": 20
        },
        dataType: "json",
        success: function(data) {
            var array = new Array();
            var array_length = 0;
            console.log("首先加载得到的：");
            for (var id in data.content) {
                console.log(data.content.albumList[id].id);
                array[array_length++] = data.content.albumList[id].id;
            }
            //    console.log("数组长度："+array+array.length);
            //    console.log(data.content[0,data.content.length-1].id);
            console.log(data.content.albumList.length);
            console.log(array);
            console.log(array_length);
            // console.log(data.content[0, data.content.length - 1].id);
            createGoodList(array.length - 1, array);
        },
        error: function(data) {
            console.log("加载数据失败！");
        }
    });
}

function createGoodList(len, array) {
    console.log(len);
    console.log(array);
    for (var id in array) {
        jQuery.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
            data: {
                typeIdChild: id,
                pageNum: 1,
                pageSize: 12,
            },
            dataType: "json",
            success: function(data) {
                // console.log(data);
                // var str = JSON.stringify(data['content']['goodsList']);
                // console.log(str);
                // localStorage.setItem("productListSecond", str);
                // var proId = val['id'];
                var string = '<li><div  id="imgDiv"><img' +
                    '<img class="full" src="' + val['picture'] + '">' +
                    '<div class="show-font"><p><span class="title-1">' + val['name'] + '</span></p>' +
                    '<p><span class="title-2" title="' + val['title'] + '">' + val['title'] + '</span></p>' +
                    '<p><span class="title-price">¥' + val['price'] + '/' + val['unit'] + '</span></p>' +
                    '</div></li>';
                jQuery("#contentDetailInfo").append(string);
            },
        });
    }
}



// function createProductList(data) {

// }


function createProductListdetail(data) {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
        data: {
            typeIdChild: id,
            pageNum: 1,
            pageSize: 12,
        },
        dataType: "json",
        success: function(data) {
            console.log(data);
            var str = JSON.stringify(data['content']['goodsList']);
            console.log(str);
            localStorage.setItem("productListSecond", str);
            window.location.href = "productTypeSecond.html";
        },
    });

}


function signOut() {
    localStorage.clear();
    window.location.reload();
}
