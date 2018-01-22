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
        	alert("验证成功");
        },
        error: function(data) {
            //alert(data.result);
            alert("手机号不能为空");
        }	      
    });
}
 function retrieve(){
    var phone = $("#phone").val();
    var msg = sessionStorage.getItem("data");
    var pw1=$("#pw1").val();
	var pw2=$("#pw2").val();
	var phoneIdentify=0;
	var codeIdentify=0;
	var passwordIdentify=0;
	//phone validate
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(validatemobile(phone)==false||!myreg.test(phone)){
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
	//password validate
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
	if(phoneIdentify==1&&passwordIdentify==1&&codeIdentify==1){
			pw1=hex_md5(pw1);
			//alert(pw1);
            $.ajax({
                type: "POST",
                url: "http://127.0.0.1:8080/SmartyAgriculture/publicApp/changepasswd",
                dataType: "json",
                data: {
                    "phone":phone,
                    "passwd":pw1
                },
                success: function(data) {
                    window.location.href="login.html";
                },
                error: function(data) {
                    alert(data.result)
                }
            });
    }
}
// }

function validatemobile(mobile) {
    if (mobile.length == 0 || mobile.length != 11) {
        return false;
    }
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    if (!myreg.test(mobile)) {
        return false;
    }
}
function validatepassword(pw1,pw2) {
    if (pw1!=pw2) {
        return false;
    }
}
