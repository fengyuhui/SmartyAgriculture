/**
*修改订单状态
*/
function updateOrderStatus(id, element){
	var status = $(element).val();
//	alert(typeof(status) );

		$.ajax({
			type : "post",
			url : "farmVisits/updateOrderStatus",
			dataType : "json",
			contentType:"application/json;charset=utf-8",
			data : JSON.stringify({"orderStatus":status,"id":id}),
			success : function(data) {
				alert("修改成功！");
//			    window.location.reload();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("修改失败！");
				console.log(XMLHttpRequest);
				console.log(textStatus);
				// window.location.reload();
			}
		});
	
	
}
/**
 * 显示
 */
function operate_pop(){
	$("tr").remove(".item");
    $("#gray").show();
	$("#popup").show();// 查找ID为popup的DIV show()显示#gray
}
function submitChangeInPop(){
	var status = 3;//已发货
	var id=$("#submit_changeSaleStauts").attr("data-id");
	var tracking=$("#tracking").val();
	$.ajax({
		type : "post",
		url : "order/updateOrderStatus",
		dataType : "json",
		data : {
			status : status,
			id : id,
			tracking:tracking
		},
		success : function(data) {
			alert("修改成功！");
		    window.location.reload();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改失败！");
			console.log(XMLHttpRequest);
			console.log(textStatus);
			// window.location.reload();
		}
	});
}
/**
 * 根据购买时间删选订单
 */
function orderTimeChange(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	if (starttime == "" || endtime == "") {
		alert("请选择购买时间");
	}
	else{
		var src = "/SmartyAgriculture/farmVisits/getAllOrders/0/1/prvious";
		src = src + "/" + starttime + "/" + endtime ;
		parent.changeIframe(src);
	}
}
/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
    if(starttime==""){
    	starttime="0000-00-00";
    	endtime="0000-00-00";
    }
	var src = "/SmartyAgriculture/farmVisits/getAllOrders/" + allPages + "/"
	+ currentPage + "/" + flag+ "/" + starttime + "/" + endtime ;
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
function exportExcel(){
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var timeFlag=$("#status_search").val();
    var status=$("#orderStatus").val();
    if(starttime=="" && endtime == ""){
    	starttime="0000-00-00";
    	endtime="0000-00-00";
    }
    else {
    	if(starttime == "" || endtime == "") {
	    	alert("请选择开始时间和结束时间再选择导出excel,若都不选择则默认导出所有订单的excel");
	    	return ;
    	}
    }
    var src = "farmVisits/exportExcelOrder/"+starttime+"/"+endtime;
    var form = $("#exportExcel");
    form.attr("action",src);
    form.submit();
//	  $.ajax({ 
//	  type:"POST", 
//	  url:src,
//      contentType : "application/json",
//	  success: function(data){ 
//			alert("文件已成功下载到桌面");
//	  },  
//	 error: function(data)   
//	 {  
//		 alert("文件下载失败，请稍后重试");
//	 }
//	});
//	  alert("若订单过多导出可能非常耗时，但请不要关闭页面，导出成功后会立即有提示");
}
//点击关闭按钮
$("a.guanbi").click(function() {
	$("#gray").hide();
	$("#popup").hide();// 查找ID为popup的DIV hide()隐藏
})