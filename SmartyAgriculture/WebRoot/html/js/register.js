function createCode(){
	var phone = $("#phone").val();
	var randomCode = $("#randomCode").val();
    var code;
    code = new Array();
    var codeLength = 6;//验证码的长度
    var selectChar = new Array(0,1,2,3,4,5,6,7,8,9);
    for(var i=0;i<codeLength;i++) {
    	var charIndex = Math.floor(Math.random()*10);
        code +=selectChar[charIndex];
    }
    sessionStorage.setItem("data",code);
    var msg = sessionStorage.getItem("data"); 
    $.ajax({
        type: "POST",
        url: "http://127.0.0.1:8080/SmartyAgriculture/SDKSendSMS/SendSMS",
        dataType: "json",
        data: {
            "phone":phone,
            "randomCode":msg,
        },
        success: function(data) {
        	alert("验证码已发送");
        },
        error: function(data) {
           // alert(data.result);
           alert("请输入邮箱或手机号");
        }	      
    });
}	
 function register(){
    var phone = $("#phone").val();
    var randomCode = $("#randomCode").val();
    var msg = sessionStorage.getItem("data");
    var pw1=$("#pw1").val();
	var pw2=$("#pw2").val();
	var phoneIdentify=0;
	var codeIdentify=0;
	var passwordIdentify=0;
	var checkIdentify=0;
	if(validatemobile(phone)==false){
		alert("请输入有效手机号");
	}
	else{		
		phoneIdentify=1;
	}
	if(randomCode.length == 0){
		alert("验证码不能为空");
	}
	else{
		if(randomCode != msg){
			alert("输入的验证码错误");
		}
		else{
           codeIdentify=1;
	   }	
	}
	if(pw1.length==0||pw2.length==0){
		alert("密码不能为空");
	}
	else{
		if(validatepassword(pw1,pw2)==false){
			alert("两次输入密码不一致");
		}
		else{
			passwordIdentify=1;
		}
	}
	if(validatecheck()==false){
		alert("请阅读并勾选智慧农业协议");
	}
	else{
		checkIdentify=1;
	}

	if(phoneIdentify==1&&passwordIdentify==1&&checkIdentify==1&&codeIdentify==1){
	    pw1=hex_md5(pw1);//密码用MD5加密传输
        $.ajax({
            type: "POST",
            url: "http://127.0.0.1:8080/SmartyAgriculture/publicApp/register",
            dataType: "json",
            data: {
                "phone":phone,
                "passwd":pw1
            },
            success: function(data) {
            	if(data.result==-1){
            		alert("该账号已经注册!");
            		window.location.href="login.html";
            	}else{
            		window.location.href="index.html";
            	}
            },
            error: function(data) {
                alert(data.result);
            }
        });
    }
}
function validatemobile(mobile) {
    var phoneverify=0;
    var emailverify=0;
    if (mobile.length == 0) {
        return false;
    }
    //var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    var myreg = /^1(3|4|5|7|8)\d{9}$/;
    var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
    if (myreg.test(mobile)) {
       phoneverify=1;
    }
    if (emailReg.test(mobile)) {
       emailverify=1;
    }
    if(phoneverify==1||emailverify==1){
        return true;
    }
    else{
        return false;
    }
}
function validatepassword(pw1,pw2) {
    if (pw1!=pw2) {
        return false;
    }
}
function validatecheck() {
	if($("#checkbox_input").is(":checked")){
		return true;
	}
	else{
		return false;
	}
}
