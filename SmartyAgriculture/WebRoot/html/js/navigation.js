/*----------------------------------
--------判断是否登陆，显示适当navigation-
-----------------------------------*/
function navigationJudge(){
    var token =localStorage.getItem("token");
   console.log(token);
    if(token==null){
        var navString='<a href="#"><i class="icon-user"></i></a><a href="login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="register.html"><span>注册</span></a>';
        jQuery('.login-info').append(navString);
        console.log(navString);
    }
    else{
        console.log("123");
        jQuery.ajax({
            type:"post",
            url:"http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
            data:{
              token:token,
            },
            dataType:"JSON",
            success:function(data){
                if(data.result==1){
                    localStorage.setItem("userInfo",data.content)
                    console.log(data.content);
                    var navString='<span class="login-success"><a>Welcome!&nbsp;</a>'
                                                 +'<a href="#" class="user-name user-nick">'+data.content.name
                                                 +'</a></span><span class="logon-success">'
                                                 +'<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
                    jQuery('.login-info').append(navString);
                    console.log(navString);
                }
            }
        });
    }
}
//function signOut(){
//    var token =getToken();
//    jQuery.ajax({
//            type:"post",
//            url:"http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
//            data:{
//              token:token,
//            },
//            dataType:"JSON",
//            success:function(data){
//                if(data.result==1){
//                    localStorage.clear();
//                    window.location.href="../index.html";
//                    }
//                }
//        });
//}
