
function getAddress(){
    
}
function getAddressList(){
    var token=localStorage.getItem("token");
    if(token==null){
        var address=localStorage.getItem("withoutLogAddress");
        localStorage.setItem("addrList",address)
    }else{
        jQuery.ajax({
        type:"post",
        async:false,
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/address/getAddressList",
        data:{
            "token": token,
	        "pageNum": 1,
	        "pageSize":50
        },
        dataType:"JSON",
        success:function(data){
            console.log(data);
            var str =JSON.stringify(data['content']);
            localStorage.setItem("addrList",str);
        }
    });
    }  
}
//地址栏点击变换元素风格
function messageTest(obj){
    jQuery(obj).attr("checked",true);
    var radios=document.getElementsByName("adress");
    var addId=jQuery('input[type="radio"]:checked').val();//获取选中地址ID
    for(var i=0;i<radios.length;i++){
        if(radios[i].value!=obj.value){
            radios[i].checked=false;
            jQuery(radios[i]).parent().parent().removeClass('selected');
        }
    }   
    jQuery(obj).parent().parent().addClass('selected');
}
var totalFees=Number(0);
function loadOrderDetail(i,idtest,num){
    var token=localStorage.getItem("token");
    alert(token);
    jQuery.ajax({
            type:"post",
            async:false,
            url:"http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsDetail",
            data:{
                goodsId:idtest,
                token:token,
            },
            dataType:"JSON",
            success:function(data){
                    var pri=new Number(data['content'].price).toFixed(2);
                    var currentFees=new Number(data['content'].price*num).toFixed(2);
                    var orderString='<tr class="grid-main"><td colspan="5" class="tube-main"><table class="isotope-table">'
                                    +'<tbody class="detail-body"><tr class=""><td class="tube-title">'
                                    +'<p>'+data['content'].name+'</p></td>'
                                    +'<td class="tube-price">'+pri+'</td>'
                                    +'<td class="tube-amount"><div class="it-amount" id="Div1"><div class="amount-edit">'
                                    +'<img onclick="numMinus('+i+','+pri+')" class="minus" src="images/minus.png" />'
                                    +'<input id="amount-num" class="amount-text'+i+'" name="inputAmount" value="'+num+'"/>'
                                    +'<img class="plus" onclick="numPlus('+i+','+pri+')" src="images/plus.png"/></div></div></td>'
                                    +'<td class="tube-promo">-</td>'
                                    +'<td class="tube-sum"><span class="itemPay" id=""><p class="sum'+i+'">'+currentFees+'</p></span></td>'
                                    +'</tr></tbody></table></td></tr>';
                    jQuery('.orderInfo-body').append(orderString);
                    totalFees=Number(totalFees);
                    currentFees=Number(currentFees);
                    totalFees=(totalFees+currentFees).toFixed(2);
                jQuery('#real-pay').html(totalFees);
                
        }
    });
    return 1;
}
function loadaddresslist(addressList){
    addressList=JSON.parse(addressList);
    var str="";
                for(var i=0;i<addressList.length;i++){
                    var defaultTip="";
                    var defaultSet="";
                    if(addressList[i].isDefaultAddress==2){
                        defaultTip="默认地址";
                    }else{
                        defaultSet="设置为默认地址"
                    }
                    var addrString='<li class="addressli"><div class="address-info">'
                                +'<a href="javascript:void(0)" class="addr-modify">修改本地址</a>'
                                +'<input onclick="messageTest(this)" name="adress" type="radio" value="'+addressList[i].id+'" ><label class="user-address">'
                        +addressList[i].province+addressList[i].city+addressList[i].county+addressList[i].detailAddress
                                +'&nbsp;&nbsp;（'+addressList[i].name+'&nbsp;收）"&nbsp;'+'<em>'+addressList[i].phone+'</em>'
                                +'</label><em class="tip">'+defaultTip+'</em><a class="set-default" href="javascript:void(0)" data-id="'
                                +addressList[i].id+'" onclick="updateAddressInfo(this)">'+defaultSet+'</a></div></li>'
                    str+=addrString;
                    //jQuery('.addr-list').append(addrString);
                }
    $('.addr-list').html(str);
}
function orderOnload(){
    inedexOnload();
}
function addAdress(){
    var token=localStorage.getItem("token");
    var name=$('#name').val();
    var phone=$('.user-phone').val();
    var province=$('#s_province option:selected').text();
    var city=$('#s_city option:selected').text();
    var county=$('#s_county option:selected').text();
    var detail=$('#detail-address').val();
    var isdefault=$('#isdefault option:selected').text();
    isdefault=="是" ? isdefault=2:isdefault=1;
    //alert(isdefault+" "+county);
    if(validateMobile(phone)==false){
        $(".user-phone").css("border-color","red");
    }else{
        if(name.length!=0&&province.length!=0&&city.length!=0&&county.length!=0&&detail.length!=0){
            $.ajax({
                type:"POST",
                url:"http://127.0.0.1:8080/SmartyAgriculture/interface/address/addAddress",
                data:{
                    token:token,
                    name:name,
                    phone:phone,
                    province:province,
                    city:city,
                    county:county,
                    detailAddress:detail,
                    isDefaultAddress:isdefault
                },
                dataType:"json",
                success:function(data){
                    bootbox.setLocale("zh_CN"); 
                    bootbox.alert("添加地址成功！");
                    closepop();
                    getAddressList();
                    var addressList=localStorage.getItem("addrList");
                    loadaddresslist(addressList);
                    //$('.addr-list').html(str);
                }
            });
        }
    }
    
}
function updateAddressInfo(element){
    var token=getToken();
    var id=jQuery(element).attr("data-id");
//    alert(id);
       jQuery.ajax({
        type: "POST",       
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/address/changeAddress",
        data: {
            token: token,
            id:id,
            isDefaultAddress:2
        },
        dataType: "json",
        success: function (data) {
           bootbox.alert("修改成功！");
           getAddressList();
           var addressList=localStorage.getItem("addrList");
           loadaddresslist(addressList);
        }
    });
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
function openpop(){
    $('.mask').css("display","block");
    $('.pops-address').css("display","block");
}
function closepop(){
    $('.pops-address').hide();
    $('.mask').hide();
}

$(document).ready(function(){
        getAddressList();
        $('.add-address-button').click(function(){
            addAdress();
        });
        var addressList=localStorage.getItem("addrList");
        loadaddresslist(addressList);
        var orderInfo=localStorage.getItem("orderInfo");
        orderInfo=JSON.parse(orderInfo);
        var i=0;
        while(i<orderInfo.length){
            var numtmp=orderInfo[i].num;
            var idtmp=orderInfo[i].goodsID;
            if(loadOrderDetail(i,idtmp,numtmp)==1){
                i++;
            }
    }
});
function numMinus(i,price){ 
		if(jQuery('.amount-text'+i).val()==1){
			return ;
		}else{
			var num=Number(jQuery('.amount-text'+i).val());
			jQuery('.amount-text'+i).val(num-1);
            --num;
            localStorage.setItem("num", num);//将改变后的数量储存起来！！！
            var sum=Number(price * num).toFixed(2);
            jQuery('.sum'+i).html(sum);
            totalFees=Number(totalFees);
            totalFees=(totalFees-price).toFixed(2);
            //jQuery('p.sum').html(sum);
            jQuery('#real-pay').html(totalFees);
		}
}

function numPlus(i,price){
		var num =Number(jQuery('.amount-text'+i).val());
        ++num;
		jQuery('.amount-text'+i).val(num);
		//num=jQuery('.amount-text').val();
    	localStorage.setItem("num", num);//将改变后的数量储存起来！！！
        var sum=Number(price * num).toFixed(2);
    totalFees=Number(totalFees);
        totalFees=(totalFees+price).toFixed(2);
        jQuery('p.sum'+i).html(sum);
        jQuery('#real-pay').html(totalFees);
    }
     
function orderSubmit(){
    var orderInfo=localStorage.getItem("orderInfo");
    orderInfo=JSON.parse(orderInfo);
    console.log(orderInfo)
    var addJson={};
    var msgJson={};
    //var orderJson={};//订单列表json数组
    var addressId=jQuery('input[name="adress"]:checked').val();
    var addressStr=jQuery('input[name="adress"]:checked').next().html();
    localStorage.setItem("adderssStr",addressStr);
    for(var i=0;i<orderInfo.length;i++){
        var num=jQuery('input.amount-text'+i).val();
        orderInfo[i].num=num;
    }
    if(addressId==undefined){
        bootbox.alert("亲，请选择收货地址");
    }else{
        var message=jQuery('textarea.memo-text').val();
        addJson["addressId"]=addressId;
        console.log(addJson);
        msgJson["message"]=message;
        console.log(msgJson);
//        orderInfo.push({"addressId":addressId,"message":message});
//        console.log(orderInfo);
        }
    /*var tmp={};
    tmp=$.extend({},addJson,orderInfo);
    */
    //orderJson=$.extend({},orderInfo);
    //console.log(orderJson);
    var myDate=new Date();
    var str=myDate.toLocaleString();
    console.log(str);
    setOrder(orderInfo,addJson,msgJson);
    //document.getElementById("new-address").addEventListener("click",function(){openpop();})
    //$('#new-address img').click(function(){openpop()});
    //getOrder();
    //requestUrl();
    //window.open("pay.html");
}
function setOrder(orderJson,addressId,message){
   var token= localStorage.getItem("token");
   //var orderJson= localStorage.getItem("orderJSON")
   //orderJson=JSON.parse(orderJson);
    console.log("请求参数");
    console.log(/*orderJson.addressId+' '+*/orderJson[0].goodsID+'   '+orderJson[0].num);
   $.ajax({
       type:"post",
       url:"http://127.0.0.1:8080/SmartyAgriculture/interface/order/addOrder",
       data:{
            "token":token,
            "addressId":addressId["addressId"],
           "goodsIds":JSON.stringify(orderJson),
          // "nums":orderJson,
           "pickUp":1,
           "message":message["message"]
       },
       dataType:"JSON",
       success:function(data){
           var orderId=data['content'].orderId;
           console.log(orderId);
           localStorage.setItem("currentOrderId",orderId);
           bootbox.alert({
			message: "添加订单成功",
			size: 'small',
			callback: function () {
				getOrder();
                window.open("pay.html");
			}
		});
       },
       error:function(){
            bootbox.alert("添加订单失败！");
        }
   });
}
function getOrder(){
    var token=localStorage.getItem("token");
    console.log(token);
    var orderId=localStorage.getItem("currentOrderId");
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
        }
    });
}
