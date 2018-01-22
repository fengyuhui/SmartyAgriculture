function load() {
	if ($.cookie("rmbUser") == "true") {
		$("#ck_rmbUser").attr("checked", true);
		$("#phone").val($.cookie("phone"));
		$("#pw1").val($.cookie("pw1"));
	}
}
//记住用户名密码
function Save() {
	if ($("#ck_rmbUser").attr("checked")) {
		var str_username = $("#phone").val();
		var str_password = $("#pw1").val();
		console.log(str_username);
		alert(str_username);
		$.cookie("rmbUser", "true", {
			expires: 7
		}); //存储一个带7天期限的cookie
		$.cookie("phone", str_username, {
			expires: 7
		});
		$.cookie("pw1", str_password, {
			expires: 7
		});
	} else {
		$.cookie("rmbUser", "false", {
			expire: -1
		});
		$.cookie("phone", "", {
			expires: -1
		});
		$.cookie("pw1", "", {
			expires: -1
		});
	}

};

function login() {
	var phone = $("#phone").val();
	var pw1 = $("#pw1").val();
	var phoneIdentify = 0;
	var passwordIdentify = 0;
	localStorage.setItem("data", phone);
	var msg = localStorage.getItem("data");
	//phone validate
	if (validatemobile(phone) == false) {
		bootbox.alert({
			message: "账号或密码错误",
			size: 'small',
			callback: function () {
				window.location.href = "login.html";
			}
		});
	} else {
		phoneIdentify = 1;
	}
	//password validate
	if (pw1.length == 0) {
		bootbox.alert({
			message: "密码不能为空",
			size: 'small',
			callback: function () {
				window.location.href = "login.html";
			}
		});
	} else {
		passwordIdentify = 1;
	}
	if (phoneIdentify == 1 && passwordIdentify == 1) {

		pw1 = hex_md5(pw1);
		$.ajax({
			type: "POST",
			url: "http://localhost:8080/SmartyAgriculture/publicApp/login",
			dataType: "json",
			data: {
				"phone": phone,
				"passwd": pw1
			},
			success: function (data) {
				if (data.result == 1) {
					// alert("登录成功");
					bootbox.alert({
						message: "登录成功",
						size: 'small',
						callback: function () {
							window.history.go(-1);
						}
					});
					localStorage.setItem("token", data.content.token);
					// window.history.go(-1);
				} else {
					if (data.result == -1) {
						//   alert("该用户不存在");
						bootbox.alert({
							message: "该用户不存在",
							size: 'small',
							callback: function () {
								window.location.href = "login.html";
							}
						});


					} else {
						if (data.result == -2) {
							// alert("密码错误，请重新输入");
							bootbox.alert({
								message: "密码错误，请重新输入",
								size: 'small',
								callback: function () {
									window.location.href = "login.html";
								}
							});
						}

					}

				}
			},
			error: function(data) {
				// alert("登录失败");
				bootbox.alert({
					message: "登录失败",
					size: 'small',
					callback: function() {
						window.location.href = "login.html";
					}
				});
			}
		});
	}
}
// }

function validatemobile(mobile) {
	var phoneverify = 0;
	var emailverify = 0;
	if (mobile.length == 0) {
		return false;
	}
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	var emailReg = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if (myreg.test(mobile)) {
		phoneverify = 1;
	}
	if (emailReg.test(mobile)) {
		emailverify = 1;
	}
	if (phoneverify == 1 || emailverify == 1) {
		return true;
	} else {
		return false;
	}
}
