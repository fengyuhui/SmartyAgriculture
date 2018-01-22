/**
 * 修改地址是否是默认
 */
function updateAddressInfo(element){
    var token=getToken();
    var id=jQuery(element).attr("data-id");
       jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/address/changeAddress",
        data: {
            token: token,
            id:id,
            isDefaultAddress:2
        },
        dataType: "json",
        success: function (data) {
           // console.log(data);
           tip("修改成功");
           setTimeout(function(){window.location.reload();},2000);
        },
    });
}
/**
 * 删除地址
 */
function deleteAddress(id){
      var token=getToken();
     jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/address/deleteAddress",
        data: {
            token: token,
            addressId:id,
        },
        dataType: "json",
        success: function (data) {
            console.log(data);
            if(data['result']!=0){
                tip("地址删除成功");
                setTimeout(function(){window.location.reload();},2000);
            }
        },
    });
}
/**
 * 添加地址
 */
function addAddress(){
    var token=getToken();
    var name=jQuery("#name").val();
    var phone=jQuery("#phone").val();
    var province=jQuery("#s_province").val();
    var city=jQuery("#s_city").val();
    var county=jQuery("#s_county").val();
    var detailAddress=jQuery("#detailAddress").val();
    var isDefaultAddress=jQuery("#isDefaultAddress").attr("checked");
    if(isDefaultAddress=="checked"){
        isDefaultAddress=2;
    }
    else{
        isDefaultAddress=1;
    }
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080//SmartyAgriculture/interface/address/addAddress",
        data: {
            token: token,
            name:name,
            phone:phone,
            province:province,
            city:city,
            county:county,
            detailAddress:detailAddress,
            isDefaultAddress:isDefaultAddress
        },
        dataType: "json",
        success: function (data) {
            if(data['result']==1){
                tip("地址添加成功");
                setTimeout(function(){window.location.href='addressInfo.html';},2000);
            }
        },
    });
}
/**
 * 编辑地址
 */
function getAddressInfo(id){
   sessionStorage.setItem("addressId",id); 
    window.location.href = "editAddress.html";
}
/**
 * 获取地址详情
 */
function getAddressDetail(){
    setUserInfo();
     var token=getToken();
     var id=sessionStorage.getItem("addressId");
      jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/address/getAddressDetail",
        data: {
            token: token,
            addressId:id,
        },
        dataType: "json",
        success: function (data) {
           console.log(data);
           if(data['result']==1){
             jQuery("#name").val(data['content']['name']);
             jQuery("#phone").val(data['content']['phone']);
             jQuery("#detailAddress").val(data['content']['detailAddress']);
             //设置默认地址
             if(data['content']['isDefaultAddress']==2){
                   jQuery("#isDefaultAddress").attr("checked",false);
             }
            // jQuery("#s_province").val(data['content']['province']);
            // jQuery("#s_city").val(data['content']['city']);
            // jQuery("#s_county").val(data['content']['county']);
           }
        },
    });
}
/**
 * 保存编辑的地址
 */
function saveAddress(){
    var token=getToken();
    var name=jQuery("#name").val();
    var phone=jQuery("#phone").val();
    var province=jQuery("#s_province").val();
    var city=jQuery("#s_city").val();
    var county=jQuery("#s_county").val();
    var detailAddress=jQuery("#detailAddress").val();
    var isDefaultAddress=jQuery("#isDefaultAddress").attr("checked");
    if(isDefaultAddress=="checked"){
        isDefaultAddress=2;
    }
    else{
        isDefaultAddress=1;
    }
    jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/address/changeAddress",
        data: {
            token: token,
            name:name,
            phone:phone,
            province:province,
            city:city,
            county:county,
            detailAddress:detailAddress,
            isDefaultAddress:isDefaultAddress
        },
        dataType: "json",
        success: function (data) {
            if(data['result']==1){
                tip("地址添加成功");
                setTimeout(function(){window.location.href='addressInfo.html';},2000);
            }
        },
    });
}
