/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	//订单的状态
	var orderStatus=$("#orderStatus").val();
    var src = "user/userStatisticsIndex/" + allPages + "/"
	+ currentPage + "/" + flag;
    if (starttime == null || endtime == "") {
		starttime=0;
		endtime=0;
	}
    src = src + "/" + orderStatus + "/" + starttime +"/"+endtime;
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
//function checkedEvent(){
//	var timeFlag=$("#status_search").val();
//    var status=$("#orderStatus").val();
//    //列表
//    var src = "user/userStatisticsIndex/0/1/prvious/"+status+"/"+timeFlag;
//	parent.changeIframe(src);
//}
/**
 * 导出excel
 * 
 */
function exportExcel(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	//订单的状态
	var orderStatus=$("#orderStatus").val();
	if (starttime == null || endtime == "") {
		starttime=0;
		endtime=0;
	}
    var src = "user/exportExcelUserStatistics/"+orderStatus+ "/" + starttime +"/"+endtime;
    var form = $("#exportExcel");
    form.attr("action",src);
    //alert(form.attr("action"));
    form.submit();
}
/**
 * 会员统计 搜索提交
 */
function TimeChange(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	//订单的状态
	var orderStatus=$("#orderStatus").val();
	if (starttime == null || endtime == "") {
		starttime=0;
		endtime=0;
	}
		var src = "/SmartyAgriculture/user/userStatisticsIndex/0/1/prvious/"+orderStatus+"/"+starttime+"/"+endtime;
	//	src = src + "/" + starttime + "/" + endtime ;
		parent.changeIframe(src);
	
}