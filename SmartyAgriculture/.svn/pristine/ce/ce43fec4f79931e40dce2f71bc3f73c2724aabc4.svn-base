
/**
 * 分页
 */
function dividePage(goodsId,allPages, currentPage, flag) {	
	var status = $("#orderStatus").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	if (orderStatus == "") {
		orderStatus = 0;
	}
    var src = "product/productStatistics/" + allPages + "/"
	+ currentPage + "/" + flag;
    src = src + "/" + status + "/" + starttime +"/"+endtime+"/" +goodsId;
    
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
//
//function checkedEvent(goodsId){
//	var timeFlag=$("#status_search").val();
//    var status=$("#orderStatus").val();
//    var src = "statistics/echartsDataTotalSales/" + status + "/"
//	+ timeFlag ;
//    $.ajax({ 
//        type:"POST", 
//        url:src,
//    	dataType : "json",
//    	data : {
//    		timeFlag : timeFlag,
//    		status : status,
//		},
//		contentType : "application/json",
//		success: function(data){ 
//			console.log(myChart);
//		   myChart.setOption({
//			   title : {
//					text : '总销售额统计',// 图标标题
//				},
//	              xAxis: {
//	            	  name : '时间',
//	                  data: data.time
//	              },
//	              yAxis: {//纵坐标的数据
//	              	name : '销售额', //纵坐标的名字
//	              },
//	              series: [{
//	                  // 根据名字对应到相应的系列
//	                  name: '销售额',
//	                  data: data.sales
//	              }]
//	          });
//       },  
//       error: function()   
//       {  
//           alert("!");
//       }
//});
    //列表
//    var src = "product/productStatistics/0/1/prvious/"+status+"/"+timeFlag+"/"+goodsId;
//	parent.changeIframe(src);
//}
/**
 * 导出excel
 * 
 */
function exportExcel(goodsId){
	var status = $("#orderStatus").val();
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	if (orderStatus == "") {
		orderStatus = 0;
	}
    var src = "product/exportProductStatictics/"+status+"/"+starttime+"/"+endtime+"/"+goodsId;
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
	// 订单的状态
	var orderStatus = $("#orderStatus").val();
	if (starttime == null || endtime == "") {
		starttime = 0;
		endtime = 0;
	}
	if (orderStatus == "") {
		orderStatus = 0;
	}
	var src = "/SmartyAgriculture/product/productStatistics/0/1/prvious/"
			+ orderStatus + "/" + starttime + "/" + endtime+"/"+goodsId;
	parent.changeIframe(src);

}
