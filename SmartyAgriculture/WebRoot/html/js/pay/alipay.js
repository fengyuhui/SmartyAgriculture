function requestUrl(){
    var order=localStorage.getItem("orderPay");
    console.log(order);
    order=JSON.parse(order);
    paraSubmit(order);
    //var type=localStorage.getItem("orderType");
    var type=1;//1商品，2参观农场
    var overduaMoney=0.01;//overdueMoney 大于0就是纯支付宝支付，否则就是支付宝加购物卡支付
    var body={"type":type,"overduaMoney":overduaMoney};
    console.log(body);
    body=JSON.stringify(body);
    body=body.replace(/["]/g,"");//利用正则表达式删除字符串中的双引号
    console.log(body);
    $('input[name=WIDtotal_fee]').attr("value",0.01);
    $('input[name=WIDbody]').attr("value",body);
    $('form').submit();
}
function timetrans(date){//处理时间格式
    //var date = new Date(date*1000);//如果date为10位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() <10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() <10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y+M+D+h+m+s;
}
function paraSubmit(order){
    $('input[name=WIDout_trade_no]').attr("value",order.OrderId);
//    $('input[name=WIDsubject]').attr("value",order.name);
    //$('input[name=WIDtotal_fee]').attr("value",0.01);
    //$('input[name=WIDbody]').attr("value",bodyStr);
}
