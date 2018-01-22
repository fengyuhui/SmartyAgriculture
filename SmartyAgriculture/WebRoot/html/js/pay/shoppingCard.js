function card_pay(){
    var totalFees=0.01;
    $('.pay-mask').show();
    $('.cardGallery').show();
    $('#totalFees').html("支付&nbsp;"+totalFees);
    //payAction();
}
function payAction(){
    var token=localStorage.getItem("token");
    var orderstr=localStorage.getItem("orderPay");
    order=JSON.parse(orderstr);
    var orderId=order.id;
    var number=$('#cardId').val();
    var passwd=$('#cardPswd').val();
    console.log(orderId+' '+token);
    loadPay(orderstr,orderId,number,passwd,token);//建立请求
}
function loadPay(orderstr,orderId,number,passwd,token){
    var order=JSON.parse(orderstr);
    $.ajax({
        type:"POST",
        async:false,
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goodsCart/payByCard",
        data:{
        "orderIds":orderId,
        "number":number,
        "passwd":passwd,
        "token":token
        },
        dataType:"JSON",
        success:function(data){
            bootbox.setLocale("zh_gouwu");
            //bootbox.alert(data['resultDesp']);
            if(data['resultDesp']!="购买成功"){
                //bootbox.alert(data['resultDesp']);
                alert("失败here");
            }
            if(data['content'].overdueMoney>0){
                order.money=data['content'].overdueMoney;
                var str=JSON.stringify(order);
                console.log(str);
                localStorage.setItem("orderPay",str);
            }else{
                bootbox.confirm({
                message:"购物卡支付成功",
                button:{
                    confirm:{
                        label:"首页"
                    },
                    cancel:{
                        label:"已完成订单"
                    }
                },
                callback:function(result){
                    if(result){
                        window.location.href="index.html";
                    }else{
                        window.location.href="personInfo/payedInfo.html"
                    }
                }
            });
            }
        },
        error:function(data){
            alert(data['resultDesp']);
        }
    });
}
function cardtips(str,overdueMoney){
    $('.tips-text span').html(str);
    $('.overdueMoney').html(overdueMoney);
    $('.type-choose').show();
    $('#cardSubmit').hide();
}
function diyAlert(){
    bootbox.confirm({
        title: "支付结果",
        message: '<span style=＂text-aligh:center">购买成功!</span>',
        buttons: {
            cancel: {
//                label: '<i class="fa fa-times"></i> Cancel'
                label:'返回首页'
            },
            confirm: {
//                label: '<i class="fa fa-check"></i> Confirm'
                label:'到我的订单查看'
            }
        },
        callback: function (result) {
            //console.log('This was logged in the callback: ' + result);
            if(result==true){
                window.location.href="personInfo/allOrder.html";
            }else{
                window.location.href="index.html";
            }
        }
    });   
}
$(document).ready(function(){
    $('.h-close').click(function(){
         $('.pay-mask').hide();
         $('.cardGallery').hide();
    });
    $('.overdue-alipay').click(function(){
        $('.cardGallery').hide();
        wx_pay();
    });
});
