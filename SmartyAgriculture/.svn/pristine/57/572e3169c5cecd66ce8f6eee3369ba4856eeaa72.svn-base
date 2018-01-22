
//分页
function dividePage(allPages, currentPage, flag) {
	if (flag == "next") {
		if (allPages != currentPage) {
			var src = "/SmartyAgriculture/user/index/" + allPages + "/"
					+ currentPage + "/" + flag;
			var userName = $("#userName-search-input").val();
			var phoneNumber = $("#phoneNumber-search-input").val();
			
			if (userName == null || userName == "") {
				userName = 1;
			}
			
			if (phoneNumber == null || phoneNumber == "") {
				phoneNumber = 1;
			}
			src = src + "/" + userName + "/" + phoneNumber;
			parent.changeIframe(src);
		} else {
			alert("已到达最后一页");
		}
	} else {
		var src = "/SmartyAgriculture/user/index/" + allPages + "/"
				+ currentPage + "/" + flag;
		var userName = $("#userName-search-input").val();
		var phoneNumber = $("#phoneNumber-search-input").val();
		
		if (userName == null || userName == "") {
			userName = 1;
		}
		
		if (phoneNumber == null || phoneNumber == "") {
			phoneNumber = 1;
		}
		src = src + "/" + userName + "/" + phoneNumber;
		parent.changeIframe(src);
	}
}

/**
 * 根据用户名和手机号进行查询
 */
function searchByuserNameAndphoneNumber(){
	var userName = $("#userName-search-input").val();
	var phoneNumber = $("#phoneNumber-search-input").val();
	
	if (userName == null || userName == "") {
		userName = 1;
	}
	
	if (phoneNumber == null || phoneNumber == "") {
		phoneNumber = 1;
	}
	
	//productName=encodeURI(encodeURI(productName));
	var src = "/SmartyAgriculture/user/index/0/1/prvious";
	src = src + "/" + userName + "/" + phoneNumber;
	parent.changeIframe(src);
}
/**
*修改用户状态
*/
function updataUserStatus(id,element){
	var status = $(element).val();
	$.ajax({
		type : "post",
		url : "user/updateUserStatus",
		dataType : "json",
		data : {
			status : status,
			id : id
		},
		success : function(data) {
			alert("修改成功！");
		    window.location.reload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改失败！");
			console.log(XMLHttpRequest);
			console.log(textStatus);
		}
	});
}