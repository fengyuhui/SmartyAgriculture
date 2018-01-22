/*------------------------------------

    created by zhangqiang
    date:2016 10 25

------------------------------------*/
function navigationJudge() {
	var token = localStorage.getItem("token");
	console.log("Token:" + token);
	if(token == null) {
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
				if(data.result == 1) {
					localStorage.setItem("userInfo", data.content)

					var navString = '<span class="login-success"><a>欢迎您!&nbsp;</a>' +
						'<a href="#" class="user-name user-nick">' + data.content.name +
						'</a></span><span class="logon-success">' +
						'<a href="javascript:void(0)" class="login-out" onclick="signOut()">退出</a></span>';
					jQuery('.login-info').append(navString);

				} else {
					var navString = '<a href="#"><i class="icon-user"></i></a><a href="login.html"><span>登录</span></a><span>&nbsp;/&nbsp;</span><a href="register.html"><span>注册</span></a>';
					jQuery('.login-info').append(navString);
				}
			}
		});
	}
}

$(function() {
	$("#current").css("border-bottom", "0px solid #fff");
	$("#brand").css("border-bottom", "0px solid #fff");
	$("#ecology").css("border-bottom", "0px solid #fff");
	$("#vist").show();
	$("#life").css("border-bottom", "0px solid #fff");
	$("#health").css("border-bottom", "0px solid #fff");
	$("#game").css("border-bottom", "0px solid #fff");
	$("#product").css("border-bottom", "0px solid #fff");
});

/**
 * 获取搜索信息
 */
function setSearchinfo() {
	var search_info = jQuery(".search-field").val();
	if(search_info == "搜索") {
		return 0;
	}
	localStorage.setItem("search_info", search_info);
}

/**
 * 回车键事件 
 * 绑定键盘按下事件 
 */
$(document).keypress(function(e) {
	// 回车键事件  
	if(e.which == 13) {
		jQuery(".search-btn").click();
	}
});

$(function() {
	localStorage.setItem("visitorNum", 1);
	$(".minus-number").click(function() {
		if($('.visitor-number').html() == 1) {
			$('.visitor-number').html(0);
			$('.allPrice').text('0');
			return 0;
		}
		if($('.visitor-number').html() == 0) {
			$('.visitor-number').html(0);
			$('.allPrice').text('0');
		} else {
			var num = $('.visitor-number').html();
			$('.visitor-number').html(num - 1);
			num = Number($('.visitor-number').text());
			var price = Number($('#priceInfo').text());
			//			console.log(price);
			//			console.log(num);
			console.log(num);
			var allprice = price * (num);
			$('.allPrice').text(allprice.toFixed(2));

			localStorage.setItem("visitorNum", num);
			localStorage.setItem("num", num);
		}
	});
	$(".pluse-number").click(function() {
		var num = Number($('.visitor-number').html());
		$('.visitor-number').html(num + 1);
		num = Number($('.visitor-number').text());
		var price = Number($('#priceInfo').text());
		console.log(price);
		var allprice = price * (num);
		$('.allPrice').text(allprice.toFixed(2));
		localStorage.setItem("visitorNum", num);
		localStorage.setItem("num", num);
	});
})

//提交订单函数
function submitOrder() {
	var num = localStorage.getItem("num");
	console.log(num);
	var token = localStorage.getItem("token");
	var id = localStorage.getItem("id");
	id=43;
	var time = $("#data").val();
	console.log(time);
	console.log(id);
	console.log(token);
	var numIdentify = 0;
//	var idIdentify = 0;
	var timeIdentify = 0;
	if(time.length == 0) {
		alert("时间不能为空")
	} else {
		timeIdentify = 1;
	}
	var d=new Date(Date.parse(time.replace(/-/g,"/")));
    
	var curDate=new Date();

	console.log("curDate :"+curDate);
	console.log("d "+d);
	if(d<=curDate){
	alert("请选择有效日期！");
	timeIdentify = 0;
	}
//	if(id.length == 0) {
//		alert("尚未选择订单")
//	} else {
//		idIdentify = 1;
//	}
	if(num.length == 0 && num ==0) {
		alert("尚未选择人数")
	} else {
		numIdentify = 1;
	}
	if(timeIdentify == 1 && numIdentify == 1) {
		$.ajax({
			type: "POST",
			url: "http://127.0.0.1:8080/SmartyAgriculture/interface/farmVisitOrder/createOrder",
			dataType: "json",
			data: {
				"token": token,
				"visitld": id,
				"visit_time": time,
				"num": num
			},
			success: function(data) {
				var s = JSON.stringify(data);
				console.log("data: "+s);
                    
					alert("加入订单成功");
					
				    //window.location.href = "personInfo/infoIndex.html";

			}

		})
	}
};

//function searchClick() {
//	var searchfield = jQuery(".search-field").val();
//	console.log("商品名称：" + searchfield);
//	jQuery.ajax({
//		type: "POST",
//		url: "http://127.0.0.1:8080/SmartyAgriculture/interface/goods/getGoodsList",
//		dataType: "json",
//		data: {
//			name: searchfield, //此处是传递给服务器需要查询的商品名称
//			pageNum: 1,
//			pageSize: 10,
//		},
//		success: function(data) {
//			createList(data);
//		},
//	});
//}

$(document).keypress(function(e) {
	//回车键事件
	if(e.which == 13) {
		jQuery(".search-btn").click();
	}

});

function getToken() {
	var token = localStorage.getItem("token");
	//如果没有获取到token，则跳转到登陆界面
	if(jQuery.trim(token) != "") {
		return token;
	}
}

function load() {
	navigationJudge();
	var screenHeight = $(window).height();
	var screenWidth = $(window).width();
	$("#container").css("height", screenHeight);
	$("#container").css("width", screenWidth);
	var token = localStorage.getItem("token");
	console.log("userToken:" + token);
	//  var string= '';
	$.ajax({
		type: "post",
		url: "http://127.0.0.1:8080/SmartyAgriculture/interface/farmVisit/getAllVisitTypes",
		data: {
			token: token
		},
		dataType: "json",
		success: function(data) {
			var strlist = JSON.stringify(data['content']);
			console.log("str: "+strlist);
			localStorage.setItem("productDetail", strlist);
			//  		console.log(data);
			var arrLen = data.content.length;
			var arrLens = data.content[0].typeList.length;
			//			console.log(arrLens); //			var string= '';
			var price = data.content[0].typeList[0].price;
			//			var ids = data.content[0].typeList[i].id;
			$('#priceInfo').text(price);
			$(".allPrice").text(price.toFixed(2));
			//			console.log(price);
			for(var i = 0; i < arrLens; i++) {
				var string = '';
				if(i == 0) {
					string +=
						"<li class='left-top' id='" + data.content[0].typeList[i].id + "' onclick='xiangmu0(" + data.content[0].typeList[0].id + "," + data.content[0].typeList[0].price + ")' style='background:#61ca8b'>" +
						'<span>' + data.content[0].typeList[0].title + '</span>' +
						'</li>';
					//					   $(".classification").append(string);
				} else {
					string +=
						//						''  <span class="left-top" id="1" onclick="leftTop()">半日含餐</span> 
						"<li class='left-top' id='" + data.content[0].typeList[i].id + "' onclick='xiangmu0(" + data.content[0].typeList[i].id + "," + data.content[0].typeList[i].price + ")'>" +
						'<span>' + data.content[0].typeList[i].title + '</span>' +
						'</li>';
					//					   $(".classification").append(string);
				}
				$(".classificationss").append(string);

			}
			for(var i = 0; i < arrLen; i++) {
				var typeList = data.content[i].typeList;
				var projectTitle = data.content[i].projectTitle;
				var id = data.content[i].id;
				console.log("id "+id);
				var string = '';
				if(i == 0) {
					string +=
						"<span class='xiangmu' id='" + id + "' onclick='xiangmus(" + JSON.stringify(typeList) + "," + JSON.stringify(projectTitle) + "," + id + " )' style='border: 1px solid #098D38' >" +
						'<img src="images/login-left.jpg"' +
						//				     '<img src="http://127.0.0.1:8080'+data.content[i].imgList+
						//				     '"onerror="javascript:this.src=\'images/logo/96.png\';" alt="中远农庄" ' +
						'/><a>' + projectTitle + '</a></span>';
				} else {
					string +=
						"<span class='xiangmu' id='" + id + "' onclick='xiangmus(" + JSON.stringify(typeList) + "," + JSON.stringify(projectTitle) + "," + id + " )'>" +
						'<img src="images/login-left.jpg"' +
						'/><a>' + projectTitle + '</a></span>';
				}

				$(".projectChooses").append(string);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
	           console.log(XMLHttpRequest.responseText); 
	           console.log(XMLHttpRequest.status);
	           console.log(XMLHttpRequest.readyState);
	           console.log(textStatus); // parser error;
	   },

	})

}

function xiangmus(typeList, projectTitle, id) {
	//	var typeList = JSON.stringify(typeList);
	var arrButtons = [];
	var arrLists = [];
	var set = document.getElementById(id);
	//	console.log(set);
	arrLists = document.getElementsByTagName("span");
	console.log(arrLists);
	for(var i = 0; i < arrLists.length; i++) {
		if(arrLists[i].className == "xiangmu") {
			arrButtons.push(arrLists[i]);
		}
	}
	console.log(arrButtons);
	for(var j = 0; j < arrButtons.length; j++) {
		if(set.id == arrButtons[j].id) {
			//			arrButtons[j].style.backgroundColor = "#61ca8b";
			arrButtons[j].style.border = "1px solid #098D38";
			continue;
		} else {
			//			arrButtons[j].style.backgroundColor = "white";
			arrButtons[j].style.border = "1px solid white";
		}
	}
	var projectTitle = JSON.stringify(projectTitle);
	var arrLen = typeList.length;
	$('.visitor-number').text(1);
	$('#priceInfo').text(Number(typeList[0].price));
	$(".allPrice").text(Number(typeList[0].price).toFixed(2));
	//	console.log(arrLen);
	//	alert(projectTitle);

	$(".classificationss").empty();
	var string = '';
	for(var i = 0; i < arrLen; i++) {
		if(i == 0) {
			string +=
				"<span class='left-top' id='" + typeList[0].id + "' onclick='xiangmu0(" + typeList[0].id + "," + typeList[0].price + ")' style='background:#61ca8b'>" +
				'<span>' + typeList[0].title + '</span>' +
				'</span>';
			//								   $(".classification").html(string);
		} else {
			string +=
				//						''  <span class="left-top" id="1" onclick="leftTop()">半日含餐</span> 
				"<span class='left-top' id='" + typeList[i].id + "' onclick='xiangmu0(" + typeList[i].id + "," + typeList[i].price + ")'>" +
				'<span>' + typeList[i].title + '</span>' +
				'</span>';
		}
	}
	$(".classificationss").append(string);

}

function xiangmu0(id, price) {
	var arrButtons = [];
	var arrLists = [];
	var set = document.getElementById(id);
	//	console.log(set);
	var id = localStorage.setItem("id", id);
	//	console.log(id);
	//	console.log(price);
	arrLists = document.getElementsByTagName("span");
	//	console.log(arrLists);
	for(var i = 0; i < arrLists.length; i++) {
		if(arrLists[i].className == "left-top") {
			arrButtons.push(arrLists[i]);
		}
	}
	console.log(arrButtons);
	for(var j = 0; j < arrButtons.length; j++) {
		if(set.id == arrButtons[j].id) {
			arrButtons[j].style.backgroundColor = "#61ca8b";
			continue;
		} else {
			arrButtons[j].style.backgroundColor = "white";
		}
	}
	$('.visitor-number').text(1);
	$('#priceInfo').text(Number(price));
	$(".allPrice").text(Number(price).toFixed(2));
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
	if(jQuery.trim(token) != "") {
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
		success: function(data) {
			if(data['result'] == 1) {
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

$(document).ready(function() {
	$('.thumbnails').simpleGal({
		mainImage: '.custom'
	});
});

function signOut() {
	localStorage.clear();
	window.location.reload();
}
