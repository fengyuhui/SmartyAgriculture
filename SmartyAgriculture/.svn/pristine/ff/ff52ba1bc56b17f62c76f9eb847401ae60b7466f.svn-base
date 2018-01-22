/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	if (flag == "next") {
		if (allPages != currentPage) {
			var src = "/SmartyAgriculture/farmMessage/index/" + allPages + "/"
					+ currentPage + "/" + flag;
			parent.changeIframe(src);
		} else {
			alert("已到达最后一页");
		}
	} else {
		var src = "/SmartyAgriculture/farmMessage/index/" + allPages + "/"
				+ currentPage + "/" + flag;
		parent.changeIframe(src);
	}
}