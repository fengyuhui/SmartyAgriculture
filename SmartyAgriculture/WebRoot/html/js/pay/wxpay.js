var QRcodeTimer=null;//define a countDown interval
var windowTimer=null;
var maxTime=60;
var token=localStorage.getItem('token');
function countDownMinus() {//countDown function
    var msg ;
        if (maxTime >= 0) {
            var seconds = Math.floor(maxTime % 60);
            msg =seconds+"秒后此二维码过期";
            $('.wx-countdown').html(msg);
            $('#QR-retry').hide();
            $('#QR-msg').show();
            --maxTime;
            //return msg;
        } else {
            maxTime = 60;
            msg = "";
            $('#QR-msg').hide();
            $('#QR-retry').show();
            $('.QR-img img').css("opacity",0.5);
            clearInterval(QRcodeTimer);//interval end!
            return;
        }
        //console.log(msg);
}
function wx_pay(){
    var order=localStorage.getItem("orderPay");
    order=JSON.parse(order);
    console.log(order.id);
    orderId=order.id;// ID number
    orderList=orderId;
    console.log(orderList);
    var totalFees=order.money;//total price
    var paytype=1;
    var orderType=1;
    getQRCode(orderList,paytype,orderType,totalFees);
    windowTimer=setInterval('testStatus(token,orderId)',1000);
}
function getQRCode(orderList,payType,orderType,totalFees) {
    //setInterval('countDownMinus()', 1000);
    $.ajax({
        type:"post", 
        url:"http://zhongyuanfarm.cn:8080/SmartyAgriculture/interface/weixinNative/getQRCode",
        data:
        {
            orderList:orderList,
            payType:payType,
            orderType:orderType,
            totalFee:totalFees
        },
        dataType:"JSON",
        success:function(data){
            var QRimg=data['content'].QR_code;
            localStorage.setItem("QRimg", QRimg);
            QRcodeTimer = setInterval('countDownMinus()', 1000);
            $('#payGallery img').attr("src",QRimg);
            $('#payGallery img').css("opacity",1);
            $('#totalFees').html("支付&nbsp;"+totalFees);
            $('.pay-mask').show();
            $('#payGallery').show();
        }
    });
}
function wx_close_window(){
    $('.pay-mask').hide();
    $('#payGallery').hide();
    clearInterval(QRcodeTimer);
    clearInterval(windowTimer);
}
function testStatus(token,orderId){
    $.ajax({
        type:"post",
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/order/getOrderDetail",
        data:{
            token:token,
            orderId:orderId
        },
        dataType:"JSON",
        success:function(data){
            console.log(data);
            var status=data['content']['orderView'].status;
            console.log(status);
            if(status==2){
                wx_close_window();
                alert("购买成功！");
            }
        }
    });
}
function QRretry(){
    clearInterval(QRcodeTimer);
    clearInterval(windowTimer);
    wx_pay();
}
//定时刷新二维码时间提示
$(document).ready(function(){
    $('.h-close').click(function(){
         $('.pay-mask').hide();
         $('#payGallery').hide();
         clearInterval(QRcodeTimer);
         clearInterval(windowTimer);
    });
//    $('.QR-retry').click(function(event){
//        event.preventDefault();
//        wx_pay();});
});
