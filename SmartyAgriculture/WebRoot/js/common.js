

/**
 * 分页 url eg type/getTypeList/
 */
function dividePage(allPages, currentPage, flag,url,mapList) {
	var parentId=$("#parentId_search").val();
	var src = "/SmartyAgriculture/" + url + allPages + "/"
					+ currentPage + "/" + flag;
			var i = 0;
			for(var key in mapList){
				if( i == 0 ){
					src += "?";
					i++;
				}else{
					src += "&"
				}
				src += key + "=" + mapList[key];
			}  
			parent.changeIframe(src);

	if (flag == "next" && allPages == currentPage) {
		alert("已到达最后一页");
	} 
}

