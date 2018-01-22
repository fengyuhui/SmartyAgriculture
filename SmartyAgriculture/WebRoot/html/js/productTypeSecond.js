/********************************


        created  by  zhangqiang
        Date:2016-9-25


********************************/
var j;
var current; //当前页数

function getProductDetail(id) {
    console.log("进入");
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
            window.open("../detail-buy.html");
        }
    })
}

function setProductPage(id, pageNum) {

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
                    typeIdChild: id,
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


function productTypeOnloadLast() {
    navigationJudge();
    var productList = localStorage.getItem("productListSecond");
    productList = JSON.parse(productList);
    var pageNum = localStorage.getItem("pageNum");
    var currentPage = localStorage.getItem("currentPage");
    console.log("当前页:" + currentPage);
    var id = localStorage.getItem('id');
    console.log("获取的id：" + id);
    console.log("获取的商品");
    console.log(productList);
    var len = productList.length;
    console.log("页数：");
    console.log(pageNum);
    console.log("商品个数：");
    console.log(len);
    if (len == 0) {
        $(".info_content_div").hide();
        $('.searchNullImg').show();
        $(".footer").css({ "position": "absolute", "width": "100%", "clear": "both", "bottom": "0px" });
    }
    if (len < 5) {
        jQuery("#contentDetailInfo_div").height(650);
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
    setProductPage(id, pageNum);
}



/* 旧的分页代码 */


// function loadData(id, index) {
//     var elements = jQuery(".pagination>li");
//     console.log(elements);
//     for (var i = 1; i <= elements.length; i++) {
//         if (i === index) {
//             // console.log(i);
//             // console.log(elements[i]);
//             $(elements[i + 1]).addClass("active");
//             j = index;
//         }
//         if (i != index) {
//             $(elements[i + 1]).removeClass('active');
//         }

//     }
//     console.log("id:" + id);
//     jQuery.ajax({
//         type: "POST",
//         url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
//         data: {
//             typeIdChild: id,
//             pageNum: index,
//             pageSize: 20,
//         },
//         dataType: "json",
//         success: function(data) {
//             var len = data.content.goodsList.length;
//             if (1 < len < 5) {
//                 jQuery("#contentDetailInfo_div").height(600);
//             }
//             jQuery('#contentDetailInfo').empty();
//             jQuery.each(data.content.goodsList, function(i, val) {
//                 var string = "";

//                 string += "<li onmouse onclick='getProductDetail(" + val['id'] + ")'> <div  id='imgDiv'><img src=' " + val['picture'] + " ' class='productImg' /></div>";
//                 string += "<p id ='productInfo'><span id = 'productInfo_span' title='" + val['name'] + "'> " + val['name'].substr(0, 10) + "</span></p>";
//                 string += "<p id='productTitle' title='" + val['title'] + "'>" + val['title'].substr(0, 10) + "</p>";
//                 string += "<p id='buyNum'>" + val['buyNum'] + " 人已购买</p>";
//                 string += "<p id='price'><span>&nbsp;¥&nbsp;</span><span id='priceright'>" + val['price'] + "&nbsp;</span></p>";
//                 string += " </li>";

//                 jQuery("#contentDetailInfo").append(string);
//             });

//         },
//     });

// }


// function setPage(id, pageNum, currentPage) {
//     var pageAll = pageNum;
//     console.log(pageNum);
//     var str = '';
//     console.log("当前页：" + currentPage)
//     if (currentPage == 1) {
//         current = 1;
//         str += '<li><a onclick="loadData(' + id + ',' + current + ')">&laquo;</a></li>';
//     } else {
//         current = currentPage - 1;
//         str += '<li><a onclick="loadData(' + id + ',' + current + ')">&laquo;</a></li>';
//     }
//     str += '<li><a onclick="loadData(' + id + ',' + 1 + ')">首页</a></li>';
//     for (var i = 1; i <= pageNum; i++) {

//         if (i === 1) {
//             str += '<li><a onclick="loadData(' + id + ',' + i + ')">' + i + '</a></li>';
//             continue;
//         }
//         if (pageNum === 1) {
//             str += '<li class="active"><a onclick="loadData(' + id + ',' + i + ')">1</a></li>';
//             break;
//         }
//         str += '<li><a onclick="loadData(' + id + ',' + i + ')">' + i + '</a></li>';
//     }
//     str += '<li><a onclick="loadData(' + id + ',' + pageNum + ')">尾页</a></li>';
//     if (currentPage == pageNum) {
//         current = pageNum;
//         str += '<li"><a onclick="loadData(' + id + ',' + current + ')>&raquo;</a></li>';
//     } else {
//         current = currentPage + 1;
//         // console.log(currentPage + 1);
//         str += '<li><a onclick="loadData(' + id + ',' + current + ')">&raquo;</a></li>';
//     }

//     str += '<li style="display: inline-block; padding-top: 7px; padding-left: 10px; font-weight:bold; cursor:default !important;">共 ' + pageNum + ' 页</span></li>';
//     jQuery(".pagination").append(str);
// }
