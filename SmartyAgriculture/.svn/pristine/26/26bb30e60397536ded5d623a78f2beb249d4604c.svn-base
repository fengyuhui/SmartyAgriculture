
/**
 * 分页
 */
function dividePage(goodsId,allPages, currentPage, flag) {	
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
    var src = "product/productInputStatistics/" + allPages + "/"
	+ currentPage + "/" + flag;
    src = src  + "/" + starttime +"/"+endtime+"/" +goodsId;
    
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
/**
 * 导出excel
 * 
 */
function exportExcel(goodsId){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
    var src = "product/exportProductInputStatistics/"+starttime+"/"+endtime+"/"+goodsId;
    var form = $("#exportExcel");
    form.attr("action",src);
    form.submit();
//	  $.ajax({ 
//	  type:"POST", 
//	  url:src,
//      contentType : "application/json",
//		success: function(data){ 
//			alert("文件已成功下载到桌面");
//	  },  
//	 error: function(data)   
//	 {  
//	     alert("文件下载失败，请稍后重试");
//	 }
//	});
}
/**
 * 统计 搜索提交
 */
function TimeChange(goodsId) {
	
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	var src = "/SmartyAgriculture/product/productInputStatistics/0/1/prvious/"
			 + starttime + "/" + endtime+"/"+goodsId;
	parent.changeIframe(src);
}
