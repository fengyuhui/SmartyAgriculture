/**
 * created by zhangqiang
 * date: 2016 10 20
 */

var photoNum = 0;
var count = 1;
var flag = true;
var j;

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




/*----------------------------------
--------判断是否登陆，显示适当navigation-
-----------------------------------*/

jQuery(document).ready(function() {
    var screenHeight = jQuery(window).height;
    var screenWidth = jQuery(window).width;
    jQuery("#container").css("width", "screenWidth");
    jQuery("#container").css("height", "screenHeight");
    console.log(screenWidth);
    console.log(screenHeight);
    navigationJudge();
});



$(document).keypress(function(e) {
    //回车键事件
    if (e.which == 13) {
        jQuery(".search-btn").click();
    }
});



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
                        '<a href="#" class="user-name user-nick">' + data.content.name +
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


window.onload = function() {
    imgLoad();

}


/* 处理分页功能 */
function setProductPage(pageNum) {
    jQuery('.pagination').jqPaginator({
        totalPages: Number(pageNum),
        visiblePages: 4,
        currentPage: 1,
        onPageChange: function(num, type) {
            jQuery.ajax({
                type: "POST",
                url: "http://127.0.0.1:8080/SmartyAgriculture/interface/farmMessage/getFarmPhoto",
                data: {
                    pageNum: num,
                    pageSize: 20,
                },
                dataType: "json",
                success: function(data) {
                    console.log("新加载页面：");
                    console.log(data.content.albumList.length);
                    jQuery(".mygallery").empty();
                    var string = "";
                    if (Number(data.content.albumList.length) < 5) {
                        jQuery(".chroma-gallery").css('height', '900px');
                    } else {
                        jQuery(".chroma-gallery").css('height', 'auto');
                    }
                    jQuery.each(data.content.albumList, function(i, val) {
                        string += "<img src='" + data.content.albumList[i].pictures + "' alt='" + data.content.albumList[i].photoName + "'>";
                    });
                    jQuery(".chroma-gallery").append(string);
                    jQuery('.mygallery').chromaGallery({
                        color: '#000',
                        gridMargin: 15,
                        maxColumns: 5,
                        dof: true,
                        screenOpacity: 0.8,
                        // lazyLoad: true
                    });
                },
            });
        }
    });
}




function imgLoad() {
    $.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/farmMessage/getFarmPhoto",
        data: {
            "pageNum": 1,
            "pageSize": 200
        },
        dataType: "json",
        success: function(data) {
            console.log("页数：" + data.content.allPages);

            var str = "";
            jQuery.each(data.content.albumList, function(i, val) {
                str += "<img src='" + data.content.albumList[i].pictures + "' alt='" + data.content.albumList[i].photoName + "'>";
            });
            jQuery(".chroma-gallery").append(str);
            jQuery('.mygallery').chromaGallery({
                color: '#000',
                gridMargin: 15,
                maxColumns: 5,
                dof: true,
                screenOpacity: 0.8,
                lazyLoad: true
            });

            // setProductPage(data.content.allPages);
        }
    });
}


function signOut() {
    localStorage.clear();
    window.location.reload();
}



// function loadMorePhotos() {
//     if (flag) {
//         count++;
//         console.log("进入点击加载函数中...");
//         console.log("加载的次数:" + (count - 1));
//         $.ajax({
//             type: "post",
//             async: false,
//             url: "http://127.0.0.1:8080/SmartyAgriculture/interface/farmMessage/getFarmPhoto",
//             data: {
//                 "pageSize": 0,
//                 "pageNum": count
//             },
//             dataType: "json",
//             success: function(data) {
//                 console.log("加载出来的图片数量有：" + data.content.length);
//                 createImgList(data, 10 * count);
//             }
//         });
//         // imgLoad(count*10);
//     }

// }

// function createImgList(data, loadCount) {
//     var i;
//     var len = data.content.length;
//     console.log("第" + (count - 1) + "次点击:" + (count - 1));
//     console.log("传递到函数的图片张数有:" + len);
//     console.log("loadCount:" + loadCount);
//     if (len > loadCount) {
//         var string = "";
//         console.log("(count-1)*10:" + (count - 1) * 10);
//         console.log("loadCount:" + loadCount);
//         for (i = (count - 1) * 10; i < loadCount; i++) {
//             console.log(data.content[i].pictures);
//             string += "<img src='" + data.content[i].pictures + "' alt='照片" + (i + 1) + "'>";
//         }
//         $("#loadMore").val("正在加载...");
//         console.log("string = " + string);
//         jQuery(".chroma-gallery").prepend(string);
//         jQuery(".mygallery").chromaGallery({
//             color: '#000',
//             gridMargin: 15,
//             maxColumns: 5,
//             dof: true,
//             screenOpacity: 0.8,
//             lazyLoad: true
//         });
//         $("#loadMore").val("点击获取更多数据");

//     } else {
//         if (len % 10 !== 0) {
//             string = "";
//             console.log("余数数据");
//             console.log("第一个数据：" + (count - 1) * 10);
//             let Num = Number((count - 1) * 10 + (len % 10));
//             console.log("最后一个数据：" + Num);
//             for (i = (count - 1) * 10; i < Num; i++) {
//                 console.log(data.content[i].pictures);
//                 string += "<img src='" + data.content[i].pictures + "' alt='照片" + (i + 1) + "'>";

//             }
//             console.log("string = " + string);
//             jQuery(".chroma-gallery").prepend(string);
//             jQuery(".mygallery").chromaGallery({
//                 color: '#000',
//                 gridMargin: 15,
//                 maxColumns: 5,
//                 dof: true,
//                 screenOpacity: 0.8,
//                 lazyLoad: true
//             });
//             $("#loadMore").val("暂无数据");
//         }
//         flag = false;
//         $("#loadMore").val("暂无数据");
//     }
// }




// function loadData(index) {
//     var elements = jQuery(".pagination>li");
//     console.log(elements);
//     for (var i = 1; i <= elements.length; i++) {
//         if (i === index) {
//             console.log(i);
//             console.log(elements[i]);
//             $(elements[i + 1]).addClass("active");
//             j = index;
//             // alert("j="+j);
//         }
//         if (i != index) {
//             $(elements[i + 1]).removeClass('active');
//         }

//     }
//     jQuery.ajax({
//         type: "POST",
//         // async: false,
//         url: "http://127.0.0.1:8080/SmartyAgriculture/interface/farmMessage/getFarmPhoto",
//         data: {
//             pageNum: index,
//             pageSize: 20,
//         },
//         dataType: "json",
//         success: function(data) {
//             var string = "";
//             console.log("点击分页：");
//             console.log(data);
//             var len = data.content.albumList.length;
//             console.log("页数：");
//             console.log(data.content.allPages);
//             console.log("商品个数：");
//             console.log(len);
//             if (1 < len < 5) {
//                 jQuery('.mygallery').height(600);
//             }
//             jQuery('.mygallery').empty();
//             jQuery.each(data.content.albumList, function(i, val) {
//                 string += "<img src='" + data.content.albumList[i].pictures + "' alt='照片" + (i + 1) + "'>";
//             });
//             console.log("照片流:" + string);
//             jQuery(".chroma-gallery").append(string);
//             jQuery(".mygallery").chromaGallery({
//                 color: '#000',
//                 gridMargin: 15,
//                 maxColumns: 5,
//                 dof: true,
//                 screenOpacity: 0.8,
//                 lazyLoad: true
//             });
//         },
//     });
// }




// function setPage(pictureNum) {
//     var pageAll = Number(pictureNum);
//     console.log(pageAll);
//     var str = '';
//     if (pageAll > 1 && j - 1 <= 1) {
//         str += '<li><a onclick="loadData(' + j - 1 + ')">&laquo;</a></li>';
//     } else {
//         str += '<li><a onclick="loadData(' + 1 + ')">&laquo;</a></li>';
//     }
//     str += '<li><a onclick="loadData(' + 1 + ')">首页</a></li>';
//     for (var i = 1; i <= pageAll; i++) {

//         if (i === 1) {
//             str += '<li><a onclick="loadData(' + i + ')">' + i + '</a></li>';
//             continue;
//         }
//         if (pageAll === 1) {
//             str += '<li class="active"><a onclick="loadData(' + i + ')">1</a></li>';
//             break;
//         }
//         str += '<li><a onclick="loadData(' + i + ')">' + i + '</a></li>';
//     }
//     str += '<li><a onclick="loadData(' + pageAll + ')">尾页</a></li>';
//     if (j <= pageAll) {
//         str += '<li"><a onclick="loadData(' + i + ')>&raquo;</a></li>';
//     } else {
//         str += '<li><a onclick="loadData(' + pageAll + ')">&raquo;</a></li>';
//     }

//     str += '<li style="display: inline-block; padding-top: 7px; padding-left: 10px; font-weight:bold; cursor:default !important;">共 ' + pageAll + ' 页</span></li>';
//     jQuery(".pagination").append(str);
// }
