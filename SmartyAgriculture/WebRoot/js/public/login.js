
/**
 * 登陆时验证用户信息
 */
function validateUserInfo(){
	
	//用户名和密码均不能为空
	var username=$("#username").val();
	var password=$("#password").val();
	var result="",flag=false;
	username=$.trim(username);
	password=$.trim(password);
	if(username.length==0){
		result="用户名";
		flag=true;
	}
	if(password.length==0){
		result=result+" 密码";
		flag=true;
	}
	if(flag){
		result=result+"不能为空";
		$("#errorInfo").html(result);
		return false;
	}
	else{
		return true;
	}
	
}