/**
 * 个人信息界面
 * 
 */
var orderStatus = ["全部", "未付款", "未发货", "已发货", "已收货", "退货中", "已退货", "交易成功"];

function getPersonInfo() {
    var token = getToken();
    var data = getUserInfo(token);

    jQuery("#username").text(data['content']['name']);
    jQuery("#score").text(data['content']['phone']);
    jQuery("#person_portrait").attr("src", data['content']['portrait']);
    //设置header中的用户名
    jQuery("#header_username").text(data['content']['name']);
    var data = getNotReaderMeaasge(token);
    if (data['content']['messageCount'] != 0) {
        //设置小红点
        jQuery("#header-message").removeClass("icon-userMessages");
        jQuery("#header-message").addClass("icon-userMessages-red");
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
            else {
                jumpToLogin();
            }
        },
    });
    return dataInfo;
}
/**
 * 获取未读消息
 */
function getNotReaderMeaasge(token) {
    var dataInfo;
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/message/getNoReadMessageCount",
        data: {
            token: token,
        },
        dataType: "json",
        success: function (data) {
            if (data['result'] == 1) {
                dataInfo = data;
            }
            else {
                jumpToLogin();
            }
        },
    });
    return dataInfo;

}
function reload(params) {

}
/**
 * 返回token
 */
function getToken() {
    var token = localStorage.getItem("token");
    //如果没有获取到token，则跳转到登陆界面
    if (jQuery.trim(token) == "") {
        jumpToLogin();
    }
    else {
        return token;
    }

}



/**
 * 获取搜索信息
 */
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
$(document).keypress(function(e) {  
        // 回车键事件  
       if(e.which == 13) {  
            jQuery(".search-btn").click();  
       }  
   }); 




/**
 * 编辑个人信息
 * 跳转
 */
function jumpToPersonInfo() {
    window.location.href = "editUserInfo.html";
}
/**
 * 我的信息
 */
function jumpToMyMessage() {
    window.location.href = "infoIndex.html";
}
/**
 * 全部订单
 */
function jumpToAllOrder() {
    window.location.href = "allOrder.html";
}
/**
 * 待发货
 */
function jumpToPayedInfo() {
    window.location.href = "payedInfo.html";
}
/**
 * 待付款
 */
function jumpToWaitPay() {
    window.location.href = "waitPay.html";
}
/**
 * 待收货
 */
function jumpToWaitSend() {
    window.location.href = "waitSend.html";
}
/***
 * 待评价
 */
function jumpToEvaluateInfo() {
    window.location.href = "evaluateInfo.html";
}
/**
 * 地址管理
 */
function jumpToaddressInfo() {
    window.location.href = "addressInfo.html";
}
/**
 * 我的消息
 */
function myMessage(pageNum) {
	$('#detail').hide();
    getPersonInfo();
    var meaaageType = ['', '特价消息', '限时抢购', '商品发货', '热卖产品', '新品上市', '异地登录', '退货成功', '退货失败'];
    //消息打开的新的界面，只要不是空，就会有一个链接
    var messageHref = ['', '../onsale.html', '../onsale.html', '', '../hotsale.html', '新品上市', '', '', ''];
    var message_picture = ['', 'special_price.png', 'time.png', 'send.png', 'hot.png', 'new_product.png', 'other_place.png', 'success.png', 'fail.png'];
    var message_status = ['未读', '已读'];
    var message_status_class = ["message_status_not_read", "message_status_read"];
    var pageSize = 5;
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_message") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_message") - 1;
    }

    sessionStorage.setItem("pageNum_message", pageNum);

    var token = getToken();

    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/message/getMessageList",
        data: {
            token: token,
            pageSize: pageSize,
            pageNum: pageNum,
        },
        dataType: "json",
        success: function (data) {
            if (data['result'] == 1 && data['content'].length != 0) {
                jQuery(".info-ul").empty();

                jQuery.each(data['content'], function (i, val) {
                    //比较时间
                    var timestring = "";

                    var newDate = new Date();
                    newDate.setTime(val['createTime']);
                    timestring = newDate.toLocaleDateString();
                    var date = new Date();
                    var cdata = timestring.split("/");
                    if (cdata[2] == date.getDate()) {
                        timestring = "今天";
                    } else if (cdata[2] < date.getDate() && cdata[2] > date.getDate() - 2) {
                        timestring = "昨天";
                    }

                    var string = "<li>"
                        + "<div>"
                        + '  <div class="info_img_div"><img src="../images/personInfo/' + message_picture[val['markId']] + '" style="width:60px;height:60px;"/></div>'
                        + '  <div class="info_font_div">'
                        + '  <div class="title_time">'
                        + '   <div class="info_title">';
                    if (messageHref[val['markId']] != "") {
                        string = string + "<a href='" + messageHref[val['markId']] + "'>"
                            + meaaageType[val['markId']]
                            + "</a>"

                    }
                    else {
                        string = string + meaaageType[val['markId']] //特价消息
                    }
                    string = string
                        + '</div>'
                        + '  <div class="time">'
                        + timestring//"今天"
                        + '</div>'
                        + '</div>'
                        + '<div class="info_content"><span>'
                        + val['message']//台湾珍珠芒果正在特价
                        + '</span><span class="' + message_status_class[val['status']] + '">'
                        + message_status[val['status']]
                        + '</span>'
                        + '</div>'
                        + '</div>'
                        + '</div>'
                        + '</li>';
                    jQuery(".info-ul").append(string);
                });
            }
            else if (pageNum == 1) {
                var noOrderString = "<div class='table_div'><div>"
                    + '<div class="no_order_div">'
                    + '<img src="../images/noOrder.png" />'
                    + '<div class="no_order_font_tip">您没有任何消息</div>'
                    + '</div>'
                    + '</div></div>';
                jQuery(".info-ul").after(noOrderString);
                jQuery(".info-ul").remove();
            }
        },
    });
}
/**
 * 全部订单
 * payedInfo.html
 */
function allOrder(pageNum) {
	$('#detail').hide();
	$('#comment_submit').hide();
    var orderStatus = ["全部", "未付款", "未发货", "已发货", "已收货", "退货中", "已退货", "交易成功"];
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_allOrder") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_allOrder") - 1;
    }

    sessionStorage.setItem("pageNum_allOrder", pageNum);
    var token = getToken();
    
    var status = 0;
    var pageSize = 4;
    var data = getOrder(status, pageSize, pageNum, token);
    
    if (data['result'] == 1 && data['content']['orderList'].length != 0) {
        jQuery(".content_tbody").empty();
        jQuery(".more_order").remove();
        jQuery.each(data['content']['orderList'], function (i, val) {
            var string = "<tr class='order_tr'>"
            	+ '<td><span class="product_description">'//id
                + val['orderId']
                + '</span></td>'
                
                + '<td><span class="product_description">'
                + val['amount']
                + '</span></td>'
                
                
                + '<td><span class="product_description">'
                + val['message']
                + '</span></td>'
                
                + '<td>'
                + '<span  class="fenhang product_description">'
                + orderStatus[val['status']]      //买家已付款
                + '</span></td><td class="left-td">';
            
            
            string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="getOrderDetail('+"'"
                + val['orderId']+"'"+","+val['status']
                + ')">详情</a>';
            
            if (val['status'] == 1) {
                //未付款
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="pay('+"'"
                + val['orderId']+"'"
                + ')">付款</a>';
            }
          //  else if (val['status'] == 2 || val['status'] == 3 || val['status'] == 4 || val['status'] == 7) {
                else if (val['status'] == 7) {//只有交易成功才能评价
            //    string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="addComment('+"'"
            //    + val['orderId']+"'"
             //   + ')">评论</a>';
            }
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="deleteOrder('+"'"
            + val['orderId']+"'"
            + ')">删除</a>';
                + '</td>';

            
            string += '</tr >';
            jQuery(".content_tbody").append(string);
        });
        var table = document.getElementById("order_table");//根据table的 id 属性值获得对象  
        var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
        for (var i = 0; i < rows.length; i++) {
            if (i % 2 == 0) {
                rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
            }
            else {
                rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
            }
        }
    }
    else if (pageNum == 1) {
        set_no_order_tip();
    }
    else {
        set_more_order();
    }
}
/**
 * 获取商品详情
 */
function getProductDetail(id) {
    //	alert(id);
    jQuery.ajax({
        type: "post",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsDetail",
        data: {
            goodsId: id,
        },
        dataType: "json",
        success: function (data) {
            var str = JSON.stringify(data);
            sessionStorage.setItem("productDetail", str);
            window.location.href = "../detail-buy.html";
        }
    })
}


/**
 * 已付款
 * 待发货
 * payedInfo.html
 */
function paiedOrder(pageNum) {
	$('#detail').hide();
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_paiedOrder") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_paiedOrder") - 1;
    }

    sessionStorage.setItem("pageNum_paiedOrder", pageNum);
    var token = getToken();
    var status = 2;
    var pageSize = 4;
    var data = getOrder(status, pageSize, pageNum, token);
    if (data['result'] == 1 && data['content']['orderList'].length != 0) {
        jQuery(".content_tbody").empty();
        jQuery(".more_order").remove();
        jQuery.each(data['content']['orderList'], function (i, val) {

        	var string = "<tr class='order_tr'>"
            	+ '<td><span class="product_description">'//id
                + val['orderId']
                + '</span></td>'
                
                + '<td><span class="product_description">'
                + val['amount']
                + '</span></td>'
                
                
                + '<td><span class="product_description">'
                + val['message']
                + '</span></td>'
                
                + '<td>'
                + '<span  class="fenhang product_description">'
                + orderStatus[val['status']]      //买家已付款
                + '</span></td><td class="left-td">';
            
            
            string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="getOrderDetail('+"'"
                + val['orderId']+"'"
                + ')">详情</a>';
 
            if (val['status'] == 1) {
                //未付款
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="pay('+"'"
                + val['orderId']+"'"
                + ')">付款</a>';
            }
          else if (val['status'] == 7) {//只有交易成功才能评价
               // string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
                //    + val['id']
                //    + ')">评价</a>';
            }
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="deleteOrder('+"'"
            + val['orderId']+"'"
            + ')">删除</a>';
                + '</td>';
            string += '</tr >';
            jQuery(".content_tbody").append(string);
        });
        var table = document.getElementById("order_table");//根据table的 id 属性值获得对象  
        var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
        for (var i = 0; i < rows.length; i++) {
            if (i % 2 == 0) {
                rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
            }
            else {
                rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
            }
        }
    }
    else if (pageNum == 1) {
        set_no_order_tip();
    }
    else {
        set_more_order();
    }
}
/**
 * 待收货
 * waitPay.html
 */
function unget() {

}
/**
 * 待付款
 * waitPay.html
 */
function unpaied(pageNum) {
	$('#detail').hide();
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_unpaied") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_unpaied") - 1;
    }

    sessionStorage.setItem("pageNum_unpaied", pageNum);
    var token = getToken();
    var status = 1;
    var pageSize = 4;
    var data = getOrder(status, pageSize, pageNum, token);
    if (data['result'] == 1 && data['content']['orderList'].length != 0) {
        jQuery(".content_tbody").empty();
        jQuery(".more_order").remove();
        jQuery.each(data['content']['orderList'], function (i, val) {
            console.log(val['status']);
            var string = "<tr class='order_tr'>"
            	+ '<td><span class="product_description">'//id
                + val['orderId']
                + '</span></td>'
                
                + '<td><span class="product_description">'
                + val['amount']
                + '</span></td>'
                
                
                + '<td><span class="product_description">'
                + val['message']
                + '</span></td>'
                
                + '<td>'
                + '<span  class="fenhang product_description">'
                + orderStatus[val['status']]      //买家已付款
                + '</span></td><td class="left-td">';
            
            
            string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="getOrderDetail('+"'"
                + val['orderId']+"'"
                + ')">详情</a>';
            
            if (val['status'] == 1) {
                //未付款
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="pay('+"'"
                + val['orderId']+"'"
                + ')">付款</a>';
            }
           else if (val['status'] == 7) {//只有交易成功才能评价
              //  string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
               //     + val['id']
                //    + ')">评价</a>';
            }
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="deleteOrder('+"'"
            + val['orderId']+"'"
            + ')">删除</a>';
                + '</td>';
            string += '</tr >';
            jQuery(".content_tbody").append(string);
        });
        var table = document.getElementById("order_table");//根据table的 id 属性值获得对象  
        var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
        for (var i = 0; i < rows.length; i++) {
            if (i % 2 == 0) {
                rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
            }
            else {
                rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
            }
        }
    }
    else if (pageNum == 1) {
        set_no_order_tip();
    }
    else {
        set_more_order();
    }

}
/**
 * 待收获
 * waitSend.html
 */
function waitReceive(pageNum) {
	$('#detail').hide();
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_waitReceive") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_waitReceive") - 1;
    }
    sessionStorage.setItem("pageNum_waitReceive", pageNum);
    var token = getToken();
    var status = 3;
    var pageSize = 4;
    var data = getOrder(status, pageSize, pageNum, token);
    if (data['result'] == 1 && data['content']['orderList'].length != 0) {
        jQuery(".content_tbody").empty();
        jQuery(".more_order").remove();
        jQuery.each(data['content']['orderList'], function (i, val) {
            console.log(val['status']);
            var string = "<tr class='order_tr'>"
            	+ '<td><span class="product_description">'//id
                + val['orderId']
                + '</span></td>'
                
                + '<td><span class="product_description">'
                + val['amount']
                + '</span></td>'
                
                
                + '<td><span class="product_description">'
                + val['message']
                + '</span></td>'
                
                + '<td>'
                + '<span  class="fenhang product_description">'
                + orderStatus[val['status']]      //买家已付款
                + '</span></td><td class="left-td">';
            
            
            string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="getOrderDetail('+"'"
                + val['orderId']+"'"
                + ')">详情</a>';
            
            if (val['status'] == 1) {
                //未付款
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="pay('+"'"
                + val['orderId']+"'"
                + ')">付款</a>';
            }
         else if (val['status'] == 7) {//只有交易成功才能评价
               // string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
                //    + val['id']
                //    + ')">评价</a>';
            }
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="deleteOrder('+"'"
            + val['orderId']+"'"
            + ')">删除</a>';
                + '</td>';
            string += '</tr >';
            jQuery(".content_tbody").append(string);
        });
        var table = document.getElementById("order_table");//根据table的 id 属性值获得对象  
        var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
        for (var i = 0; i < rows.length; i++) {
            if (i % 2 == 0) {
                rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
            }
            else {
                rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
            }
        }
    }
    else if (pageNum == 1) {
        set_no_order_tip();
    }
    else {
        set_more_order();
    }

}
/**
 * 待评价
 * evaluateInfo.html
 */
function comment(pageNum) {
	$('#detail').hide();
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_comment") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_comment") - 1;
    }
    sessionStorage.setItem("pageNum_comment", pageNum);
    var token = getToken();
    var status = 7;
    var pageSize = 4;
    var data = getOrder(status, pageSize, pageNum, token);
    if (data['result'] == 1 && data['content']['orderList'].length != 0) {
        jQuery(".content_tbody").empty();
        jQuery(".more_order").remove();
        jQuery.each(data['content']['orderList'], function (i, val) {
            console.log(val['status']);
            var string = "<tr class='order_tr'>"
            	+ '<td><span class="product_description">'//id
                + val['orderId']
                + '</span></td>'
                
                + '<td><span class="product_description">'
                + val['amount']
                + '</span></td>'
                
                
                + '<td><span class="product_description">'
                + val['message']
                + '</span></td>'
                
                + '<td>'
                + '<span  class="fenhang product_description">'
                + orderStatus[val['status']]      //买家已付款
                + '</span></td><td class="left-td">';
            
            
            string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="getOrderDetail('+"'"
            + val['orderId']+"'"+","+val['status']
            + ')">详情</a>';
            
            if (val['status'] == 1) {
                //未付款
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="pay('+"'"
                + val['orderId']+"'"
                + ')">付款</a>';
            }
          else if (val['status'] == 7) {//只有交易成功才能评价
               // string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
                //    + val['id']
                //    + ')">评价</a>';
        	  
        	// string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
              //    + val['id']
              //    + ')">评价</a>';
            }
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="deleteOrder('+"'"
            + val['orderId']+"'"
            + ')">删除</a>';
                + '</td>';
            string += '</tr >';
            jQuery(".content_tbody").append(string);
        });
        var table = document.getElementById("order_table");//根据table的 id 属性值获得对象  
        var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
        for (var i = 0; i < rows.length; i++) {
            if (i % 2 == 0) {
                rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
            }
            else {
                rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
            }
        }
    }
    else if (pageNum == 1) {
        set_no_order_tip();
    }
    else {
        set_more_order();
    }
}
/**
 * 没有订单的提示
 */
function set_no_order_tip() {
    var length1 = jQuery(".no_order_div").length;
    var length = jQuery(".more_order").length;
    if (length1 == 0 && length == 0) {
        var noOrderString = "<div>"
            + '<div class="no_order_div">'
            + '<img src="../images/noOrder.png" />'
            + '<div class="no_order_font_tip">您还没有相关订单</div>'
            + '</div>'
            + '</div>';
        jQuery("#order_table").after(noOrderString);

    }
}
/**
 * 没有更多订单的提示
 */
function set_more_order() {
    var length1 = jQuery(".no_order_div").length;
    var length = jQuery(".more_order").length;
    if (length1 == 0 && length == 0) {
        var noOrderString = "<div class='more_order'>"
            + '<div class="no_order_div">'
            + '<img src="../images/noOrder.png" />'
            + '<div class="no_order_font_tip">没有更多相关订单</div>'
            + '</div>'
            + '</div>';
        jQuery(".content_tbody").empty();
        jQuery("#order_table").after(noOrderString);
    }
}
/**
 * 地址管理
 * addressInfo.html
 */
function addressInfo(pageNum) {
	$('#detail').hide();
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_addressInfo") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_addressInfo") - 1;
    }
    sessionStorage.setItem("pageNum_addressInfo", pageNum);
    var token = getToken();
    var pageSize = 4;
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/address/getAddressList",
        data: {
            token: token,
            pageSize: pageSize,
            pageNum: pageNum,
        },
        dataType: "json",
        success: function (data) {
            //  orderInfoList = data;
            if (data['result'] == 1 && data['content'].length != 0) {
                jQuery(".content_tbody").empty();
                jQuery(".more_order").remove();
                jQuery.each(data['content'], function (i, val) {

                    var string = " <tr class='address_tr'>"
                        + '<td>'
                        + val['name']
                        + '</td>'
                        + ' <td><span class="province">'
                        + val['province'] + val['city'] + val['county']
                        + '</span>'
                        + '<span >'
                        + val['detailAddress']
                        + '</span></td>'
                        + '<td>'
                        + val['phone']
                        + '</td>'
                        + '<td><a class="black_a"  href="javascript:void(0);" onclick="getAddressInfo(' + val['id'] + ')">编辑</a>/ <a class="black_a" href="javascript:void(0);" onclick="deleteAddress(' + val['id'] + ')">删除</a>'
                        + '</td>';
                    //查看是否是默认地址
                    if (val['isDefaultAddress'] == '2') {
                        string = string + "<td>" + "<button class='set_default'>默认地址</button></td>" + '</tr >';

                    }
                    else {
                        string = string + "<td class='not_default_td'>" + "<button class='set_default not_default' data-id=" + val['id'] + " onclick='updateAddressInfo(this)'>设为默认</button></td>" + '</tr >';
                    }
                    jQuery(".content_tbody").append(string);
                });
                var table = document.getElementById("address_table");//根据table的 id 属性值获得对象  
                var rows = jQuery(".address_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
                for (var i = 0; i < rows.length; i++) {
                    if (i % 2 == 0) {
                        rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
                    }
                    else {
                        rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
                    }
                }
            }
            else if (pageNum == 1) {
                var length1 = jQuery(".no_order_div").length;
    var length = jQuery(".more_order").length;
                if (length1 == 0 && length == 0) {
                    var noOrderString = "<div class='no_order_div'>"
                        + '<div class="no_order_div">'
                        + '<img src="../images/pluse.svg" style="width:60px;" onclick="jumpToAddAdddress()"/>'
                        + '<div class="no_order_font_tip">您还没有添加地址</div>'
                        + '</div>'
                        + '</div>';
                    jQuery("#address_table").after(noOrderString);
                }
            }
              else {
                    var length1 = jQuery(".no_order_div").length;
                    var length = jQuery(".more_order").length;
                    if (length1 == 0 && length == 0) {
                        var noOrderString = "<div class='more_order'>"
                            + '<div class="no_order_div">'
                            + '<img src="../images/noOrder.png" />'
                            + '<div class="no_order_font_tip">没有更多地址</div>'
                            + '</div>'
                            + '</div>';
                        jQuery(".content_tbody").empty();
                        jQuery("#address_table").after(noOrderString);
                    }
                }
        },
    });
}

function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().substr(0, 17)
}
/**
 * 获取订单的接口
 *  status：0全部 1未付款 2未发货 3已发货 4已收货 5退货中 6已退货
 */
function getOrder(status, pageSize, pageNum, token) {
    var orderInfoList;
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/order/getOrderList",
        data: {
            token: token,
            status: status,
            pageSize: pageSize,
            pageNum: pageNum,
        },
        dataType: "json",
        success: function (data) {
            orderInfoList = data;
        },
    });
    return orderInfoList;
}


/**
 * 获取指定订单详情
 */
/*function getOrderDetail(id) {
    getPersonInfo();
    if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_allOrder") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_allOrder") - 1;
    }

    sessionStorage.setItem("pageNum_allOrder", pageNum);
    var token = getToken();
    var status = 0;
    var pageSize = 4;
    var data = getOrder(status, pageSize, pageNum, token);
    
    if (data['result'] == 1 && data['content']['orderList'].length != 0) {
        jQuery(".content_tbody").empty();
        jQuery(".more_order").remove();
        jQuery.each(data['content']['orderList'], function (i, val) {
            var string = "<tr class='order_tr'>"
            	+ '<td><span class="product_description">'//id
                + val['orderId']
                + '</span></td>'
                
                + '<td><span class="product_description">'
                + val['amount']
                + '</span></td>'
                
                
                + '<td><span class="product_description">'
                + val['message']
                + '</span></td>'
                
                + '<td>'
                + '<span  class="fenhang product_description">'
                + orderStatus[val['status']]      //买家已付款
                + '</span></td><td class="left-td">';
            
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="getOrderDetail(' +
            +val['orderId']
            + ')">详情</a>';
            
            if (val['status'] == 1) {
                //未付款
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
                    + val['orderId']
                    + ')">付款</a>';
            }
          //  else if (val['status'] == 2 || val['status'] == 3 || val['status'] == 4 || val['status'] == 7) {
                else if (val['status'] == 7) {//只有交易成功才能评价
                string += '<a href="javascript:void()" class="tuikuan_a product_description" onclick="rejectedOrderStatus('
                    + val['orderId']
                    + ')">评价</a>';
            }
            string += '<a href="javascript:void()" class="product_description tuikuan_a" onclick="deleteOrder(' +
                +val['orderId']
                + ')">删除</a>'
                + '</td>';

            
            string += '</tr >';
            jQuery(".content_tbody").append(string);
        });
        var table = document.getElementById("order_table");//根据table的 id 属性值获得对象  
        var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
        for (var i = 0; i < rows.length; i++) {
            if (i % 2 == 0) {
                rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
            }
            else {
                rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
            }
        }
    }
    else if (pageNum == 1) {
        set_no_order_tip();
    }
    else {
        set_more_order();
    }
}

*/


/*获取订单详情
 * 
 */
function getOrderDetail(id, status) {
	$('#detail').hide();
	$('#comment_submit').hide();
	
    /*if (pageNum == 0) {
        pageNum = sessionStorage.getItem("pageNum_allOrder") * 1 + 1 * 1;
    }
    else if (pageNum == -1) {
        pageNum = sessionStorage.getItem("pageNum_allOrder") - 1;
    }

    sessionStorage.setItem("pageNum_allOrder", pageNum);*/
    var token = getToken();
    var pageSize = 4;
    
    
    var orderInfoList;
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/order/getOrderDetailList",
        data: {
            orderId: id
        },
        
        
        //dataType: "json",
        //dataType: "text",
        
        success: function (data) {
            orderInfoList = data;
           // window.location.href = "../personInfo/orderDetail.html";
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	alert(XMLHttpRequest.responseText); 
        	alert(XMLHttpRequest.status);
        	alert(XMLHttpRequest.readyState);
        	alert(textStatus); // parser error;

           // window.location.href = "../personInfo/orderDetail.html";
        }
    });
    /*jQuery.each(orderInfoList['orderDetailList'], function (i, val) {
    	alert("val: "+val['goodsId']);
    });*/
    var string="";
    jQuery.each(orderInfoList, function (i, val) {
    	string = string + "<tr class='order_tr'>"
    		+ '<td><span class="product_description">'
            + val['orderDetail']['orderId']
            + '</span></td>'
            + '<td><img src="'
            + val['goods']['picture']
            + '" style="width:100px;height:auto" alt="商品图片" onclick="getProductDetail(' + val['goods']['id'] + ')"/></td>'
            + '       <td><span class="fenhang">'
            + "<a href='javascript:void(0)' onclick='getProductDetail(" + val['goods']['id'] + ")' class='product_description'>"
            + val['goods']['name']         //酸软味甜奶油草莓
            + '</a></span>'
            + '<span class="fenhang jianju product_description">'
            + val['goods']['title']
            + '</span></td>'
            + '<td><span class="product_description">'
            + val['orderDetail']['num']
            + '</span></td>'
            + '<td><span class="product_description">'
            + val['orderDetail']['price']
            + '</span></td>'
            +'<td>';
            
        if (status == 7) {//只有交易成功才能评价
        	console.log("status: "+status);
        	console.log("goods: "+val['goods']['id']);
        	console.log("orderId: "+id);
            string += "<a href='javascript:void()' class='tuikuan_a product_description'"+ 'onclick="addComments('
            +"'"+ id+"'"+","
            + "'"+val['goods']['id']+"'"
            + ')">评论</a>'
            +'</td>';
        }
        else{
        	console.log("status!= 7"+status);
        }
    	string += '</tr >';
       
    	});
    
    
    console.log("string: "+string);
	jQuery(".content_tbody").empty();
	$('#all').hide();
	$('#waitP').hide();
	$('#waitS').hide();
	$('#payed').hide();
	$('#evaluate').hide();
    jQuery(".content_tbody").append(string);
   /* if(status == 7){
    	$('#detail_operation').show();
    }
    else{
    	$('#detail_operation').hide();
    }*/
    $('#detail').show();
    
    var table = document.getElementById("detail");//根据table的 id 属性值获得对象  
    var rows = jQuery(".order_tr") //table.getElementsByTagName("tr");//获取table类型的tr元素的列表  
    for (var i = 0; i < rows.length; i++) {
        if (i % 2 == 0) {
            rows[i].style.backgroundColor = "White";//偶数行时背景色为#EAF2D3  
        }
        else {
            rows[i].style.backgroundColor = "#e0eeee";//单数行时背景色为white  
        }
    }
    return orderInfoList;
}


/**
 * 跳转到登陆界面的函数
 */
function jumpToLogin() {
    window.location.href = "../login.html";
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
 * 点击用户重新加载当前界面
 */
function reload() {
    window.location.reload();
}
/**
 * 跳转到新增地址的界面
 */
function jumpToAddAdddress() {
    window.location.href = "addAddress.html";
}

/**付款
 * 
 */

//结算函数
function pay(id) {
   var token=localStorage.getItem("token");
   console.log(token);
   var orderId=id;
   $.ajax({
       type:"post",
       url:"http://127.0.0.1:8080/SmartyAgriculture/interface/order/getOrderMaster",
       data:{
       "token":token,
       "orderId":orderId
   },
       dataType:"JSON",
       success:function (data){ 
           console.log(data['content']['orderView']);
           var str=JSON.stringify(data['content']['orderView']);
           localStorage.setItem("orderPay",str);
           alert("sucess: "+str);
       },
       error: function (XMLHttpRequest, textStatus, errorThrown) {
           alert(XMLHttpRequest.responseText); 
   	       alert(XMLHttpRequest.status);
   	       alert(XMLHttpRequest.readyState);
   	       alert(textStatus); // parser error;

      // window.location.href = "../personInfo/orderDetail.html";
   }
   });
   window.open("../pay.html");
}

/**
 * 退货
 */
function rejectedOrderStatus(id) {
    var flag = updateOrderStatus(5, id);
    if (flag == 1) {
        //退货成功
        tip("退货申请提交成功");
        setTimeout(function () { window.location.reload(); }, 3000);

    }
}
/**
 * 修改订单状态
 */
function updateOrderStatus(orderStatus, id) {
    var token = getToken();
    var flag;
    jQuery.ajax({
        type: "post",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/order/changeOrderStatus",
        data: {
            token: token,
            status: orderStatus,
            id: id
        },
        dataType: "JSON",
        success: function (data) {
            if (data['result'] == 1) {
                flag = 1;
            }
        }
    });
    return flag;
}
/**
 * 删除订单
 */
function deleteOrder(id) {
    var token = getToken();
    jQuery.ajax({
        type: "post",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/order/changeOrderStatus",
        data: {
            token: token,
            is_show: 0,
            id: id
        },
        dataType: "JSON",
        success: function (data) {
            if (data['result'] == 1) {
                tip("订单删除成功");
                setTimeout(function () { window.location.reload(); }, 3000);
            }
        }
    });
}


/**
 * 评论订单
 */

function addComment(orderId, goodsId, content, Level, isAnonymous, serviceMark ,fileData) {
    var token = getToken();
    console.log("token: "+token);
    console.log("file: "+fileData);
    jQuery.ajax({
        type: "post",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/addComments",
        data: {
            token: token,
            is_show: 0, 
            orderId: orderId,
            content: content,
            goodsId: goodsId,
            Level: Level,
            isAnonymous: isAnonymous,
            serviceMark: serviceMark,
            
            cache : false,
            //data : fileData,
            processData : false,
            contentType : false
        },
        dataType: "JSON",
        success: function (data) {
            if (data['result'] == 1) {
                tip("评论成功");
                setTimeout(function () { window.location.reload(); }, 3000);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        	alert(XMLHttpRequest.responseText); 
        	alert(XMLHttpRequest.status);
        	alert(XMLHttpRequest.readyState);
        	alert(textStatus); // parser error;

           // window.location.href = "../personInfo/orderDetail.html";
        }
    });
    $('#comment_submit').hide();
    $('#comment_file').hide();
}

function addComments(orderId, goodsId){
	
	console.log(orderId,goodsId);
	$('#comment_submit').show();
	$('#comment_file').show();
	var btn = document.getElementById('comment_btn'); 
    btn.onclick = function (){ 
    	
    	/*
    	//获取文件  
        var file = $("#fileupload").find("input")[0].files[0];  
      
        //创建读取文件的对象  
        var reader = new FileReader();  
      
        //创建文件读取相关的变量  
        var imgFile;  
      
        //为文件读取成功设置事件  
        reader.onload=function(e) {  
            imgFile = e.target.result;  
            $("#imgContent").attr('src', imgFile);  
        };  
      
        //正式读取文件  
        reader.readAsDataURL(file);
        */
    	
    	
    	var content = document.getElementById("content").value;
    	var isChecked = $('#isAnonymous').prop('checked');
    	if(isChecked){
    		var isAnonymous = 1;
    	}
    	else{
    		var isAnonymous = 0;
    	}
    	var isAnonymous = 1;
    	
    	
    	
    	var Level = $('input:radio[name="list"]:checked').val();
    	var serviceMark = 100;
    	
    	/*var file = document.querySelector('input[type=file]').files[0];
    	if (file) {
    		alert("file: "+file);
    	    //reader.readAsDataURL(file);
    	    
    	  }
    	else{
    		alert("no file");
    	}*/
    	
    	/*var fileData = new FormData();
    	fileData.append('file', file);
    	addComment(orderId, goodsId, content, Level, isAnonymous, serviceMark, fileData);*/
    	addComment(orderId, goodsId, content, Level, isAnonymous, serviceMark);
    	
    	
    	
    	/*$.ajax({  
    	     url : "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/addComments",  
    	     type : "POST",  
    	      
    	     token: token,
             is_show: 0,
             orderId: orderId,
             content: content,
             goodsId: goodsId,
             Level: Level,
             isAnonymous: isAnonymous,
             serviceMark: serviceMark,
             dataType: "JSON",
             success: function (data) {
                 if (data['result'] == 1) {
                     tip("评论成功");
                     alert("success");
                     setTimeout(function () { window.location.reload(); }, 3000);
                 }
             },
             error: function (XMLHttpRequest, textStatus, errorThrown) {
             	alert(XMLHttpRequest.responseText); 
             	alert(XMLHttpRequest.status);
             	alert(XMLHttpRequest.readyState);
             	alert(textStatus); // parser error;

                // window.location.href = "../personInfo/orderDetail.html";
             } 
    	});*/
    	
    	
    } 
}


/**
 * 弹出提示框
 */
function tip(tip) {
    bootbox.alert({
        message: tip,
        buttons: {
            ok: {
                label: "确定",
                className: "btn-danger",
            }
        }
    });
}

/**
 * 上传文件测试
 */
function loadImg(){  
    //获取文件  
    var file = $("#imgForm").find("input")[0].files[0];  
  
    //创建读取文件的对象  
    var reader = new FileReader();  
  
    //创建文件读取相关的变量  
    var imgFile;  
  
    //为文件读取成功设置事件  
    reader.onload=function(e) {  
        alert('文件读取完成');  
        imgFile = e.target.result;  
        console.log(imgFile);  
        $("#imgContent").attr('src', imgFile);  
    };  
  
    //正式读取文件  
    reader.readAsDataURL(file);  
}



