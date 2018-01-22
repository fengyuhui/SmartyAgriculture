/*----------------------------

create by xiaoming

2016-7-18

-----------------------------*/
bootbox.setDefaults("locale", "zh_CN"); //设置bootbox弹出框显示中文格式信息

/**
 * 获取搜索信息
 */
function setSearchinfo() {
    var search_info = jQuery(".search-input").val();
    if (search_info === "") {
        return 0;
    }
    localStorage.setItem("search_info", search_info);
    window.location.href = 'search.html';
    // window.open("search.html");
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


function loadInfo() {

    //screen size
    var screenHeight = $(window).height();
    var screenWidth = $(window).width();
    $(".wrapper").css("height", screenHeight);
    $(".wrapper").css("width", screenWidth);
    console.log(screenHeight);
    console.log(screenWidth);
    var token = "";
    token = localStorage.getItem("token");
    console.log(token);
    if (token != null) {
        //显示用户信息
        $(".left-button a").hide();
        $(".left-button img").hide();
        jQuery.ajax({
            type: "post",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
            data: {
                token: token,
            },
            dataType: "JSON",
            success: function(data) {
                if (data.result == 1) {
                    var navString = '<a href="personInfo/infoIndex.html"><img class="headtitle-picture" src="images/shoppingCart/user.png"></a><span class="login-success">' +
                        '<a href="personInfo/infoIndex.html">' + data.content.name +
                        '</a></span><span class="logon-success">' +
                        '<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
                    $('.left-button').append(navString);

                }
            }
        });
        //table info js start
        $.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/getGoodsCartList",
            dataType: "json",
            data: {
                "token": token
            },
            success: function(data) {
                Createtable(data);
            },
            error: function(data) {
                $(".weui_dialog_bd").text('网络错误');
                $("#dialog2").show();
            }
        });
        $.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getLikeGoodsList",
            dataType: "json",
            data: {
                "token": token
            },
            success: function(data) {
                CreatelikeTable(data);
            },
            error: function(data) {
                $(".weui_dialog_bd").text('网络错误');
                $("#dialog2").show();
            }
        });
        //初始化购物车数据
        function Createtable(data) {
            // console.log(data);
            if (data.content.length == 0) {
                $(".empty-cart").show();
            } else {
                $(".cartTabletitle").show();
                $(".foot").show();
                var i = 0;
                var dataLength = data.content.length;
                var meatData, fruitData, vegetableData, foodData;
                for (i = 0; i < dataLength; i++) {
                    if (data.content[i].typeIdParent == 3) {
                        meatData = data['content'][i]['list'];
                    }
                    if (data.content[i].typeIdParent == 2) {
                        vegetableData = data['content'][i]['list'];
                    }
                    if (data.content[i].typeIdParent == 26) {
                        foodData = data['content'][i]['list'];
                    }
                    if (data.content[i].typeIdParent == 1) {
                        fruitData = data['content'][i]['list'];
                    }
                }
                if (meatData == null) {
                    $("#cartTableMeat").hide();
                } else {
                    $("#cartTableMeat").show();
                    jQuery.each(meatData, function(i, val) {
                        var str = "";
                        str += '<tr>';
                        str += '<td class="checkbox"><input class="Meatcheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
                        str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
                        str += '<td class="price">' + val['price'] + '</td>';
                        str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
                        str += '<div class="title-font">' + val['name'] + '</div>';
                        str += '<div class="info-font">' + val['title'] + '</div>';
                        str += '</td>';
                        str += '<td class="count">';
                        str += "<img class='reduce' src='images/minus.svg'/>"
                        str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
                        str += "<img class='add' src='images/pluse.svg'/>";
                        str += '</td>';
                        str += '<td class="subtotal">' + val['price'] + '</td>';
                        str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
                        str += '</tr>';
                        jQuery(".cartTableMeat-tbody").append(str);
                    });
                }
                if (fruitData == null) {
                    $("#cartTableFruit").hide();
                } else {
                    $("#cartTableFruit").show();
                    jQuery.each(fruitData, function(i, val) {
                        var str = "";
                        str += '<tr>';
                        str += '<td class="checkbox"><input class="Fruitcheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
                        str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
                        str += '<td class="price">' + val['price'] + '</td>';
                        str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
                        str += '<div class="title-font">' + val['name'] + '</div>';
                        str += '<div class="info-font">' + val['title'] + '</div>';
                        str += '</td>';
                        str += '<td class="count">';
                        str += "<img class='reduce' src='images/minus.svg'/>"
                        str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
                        str += "<img class='add' src='images/pluse.svg'/>";
                        str += '</td>';
                        str += '<td class="subtotal">' + val['price'] + '</td>';
                        str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
                        str += '</tr>';
                        jQuery(".cartTableFruit-tbody").append(str);
                    });
                }
                if (vegetableData == null) {
                    $("#cartTableVegetable").hide();
                } else {
                    $("#cartTableVegetable").show();
                    jQuery.each(vegetableData, function(i, val) {
                        var str = "";
                        str += '<tr>';
                        str += '<td class="checkbox"><input class="Vegetablecheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
                        str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
                        str += '<td class="price">' + val['price'] + '</td>';
                        str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
                        str += '<div class="title-font">' + val['name'] + '</div>';
                        str += '<div class="info-font">' + val['title'] + '</div>';
                        str += '</td>';
                        str += '<td class="count">';
                        str += "<img class='reduce' src='images/minus.svg'/>"
                        str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
                        str += "<img class='add' src='images/pluse.svg'/>";
                        str += '</td>';
                        str += '<td class="subtotal">' + val['price'] + '</td>';
                        str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
                        str += '</tr>';
                        jQuery(".cartTableVegetable-tbody").append(str);
                    });
                }
                if (foodData == null) {
                    $("#cartTableFood").hide();
                } else {
                    $("#cartTableFood").show()
                    jQuery.each(foodData, function(i, val) {
                        var str = "";
                        str += '<tr>';
                        str += '<td class="checkbox"><input class="Foodcheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
                        str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
                        str += '<td class="price">' + val['price'] + '</td>';
                        str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
                        str += '<div class="title-font">' + val['name'] + '</div>';
                        str += '<div class="info-font">' + val['title'] + '</div>';
                        str += '</td>';
                        str += '<td class="count">';
                        str += "<img class='reduce' src='images/minus.svg'/>"
                        str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
                        str += "<img class='add' src='images/pluse.svg'/>";
                        str += '</td>';
                        str += '<td class="subtotal">' + val['price'] + '</td>';
                        str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
                        str += '</tr>';
                        jQuery(".cartTableFood-tbody").append(str);
                    });
                }
            }
        }
        //table info js end
        //guess info js start
        function CreatelikeTable(data) {
            console.log(data);
            var guessdata = data.content;
            if (guessdata.length == 0) {
                $(".empty-guess").hide();
                $(".guess").hide();
            } else {
                jQuery.each(guessdata, function(i, val) {
                    var str = "";
                    str += '<li class="sale-picture-ul-li" onclick="getProductDetail(' + val["id"] + ')">';
                    str += '<div class="show-picture">';
                    str += '<img class="corner" src="' + val['picture'] + '" />';
                    str += '</div>';
                    str += '<div class="show-font">';
                    str += '<p><span class="title-1">' + val['name'] + '</span></p>';
                    str += '<p><span class="title-2">' + val['title'].substring(0, 30) + '</span></p>';
                    str += '<p><span class="title-price">' + val['price'] + '/' + val['unit'] + '</span></p>';
                    str += '</div>';
                    str += '</li>';
                    jQuery(".sale-picture-ul").append(str);
                });
            }
        }
        //guess info js end
        //JS logic||input  initialize
        setTimeout(function() {
            logicInitialization();
            // $('input').iCheck({
            //     checkboxClass: 'icheckbox_square-red',
            //     radioClass: 'iradio_square-red',
            //     increaseArea: '20%' // optional
            // });//checkbox style
        }, 100);
    } else {
        setTimeout(function() {
            alert("请先登录！");
            window.location.href = "login.html";
        }, 100);
    }
}


//点击商品进入商品详情页面
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
            window.open("detail-buy.html");
        }
    })
}



function getShoppingCartProduct(token) {
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/getGoodsCartList",
        dataType: "json",
        data: {
            "token": token,
        },
        success: function(data) {
            console.log(data);
            setHtmlShoppingCartProduct(data);
        }
    });
}



function setHtmlShoppingCartProduct(data) {
    // cancelDeleteManage();
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


//定向刷新购物车中某一个种类的数据（蔬菜、水果、肉类、粮油等）
function CreateShoppingCartTable(data, typeParentId) { //data：表示的是某一特定种类的数据；typeParentId :表示的是所代表的某一特定种类的数据的Id编号；
    var meatProductData, vegetableProductData, foodProductData, fruitProductData;
    if (typeParentId == 3) {
        meatProductData = data.list;
    }
    if (typeParentId == 2) {
        vegetableProductData = data.list;
    }
    if (typeParentId == 26) {
        foodProductData = data.list;
    }
    if (typeParentId == 1) {
        fruitProductData = data.list;
    }
    if (meatProductData == null) {
        // $("#cartTableMeat").hide();
    } else {
        $("#cartTableMeat").show();
        jQuery(".cartTableMeat-tbody").empty();
        jQuery.each(meatProductData, function(i, val) {
            var str = "";
            str += '<tr>';
            str += '<td class="checkbox"><input class="Meatcheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
            str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
            str += '<td class="price">' + val['price'] + '</td>';
            str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
            str += '<div class="title-font">' + val['name'] + '</div>';
            str += '<div class="info-font">' + val['title'] + '</div>';
            str += '</td>';
            str += '<td class="count">';
            str += "<img class='reduce' src='images/minus.svg'/>"
            str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
            str += "<img class='add' src='images/pluse.svg'/>";
            str += '</td>';
            str += '<td class="subtotal">' + val['price'] + '</td>';
            str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
            str += '</tr>';
            jQuery(".cartTableMeat-tbody").append(str);
        });
    }
    if (fruitProductData == null) {
        // $("#cartTableFruit").hide();
    } else {
        $("#cartTableFruit").show();
        jQuery(".cartTableFruit-tbody").empty();
        jQuery.each(fruitProductData, function(i, val) {
            var str = "";
            str += '<tr>';
            str += '<td class="checkbox"><input class="Fruitcheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
            str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
            str += '<td class="price">' + val['price'] + '</td>';
            str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
            str += '<div class="title-font">' + val['name'] + '</div>';
            str += '<div class="info-font">' + val['title'] + '</div>';
            str += '</td>';
            str += '<td class="count">';
            str += "<img class='reduce' src='images/minus.svg'/>"
            str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
            str += "<img class='add' src='images/pluse.svg'/>";
            str += '</td>';
            str += '<td class="subtotal">' + val['price'] + '</td>';
            str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
            str += '</tr>';
            jQuery(".cartTableFruit-tbody").append(str);
        });
    }
    if (vegetableProductData == null) {
        // $("#cartTableVegetable").hide();
    } else {
        $("#cartTableVegetable").show();
        jQuery(".cartTableVegetable-tbody").empty();
        jQuery.each(vegetableProductData, function(i, val) {
            var str = "";
            str += '<tr>';
            str += '<td class="checkbox"><input class="Vegetablecheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
            str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
            str += '<td class="price">' + val['price'] + '</td>';
            str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
            str += '<div class="title-font">' + val['name'] + '</div>';
            str += '<div class="info-font">' + val['title'] + '</div>';
            str += '</td>';
            str += '<td class="count">';
            str += "<img class='reduce' src='images/minus.svg'/>"
            str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
            str += "<img class='add' src='images/pluse.svg'/>";
            str += '</td>';
            str += '<td class="subtotal">' + val['price'] + '</td>';
            str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
            str += '</tr>';
            jQuery(".cartTableVegetable-tbody").append(str);
        });
    }
    if (foodProductData == null) {
        // $("#cartTableFood").hide();
    } else {
        $("#cartTableFood").show();
        jQuery(".cartTableFood-tbody").empty();
        jQuery.each(foodProductData, function(i, val) {
            var str = "";
            str += '<tr>';
            str += '<td class="checkbox"><input class="Foodcheck check" name="subCheck" value="' + val['goodsId'] + '" type="checkbox"/></td>';
            str += '<td class="goods" onclick="getProductDetail(' + val["goodsId"] + ')"><img src="' + val['picture'] + '" /></td>';
            str += '<td class="price">' + val['price'] + '</td>';
            str += '<td class="goodsinfo" onclick="getProductDetail(' + val["goodsId"] + ')">';
            str += '<div class="title-font">' + val['name'] + '</div>';
            str += '<div class="info-font">' + val['title'] + '</div>';
            str += '</td>';
            str += '<td class="count">';
            str += "<img class='reduce' src='images/minus.svg'/>"
            str += '<input class="count-input" type="text" value="1" disabled="disabled"/>'
            str += "<img class='add' src='images/pluse.svg'/>";
            str += '</td>';
            str += '<td class="subtotal">' + val['price'] + '</td>';
            str += '<td class="option" ><a onclick="deleteShoppingCartGood(' + val["goodsId"] + ',' + val["typeIdParent"] + ')">删除</a></td>';
            str += '</tr>';
            jQuery(".cartTableFood-tbody").append(str);
        });
    }

}





//获取所有勾选的商品id 适用于 “结算” 按钮的函数调用
function getAllCheckedProduct() {
    var goodsCheckedArray = [];
    var goodsId = "";
    var goodsValue = "";
    $('input:checkbox[name=subCheck]:checked').each(function(i) {
        var goodsObj = { "goodsID": 0, "num": 0 };
        console.log("执行第" + (i + 1) + "次");
        goodsId = $(this).val(); //每次要重新创建一个新的对象，覆盖之前旧的对象。
        console.log("第" + (i + 1) + "个商品id :" + goodsId);
        goodsValue = $(this).parent().next().next().next().next().find("input").val();
        console.log("第" + (i + 1) + "个商品数量 :" + goodsValue);
        goodsObj.goodsID = goodsId;
        goodsObj.num = goodsValue;
        goodsCheckedArray.push(goodsObj);
        delete goodsObj; //此处代码是为了将之前的goodsId对象清空，将之前的对象进行覆盖。
    });
    return goodsCheckedArray;
}



//获取所有勾选的商品id
function getAllchecked(type) {
    var goodsId = "";
    var goodsValue = "";
    $('input:checkbox[name=subCheck]:checked').each(function(i) {
        if (0 == i) {
            goodsId = $(this).val();
            goodsValue = $(this).parent().next().next().next().find("input").val();
        } else {
            goodsId += ("," + $(this).val());
            goodsValue += ("," + $(this).parent().next().next().next().find("input").val());

        }
    });
    var goodsIdArray = goodsId.split(",");
    var goodsValueArray = goodsValue.split(",");
    console.log(goodsIdArray);
    console.log(goodsValueArray);
    if (type == 1) {
        return goodsIdArray;
    }
    if (type == 0) {
        return goodsId;
    }
}




function getShoppingCartData(typeParentId) {
    var token = "";
    token = localStorage.getItem("token");
    console.log("进入getShoppingCartData函数中");
    $.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/getGoodsCartList",
        dataType: "json",
        data: {
            "token": token
        },
        success: function(data) {
            for (var j = 0; j < data.content.length; j++) {
                if (data.content[j].typeIdParent == typeParentId) {
                    CreateShoppingCartTable(data.content[j], typeParentId);
                    break;
                }
            }
        }
    });
}


function getTypeParentArrayId() {
    var datacontent;
    $.ajax({
        type: "post",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsType/getParentGoodsTypeList",
        data: {},
        dataType: 'json',
        success: function(data) {
            datacontent = data.content;
        }
    });
    return datacontent;
}


function refreshProductData(typeParentId) {
    var arr;
    console.log('进入refreshProductData函数中');
    arr = getTypeParentArrayId();
    console.log("refreshProductData:");
    console.log(arr);
    for (var i = 0; i < arr.length; i++) {
        if (typeParentId == arr[i].id) {
            getShoppingCartData(typeParentId);
        }
    }

}



//删除购物车商品
function deleteShoppingCartGood(id, typeIdParent) {
    var userToken = "";
    userToken = localStorage.getItem("token");
    console.log(userToken);
    console.log("进入删除商品函数");
    var conf = confirm("你确定要删除该商品吗?");
    if (conf) {
        var data;
        //利用批量删除删除购物车商品
        console.log("删除购物车商品id:" + id);
        jQuery.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/deleteGoodsCart",
            dataType: "json",
            data: {
                "token": userToken,
                "goodsId": id
            },
            success: function() {
                console.log(userToken);
                refreshProductData(typeIdParent);
            }
        });
    }
}


//批量删除购物车选中的物品
function deleteSelectShoppingCartGood() {
    var goodsIds = getAllchecked(0);
    var userToken = "";
    userToken = localStorage.getItem("token");
    console.log(userToken);
    console.log("进入删除商品函数");
    var conf = confirm("你确定要删除已选中的商品吗？");
    if (conf) {
        var data;
        //利用批量删除删除购物车商品
        console.log("批量删除购物车商品列表:" + goodsIds);
        jQuery.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/deleteGoodsCartBatch",
            dataType: "json",
            data: {
                "token": userToken,
                "goodsIds": goodsIds
            },
            success: function(data) {
                console.log(userToken);
                window.location.reload();
            }
        });
    }
}


//----------------load end---------------------------

//编辑按钮
function startEdit() {
    $("#pay-bottom").hide();
    $("#delete-bottom").show();
    $("#edit-top").hide();
    $("#total-bottom").hide();
    $("#finish-top").show();
}
//完成按钮
function finishEdit() {
    $("#pay-bottom").show();
    $("#delete-bottom").hide();
    $("#edit-top").show();
    $("#total-bottom").show();
    $("#finish-top").hide();
}


//结算函数
function paySelect() {
    var goodsArray = getAllCheckedProduct();
    console.log("返回值:");
    console.log(goodsArray)
    var token = localStorage.getItem("token");
    console.log(token);
    var orderInfo = JSON.stringify(goodsArray);
    localStorage.setItem("orderInfo", orderInfo);
    window.location.assign("order-confirm.html");
}

//删除函数
function deleteSelect() {
    var goodsId = getAllchecked(0);
    console.log(goodsId);
    var token = "";
    token = sessionStorage.getItem("token");
    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/deleteGoodsCartBatch",
        dataType: "json",
        data: {
            "token": token,
            "goodsId": goodsId
        },
        success: function(data) {
            alert("删除成功");
        },
        error: function(data) {
            $(".weui_dialog_bd").text('网络错误');
            $("#dialog2").show();
        }
    });
}
//退出函数
function signOut() {
    localStorage.clear();
    window.location.reload();
}


function tishiInfo() {
    console.log("进入未登录提示信息js");
    bootbox.alert({
        size: 'middle', //尺寸大小   middle  large
        message: "您还未登录,去首页看看吧",
        callback: function(result) {
            if (result) {
                window.location.href = "index.html";
            }
        }
    });
}
