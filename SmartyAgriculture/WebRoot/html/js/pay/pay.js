function getOrder(){
    var order=localStorage.getItem("orderPay");
    order=JSON.parse(order);
    var address=localStorage.getItem("addressStr");
    $('#orderNo').html(order.orderId);//填写订单ID
}
