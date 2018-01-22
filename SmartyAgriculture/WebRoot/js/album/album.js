function showPic(whichpic) {
    if (document.getElementById) {
        document.getElementById('placeholder').src = whichpic.href;
      //  document.getElementById('ShowLightBox').href = whichpic.href;
        // if (whichpic.title) {
        //     document.getElementById('desc').childNodes[0].nodeValue = whichpic.title;
        // } else {
        //     document.getElementById('desc').childNodes[0].nodeValue = whichpic.childNodes[0].nodeValue;
        // } 
        return false;
    } else {
        return true;
    }
}
/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	if (flag == "next") {
		if (allPages != currentPage) {
			var src = "/SmartyAgriculture/album/index/" + allPages + "/"
					+ currentPage + "/" + flag;
			parent.changeIframe(src);
		} else {
			alert("已到达最后一页");
		}
	} else {
		var src = "/SmartyAgriculture/album/index/" + allPages + "/"
				+ currentPage + "/" + flag;
		parent.changeIframe(src);
	}
}