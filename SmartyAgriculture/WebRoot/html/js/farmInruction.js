var pagestr;
function getpage(){
    $.ajax({
        type:"post",
        async:false,
        url:"http://127.0.0.1:8080/SmartyAgriculture/interface/farmMessage/getFarmMessage",
        data:{
            typeId:1,
            pageNum:1,
            pageSize:1
        },
        dataType:"JSON",
        success:function(data){
            pagestr=data['content'][0].detailUrl;
            console.log(pagestr);
            var test="http://zhongyuanfarm.cn:8080/SmartyAgriculture/farmMessage/find?id=1"
            var $pageContainer=$('<div/>',{class:"pagebar"});
            var $iframe=$('<iframe/>',{id:"innerpage",src:pagestr,scrolling:"no",style:"height: 3150px;"});
            $('#iframe-container').append($iframe);
        }
    });
}


function autoadapt(){
    document.domain
    var doc=document.getElementById("innerpage");
    //$(doc).css("src",pagestr);
    console.log($(doc).contents().find("body"));
    if($(doc).contents().find("body")){
        $(doc).height($(doc).contents().height());
    }
}
jQuery().ready(function () {
    navigationJudge();
    getpage();
    autoadapt();
//    if (window.addEventListener)
//    window.addEventListener("load", autoadapt, false);
//    else if (window.attachEvent)
//    window.attachEvent("onload", autoadapt);
//    else
//    window.onload=autoadapt;
	
});
$(window).load(function(){
    //console.log(pagestr);
    //$('#innerpage').css("src",pagestr);
});
$(document).keypress(function (e) {
	//回车键事件
	if (e.which == 13) {
		jQuery(".search-btn").click();
	}
});
/**
 * 获取搜索信息
 */
function setSearchinfo() {
	var search_info = jQuery(".search-field").val();
	if (search_info == "搜索") {
		return 0;
	}
	localStorage.setItem("search_info", search_info);
}




/**
 * 获取用户信息
 */
function setUserInfo() {
	getPersonInfo();
	var token = getToken();
	var data = getUserInfo(token);
	jQuery("#edit_username").val(data['content']['name']);
	jQuery(".portrait").attr("src", data['content']['portrait']);
}


/**
 * 返回token
 */
function getToken() {
	var token = localStorage.getItem("token");
	//如果没有获取到token，则跳转到登陆界面
	if (jQuery.trim(token) != "") {
		return token;
	}
}

/**
 * 获取个人信息
 */
function getUserInfo(token) {
	var dataInfo;
	jQuery.ajax({
		type: "POST",
		async: false,
		url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
		data: {
			token: token,
		},
		dataType: "json",
		success: function (data) {
			if (data['result'] == 1) {
				dataInfo = data;
			}
		}
	});
	return dataInfo;
}

function getPersonInfo() {
	var token = getToken();
	var data = getUserInfo(token);
	jQuery("#username").text(data['content']['name']);
	jQuery("#score").text(data['content']['phone']);
	jQuery("#person_portrait").attr("src", data['content']['portrait']);
	//设置header中的用户名
	jQuery("#header_username").text(data['content']['name']);
}

/*----------------------------------
--------判断是否登陆，显示适当navigation-
-----------------------------------*/
function navigationJudge() {
    var token = localStorage.getItem("token");
    console.log("Token:"+token);
    if (token == null) {
        var navString = '<a href="#"><i class="icon-user"></i></a><a href="login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="register.html"><span>注册</span></a>';
        jQuery('.login-info').append(navString);

    } else {

        jQuery.ajax({
            type: "post",
            url: "http://127.0.0.1:8080/SmartyAgriculture/interface/user/userInfo",
            data: {
                token: token,
            },
            dataType: "JSON",
            success: function(data) {
                if (data.result == 1) {
                    localStorage.setItem("userInfo", data.content)
                    var navString = '<span class="login-success"><a>欢迎您!&nbsp;</a>' +
                        '<a href="personInfo/editUserInfo.html" class="user-name user-nick">' + data.content.name +
                        '</a></span><span class="logon-success">' +
                        '<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
                    jQuery('.login-info').append(navString);

                }
                else{
                    var navString = '<a href="#"><i class="icon-user"></i></a><a href="login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="register.html"><span>注册</span></a>';
                    jQuery('.login-info').append(navString);
                }
            }
        });
    }
}


function signOut() {
    localStorage.clear();
    window.location.reload();
}
