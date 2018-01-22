/**
 * 
 */
function dividePage(allPages, currentPage, flag,url,id,mapList) {
	var parentId=$("#parentId_search").val();
	var src = "/SmartyAgriculture/" + url + allPages + "/"
					+ currentPage + "/" + flag+'?'+"id="+id;
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
			

	if (flag == "next" && allPages == currentPage) {
		alert("已到达最后一页");
		return ;
	} 
	parent.changeIframe(src);
}