/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	var status = $("#orderStatus").val();
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
	var src = "statistics/totalSales/" + allPages + "/" + currentPage + "/"
			+ flag;
	src = src + "/" + status + "/" + starttime+"/"+endtime;
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
// function checkedEvent(){
// var timeFlag=$("#status_search").val();
// var status=$("#orderStatus").val();
// var src = "statistics/echartsDataTotalSales/" + status + "/"
// + timeFlag ;
// $.ajax({
// type:"POST",
// url:src,
// dataType : "json",
// data : {
// timeFlag : timeFlag,
// status : status,
// },
// contentType : "application/json",
// success: function(data){
// console.log(myChart);
// myChart.setOption({
// title : {
// text : '总销售额统计',// 图标标题
// },
// xAxis: {
// name : '时间',
// data: data.time
// },
// yAxis: {//纵坐标的数据
// name : '销售额', //纵坐标的名字
// },
// series: [{
// // 根据名字对应到相应的系列
// name: '销售额',
// data: data.sales
// }]
// });
// },
// error: function()
// {
// alert("!");
// }
// });
// 列表
// var src = "statistics/totalSales/0/1/prvious/"+status+"/"+timeFlag;
// parent.changeIframe(src);
// }
/**
 * 导出excel
 * 
 */
function exportExcel() {
	var status = $("#orderStatus").val();
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
	var src = "statistics/exportExcelDataTotalSales/" + status + "/" + starttime+"/"+endtime;
	var form = $("#exportExcel");
	form.attr("action",src);
	form.submit();

}
/**
 * 会员统计 搜索提交
 */
function TimeChange() {
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
	var src = "/SmartyAgriculture/statistics/totalSales/0/1/prvious/"
			+ orderStatus + "/" + starttime + "/" + endtime;
	// src = src + "/" + starttime + "/" + endtime ;
	parent.changeIframe(src);

}