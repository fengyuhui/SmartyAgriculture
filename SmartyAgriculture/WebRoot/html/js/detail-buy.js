/*------------------------
 * 
 * create by luzhaoxu
 * 2016-6-23
 * 
 ---------------------------*/
//function setHeight(){
//	$(".reply-text").css("height","50px");
//}
var node;
function reply(obj){
	var testString="";
    var str="<textarea class='reply-text' onfocus='setHeight()' name='replyArea'></textarea>";
    var tdNode=$(obj.parentNode.parentNode.parentNode).prev().children();
    console.log(tdNode[0]);
    var repNode=document.createElement('div');
    repNode.setAttribute('class','reply-area');
    repNode.innerHTML=str;
//    repNode.appendTo(tdNode[0]);
    $(tdNode[0]).after(repNode);
}
function showMe(src){
	var picString="";
	picString=src;
	document.getElementById("show-big").src=picString;
}
function onInput(event){
    var unitNum=event.target.value;
    localStorage.setItem("num",unitNum);
}

function orderInfoStrore(){
    var id=localStorage.getItem("goodsId");
    var nums=localStorage.getItem("num");
    var orderInfo =new Array();
    orderInfo.push({"goodsID":id,"num":nums});
    var stringfiorderInfo = JSON.stringify(orderInfo);
    localStorage.setItem("orderInfo",stringfiorderInfo);
    //alert(stringfiorderInfo);
}
function getOrderDetail(){
    orderInfoStrore();
    var token =localStorage.getItem("token");
    if(token==null){//是否未登录购买
        bootbox.setLocale("zh_pay");
        bootbox.confirm({
            message:"您还<b class='alert-tip'>未登录</b>，是否登陆后进行再购买吗？<br/><br/>(提示：可以选<b class='alert-tip'>匿名购买</b>进行未登录购买)",
            button:{
                confirm:{
                    label:"匿名购买"
                },
                cancel:{
                    label:"登陆"
                }
            },
            callback:function(result){
                if(result){
                    window.location.href="login.html";
                }else{
                    $('.pops-address').slideDown();
                    $('#areaMask').show();
                }
            }
        });
//        window.location.href="login.html";
    }else{
        setTimeout(function(){
                   window.open("order-confirm.html");
                   },100);
    }
}
//获取各评论类型数量
function distributeRvNum(numList){
    numList=JSON.parse(numList);
    if(numList==null){
        $('.all-rv-num').html("(0)");
    }else{
        var all=0;
//        $.each(numList,function(name,value){
//            all+=value;
//        });
        $('.all-rv-num').html(numList.all);
        $('.hasPic-rv-num').html(numList.hasPic);
        $('.good-rv-num').html(numList.good);
        $('.mid-rv-num').html(numList.middle);
        $('.bad-rv-num').html(numList.bad);
    }   
}
//jQuery.holdReady(true);
//jQuery.getScript("detail-buy.js",function(){
//    jQuery.holdReady(false);
//});
jQuery(document).ready(function(){
    localStorage.setItem("currentUrl",window.location.href);
    localStorage.setItem("num",1);
    var id=localStorage.getItem("goodsId");
    getAllComment(id,1);//获取全部评论
    getCmtNum(id);
    var numList=localStorage.getItem("commentNumList");//获取评论列表
    distributeRvNum(numList);
    $('.all-rv').click(function(){
        $('.pagination').html("");
        event.preventDefault();
        getAllComment(id,1)});
    $('.hasPic-rv').click(function(){
        $('.pagination').html("");
        event.preventDefault();
        getAllComment(id,2)});
    $('.good-rv').click(function(){
        $('.pagination').html("");
        event.preventDefault();
        getAllComment(id,3)});
    $('.avg-rv').click(function(){
        event.preventDefault();
        getAllComment(id,4)});
    $('.bad-rv').click(function(){
        event.preventDefault();
        getAllComment(id,5)});
    $('.cart-addTo').click(function(event){
       event.preventDefault;
        addCart();
    });
    $('.h-close').click(function(){
        $('.pops-address').slideUp();
        $('.mask').hide();
    });
//    $('.buy-button').click(function(event){
//        event.preventDefault;
//        getUnloginUserInfo();
//    });
});
//获取未登录购买表单信息
function getUnloginUserInfo(){
    var token =localStorage.getItem("token");
    console.log(token);
    var orderInfo=localStorage.getItem("orderInfo");
    orderInfo=JSON.parse(orderInfo);
    var name=$('#name').val();
    var phone=$('.user-phone').val();
    var province=$('#s_province option:selected').text();
    var city=$('#s_city option:selected').text();
    var county=$('#s_county option:selected').text();
    var detail=$('#detail-address').val();
    var message=$('#message').val();
    var orderMessage=new Array();
    for(var i=0;i<orderInfo.length;i++){
        orderMessage[0].push({"goodsId":orderInfo[i].goodsID,"num":orderInfo[i].num,"message":message});
    }
    orderMessage=JSON.stringify(orderMessage);
    var address={"name":name,"phone":phone,"province":province,"city":city,
                "county":county,"detailAddress":detail};
    address=JSON.stringify(address);
    console.log(orderMessage+" "+address);
    if(validateMobile(phone)==false){
        $(".user-phone").css("border-color","red");
    }else{
        if(name.length!=0&&province.length!=0&&city.length!=0&&county.length!=0&&detail.length!=0){
            $.ajax({
                type:"POST",
                url:"http://127.0.0.1:8080/SmartyAgriculture/interface/order/addOrderWithoutlog",
                data:{
                    orderMessage:orderMessage,
                    address:address,
                    phone:phone
                },
                dataType:"json",
                success:function(data){
//                    var logOrNot=1;
//                    localStorage.setItem("logOrNot",logOrNot);
                    localStorage.setItem("withoutLogAddress",address);
                    window.location.href="order-confirm.html";
//                    localStorage.setItem("annoymousBuyer",data[])
//                    bootbox.setLocale("zh_CN"); 
//                    bootbox.alert("");
                },
                error:function(){
                    console.log(orderMessage+" "+address);
                    bootbox.alert("抱歉，接口调试中！");
                }
            });
        }else{
            bootbox.alert("请输入正确信息");
        }
    }
}
function validateMobile(mobile){
    console.log(mobile);
    if (mobile.length == 0 || mobile.length != 11) {
        return false;
    }
    var myreg = /^(((13[0-9]{1})|((17[0-9]{1}))|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (!myreg.test(mobile)) {
        return false;
    }
}
