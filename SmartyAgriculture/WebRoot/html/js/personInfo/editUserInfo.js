/**
 * 获取用户信息
 */
function setUserInfo(){
     getPersonInfo();
     var token = getToken();
     jQuery("#token").attr("value",token);
     var data=getUserInfo(token);
     jQuery("#edit_username").val(data['content']['name']);
     jQuery(".portrait").attr("src", data['content']['portrait']);
}
/**
 * 更新用户信息
 */
function updateUserInfo(){
    var token = getToken();
    var name=jQuery("#edit_username").val();
    var fileName=jQuery("#update_portrait").val();
       jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/changeuserInfo",
        data: {
            token: token,
            name:name,
            fileName:fileName
        },
        dataType: "json",
        success: function (data) {
           // console.log(data);
           
        },
    });
}
/**
 * 选择用户头像
 */
function onchangeFile(element){
     var fileName=getObjectURL(element.files[0]);
     jQuery(".portrait").attr("src",fileName);
}
//建立一個可存取到該file的url
function getObjectURL(file) {
	var url = null ; 
	if (window.createObjectURL!=undefined) { // basic
		url = window.createObjectURL(file) ;
	} else if (window.URL!=undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file) ;
	} else if (window.webkitURL!=undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file) ;
	}
	return url ;
}
/**
 * 修改联系方式
 */
function updatePhone(){
    jQuery("#edit_table").addClass("display_hidden");
    jQuery("#change_password").addClass("display_hidden");
    jQuery("#update_phone").addClass("display_hidden");
    jQuery("#change_eamil").addClass("display_hidden"); 
    
    jQuery("#update_phone_1").removeClass("display_hidden");
}
/**
 * 修改密码
 */
function updatePassword(){
    // 隐藏其他的table
     jQuery("#edit_table").addClass("display_hidden");
     jQuery("#update_phone_1").addClass("display_hidden");
     jQuery("#update_phone").addClass("display_hidden");
      jQuery("#change_eamil").addClass("display_hidden"); 
    //显示修改密码的table
    jQuery("#change_password").removeClass("display_hidden"); 
}
/**
 * 修改邮箱
 */
function updateEmail(){
     jQuery("#edit_table").addClass("display_hidden");
     jQuery("#update_phone_1").addClass("display_hidden");
     jQuery("#update_phone").addClass("display_hidden");
     jQuery("#change_password").addClass("display_hidden");
     //显示修改邮箱的table
     jQuery("#change_eamil").removeClass("display_hidden"); 
}
/**
 * 修改联系方式
 * 原手机号和密码验证通过之后
 */
function updatePhone2(){
     jQuery("#change_password").addClass("display_hidden");
    jQuery("#update_phone_1").addClass("display_hidden");
    jQuery("#update_phone").removeClass("display_hidden");
}
/**
 * 修改邮箱
 * 原邮箱和密码通过之后
 */
function updateEmail2(){
     jQuery("#change_eamil").addClass("display_hidden");
     jQuery("#change_eamil2").removeClass("display_hidden");
}
/**
 * 发送验证码
 */
var InterValObj; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数

function sendMessage() {
  　curCount = count;
　　//设置button效果，开始计时
     jQuery("#btnSendCode").attr("disabled", "true");
     jQuery("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
　　  //向后台发送处理数据
    //  $.ajax({
    //  　　type: "POST", //用POST方式传输
    //  　　dataType: "text", //数据格式:JSON
    //  　　url: 'http://127.0.0.1:8080/SmartyAgriculture/SDKSendSMS/SendSMS', //目标地址
    // 　　 data: {
    //        phone:"",
    //        randomCode:'"'  
    //     },
    // 　　 error: function (XMLHttpRequest, textStatus, errorThrown) { },
    //  　　success: function (msg){ }
    //  });
}
//timer处理函数
function SetRemainTime() {
            if (curCount == 0) {                
                window.clearInterval(InterValObj);//停止计时器
                jQuery("#btnSendCode").removeAttr("disabled");//启用按钮
                jQuery("#btnSendCode").val("重新发送验证码");
            }
            else {
                curCount--;
                jQuery("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
            }
 }
 function SetRemainTime2() {
            if (curCount == 0) {                
                window.clearInterval(InterValObj);//停止计时器
                jQuery("#btnSendCode_email").removeAttr("disabled");//启用按钮
                jQuery("#btnSendCode_email").val("重新发送验证码");
            }
            else {
                curCount--;
                jQuery("#btnSendCode_email").val("请在" + curCount + "秒内输入验证码");
            }
 }
 /**
  * 生成验证码以及发送短信
  */
 function createCode(){
     sendMessage();
	var phone = jQuery("#new-phone").val();
    var code;
    code = new Array();
    var codeLength = 6;//验证码的长度
    var selectChar = new Array(0,1,2,3,4,5,6,7,8,9);
    for(var i=0;i<codeLength;i++) {
    	var charIndex = Math.floor(Math.random()*10);
        code +=selectChar[charIndex];
    }
    sessionStorage.setItem("validateCode",code);
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/SDKSendSMS/SendSMS",
        dataType: "json",
        data: {
            "phone":phone,
            "randomCode":code,
        },
        success: function(data) {
        	tip("验证码发送成功");
        },
        error: function(data) {
           // alert(data.result);
           tip("手机号不能为空");
        }	      
    });
}
/**
 * 修改手机号
 * 验证原手机号和密码
 */
function validate(){
    var phone=jQuery("#oldPhone").val();
    var password=jQuery("#password").val();
    password=hex_md5(password);
     var token = getToken();
     if(phone==''||password==''){
         tip("请填写用户名和密码");
     }
     else{
            jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/publicApp/checkpasswd",
        data: {
           phone: phone,
           passwd:password,
           token:token
        },
        dataType: "json",
        success: function (data) {
           if(data['result']==1){
               //验证通过
               updatePhone2();
           }
           else if(data['result']==-1){
               //密码错误、
                 tip("密码错误");
           }
           else{
               //
                tip("用户不存在");
               }
        },
    });
     }
}
function tip(tip){
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
 * 验证验证码
 */
function validateCode(validateCode){
    var rightValidate=sessionStorage.getItem("validateCode");//获取保存的验证码
    if(validateCode==rightValidate){
        return true;
    }
    else{
        return false;
    }
}
/**
 * 提交新手机号和验证码
 * 修改手机号
 */
function submitChangePhone(){
    var vCode=jQuery("#validate-code").val();
    var flag=validateCode(vCode);
   //验证码验证成功，提交修改新的手机号
   if(flag){
       var phone=jQuery("#new-phone").val(); 
       var token=getToken();
        jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/changePhone",
        dataType: "json",
        data: {
            "phone":phone,
            "token":token,
        },
        success: function(data) {      	
         //     window.location.reload();
         if(data['result']==1){
               tip("手机号修改成功");
               setTimeout(function(){window.location.reload();},3000);
         }
        },
        error: function(data) {
           // alert(data.result);
            tip("用户不存在");
        }	      
    });
   }
   else{
        tip("请重新填写验证码");
   }
}
function submitChangePassword(){
    var password=jQuery("#old-password").val();
    password=hex_md5(password);
    var token=getToken();
    var flag=validatePassword(password,token);
    if(flag==1){
        //密码验证通过，提交修改密码请求
        var newPassword=jQuery("#new-password").val();
        var confirmPassword=jQuery("#confirm-password").val();
        if(newPassword!=confirmPassword){
            tip("请确认2次输入新密码一致");
        }
        else{
            newPassword=hex_md5(newPassword);
              jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/changeuserInfo",
        dataType: "json",
        data: {
            "passwd":newPassword,
            "token":token
        },
        success: function(data) {
        	tip("密码修改成功");
            setTimeout(function(){window.location.reload();},3000);
        },
        error: function(data) {
           console.log(data);
        }	      
    });
        }
    }
    else{
        tip("原密码输入错误");
    }
}
/**
 * 验证密码是够正确
 */
function validatePassword(password,token){
    var phone=jQuery("#score").text();
    var flag=0;
     jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/publicApp/checkpasswd",
        dataType: "json",
        data: {
            "phone":phone,
            "passwd":password,
            "token":token
        },
        success: function(data) {
        	flag=data['result'];
        },
        error: function(data) {
           console.log(data);
        }	      
    });
    return flag;
}
/**
 * 验证邮箱格式以及密码是否正确
 */
function validateMail(){
    var oldEmail=jQuery("#oldEmail").val();
    var password=jQuery("#email_password").val();
    password=hex_md5(password);
     var token = getToken();
     if(oldEmail==''||password==''){
         tip("请填写邮箱和密码");
     }
     else if(!isEmail(oldEmail)){
         tip("请填写正确的邮箱格式"); 
     }
     else{
            jQuery.ajax({
        type: "POST",
        async: false,
        url: "http://127.0.0.1:8080/SmartyAgriculture/publicApp/checkpasswd",
        data: {
           phone: oldEmail,
           passwd:password,
           token:token
        },
        dataType: "json",
        success: function (data) {
           if(data['result']==1){
               //验证通过
               updateEmail2();
           }
           else if(data['result']==-1){
               //密码错误、
                 tip("密码错误");
           }
           else{
               //
                tip("用户不存在");
               }
        },
    });
     }
}
 /**
  * 生成验证码以及发送邮箱
  */
 function createCodeEmail(){
    sendMessage2();
	var phone = jQuery("#new-email").val();
    var code;
    code = new Array();
    var codeLength = 6;//验证码的长度
    var selectChar = new Array(0,1,2,3,4,5,6,7,8,9);
    for(var i=0;i<codeLength;i++) {
    	var charIndex = Math.floor(Math.random()*10);
        code +=selectChar[charIndex];
    }
    sessionStorage.setItem("validateCode",code);
    jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/SDKSendSMS/SendSMS",
        dataType: "json",
        data: {
            "phone":phone,
            "randomCode":code,
        },
        success: function(data) {
        	tip("验证码发送成功");
        },
        error: function(data) {
           // alert(data.result);
           tip("新邮箱不能为空");
        }	      
    });
}
function sendMessage2() {
  　 curCount = count;
　　//设置button效果，开始计时
     jQuery("#btnSendCode_email").attr("disabled", "true");
     jQuery("#btnSendCode_email").val("请在" + curCount + "秒内输入验证码");
     InterValObj = window.setInterval(SetRemainTime2, 1000); //启动计时器，1秒执行一次
}
/**
 * 提交新手机号和验证码
 * 修改手机号
 */
function submitChangeEmail(){
    var vCode=jQuery("#validate-code_eamil").val();
    var flag=validateCode(vCode);
   //验证码验证成功，提交修改新的手机号
   if(flag){
       var phone=jQuery("#new-email").val(); 
       var token=getToken();
        jQuery.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/changePhone",
        dataType: "json",
        data: {
            "phone":phone,
            "token":token,
        },
        success: function(data) {      	
         //     window.location.reload();
         if(data['result']==1){
               tip("邮箱修改成功");
               setTimeout(function(){window.location.reload();},3000);
         }
         else{
            tip("邮箱修改失败"); 
         }
        },
        error: function(data) {
           // alert(data.result);
            tip("用户不存在");
        }	      
    });
   }
   else{
        tip("请重新填写验证码");
   }
}
