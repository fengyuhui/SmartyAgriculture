
//分页
function dividePage(allPages, currentPage, flag) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
    if(starttime==""){
    	starttime="0000-00-00";
    	endtime="0000-00-00";
    }
	var src = "/SmartyAgriculture/block/index/" + allPages + "/"
	+ currentPage + "/" + flag ;
	if (flag == "next") {
		if (allPages != currentPage) {		
			parent.changeIframe(src);
		} else {
			alert("已到达最后一页");
		}
	} else {
		parent.changeIframe(src);
	}
}

