
/**
 * 分页
 */
function dividePage(typeIdParent,allPages, currentPage, flag) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var orderStatus = $("#orderStatus").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	if (orderStatus == "") {
		orderStatus = 0;
	}
    var src = "type/parentTypeStatistics/" + allPages + "/"
	+ currentPage + "/" + flag+"/"+orderStatus+"/"+ starttime + "/" + endtime+"/"+typeIdParent;;
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
function exportExcel(typeIdParent){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var orderStatus = $("#orderStatus").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	if (orderStatus == "") {
		orderStatus = 0;
	}
    var src = "type/exportProductStatictics/"+orderStatus+"/"+starttime+"/"+endtime+"/"+typeIdParent;
    var form = $("#exportExcel");
    form.attr("action",src);
    form.submit();
//	  $.ajax({ 
//	  type:"POST", 
//	  url:src,
//	//  dataType : "json",
//      contentType : "application/json",
//		success: function(data){ 
//			alert("文件已成功下载到桌面");
//	  },  
//	 error: function(data)   
//	 {  
//		 alert("文件下载失败，请稍后重试");
//	 }
//	});
}
/**
 * 统计 搜索提交
 */
function TimeChange(typeIdParent) {
	
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var orderStatus = $("#orderStatus").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	if (orderStatus == "") {
		orderStatus = 0;
	}
	var src = "/SmartyAgriculture/type/parentTypeStatistics/0/1/prvious/"
			 +orderStatus+"/"+ starttime + "/" + endtime+"/"+typeIdParent;
	parent.changeIframe(src);
}
