/**
 * 界面加载函数
 */
$(document).ready(function() {
	// 设置商品状态
	var status = $("#status_search").attr("data-checked");
	$("#status_search").val(status);
	// 设置商品销售状态
	var saleStatus = $("#saleStatus_search").attr("data-checked");
	$("#saleStatus_search").val(saleStatus);
});

/**
 * 商品状态改变触发事件
 */
function updateGoodsStatus(id, element) {
	var status = $(element).val();
	$.ajax({
		type : "post",
		url : "product/updateGoodsStatus",
		dataType : "json",
		data : {
			status : status,
			id : id
		},
		success : function(data) {
			alert("修改成功！");
		  //  window.location.reload(false);
		    document.execCommand("Refresh");
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
 * 点击修改商品的销售状态
 */
function onclickSaleStatus(id) {
	$("#gray").show();
	$("#popup").show();// 查找ID为popup的DIV show()显示#gray
	$("#submit_changeSaleStauts").attr("data-id", id);// 查找ID为popup的DIV show()显示#gray
}
// 点击关闭按钮
$("a.guanbi").click(function() {
	$("#gray").hide();
	$("#popup").hide();// 查找ID为popup的DIV hide()隐藏
})
/**
 * 修改商品销售界面---销售状态改变函数
 */
function updateSaleStatusByChange(element){
	var saleStatus=$(element).val();
//	$("#specialPrice").addClass("hidden");
//	$("#end_time").addClass("hidden");
//	$("#open_time").addClass("hidden");
	if(saleStatus==1){
		//特价
		$("#specialPrice").removeClass("hidden");
		$(".show_time").addClass("hidden");//显示开始时间和结束时间
	}
	if(saleStatus==2){
		//限时
		$(".show_time").removeClass("hidden");//显示开始时间和结束时间
	}
}
/**
 * 修改商品状态
 */
function updateSaleStatus(element) {
	var id = $(element).attr("data-id");
	var saleStatus = $("#saleStatus").val();
	var time = $("#datetimepicker_mask_open").val();
	if (saleStatus == 2 && time == "____/__/__ __:__") {
		alert("请选择限时截至时间");
	}
//	if (time == "____/__/__ __:__") {
//		time = null;
//	} else {
		$.ajax({
			type : "post",
			url : "product/updateSaleStatus",
			dataType : "json",
			data : {
				saleStatus : saleStatus,
				id : id,
				time : time
			},
			success : function(data) {
				alert(data['msg']);
				 //window.location.reload();
				$("#gray").hide();
				$("#popup").hide();// 查找ID为popup的DIV hide()隐藏
				// 获取iframe的src刷新一下
				window.location.reload();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("修改失败！");
				console.log(XMLHttpRequest);
				console.log(textStatus);
				// window.location.reload();
			}
		});
		
//	} 
}
/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	if (flag == "next") {
		if (allPages != currentPage) {
			var src = "/SmartyAgriculture/product/index/" + allPages + "/"
					+ currentPage + "/" + flag;
			var status = $("#status_search").val();
			var saleStatus = $("#saleStatus_search").val();
			var productName = $("#name-search-input").val();
			if (productName == null || productName == "") {
				productName = 1;
			}
			src = src + "/" + status + "/" + saleStatus + "/" + productName;
			parent.changeIframe(src);
		} else {
			alert("已到达最后一页");
		}
	} else {
		var src = "/SmartyAgriculture/product/index/" + allPages + "/"
				+ currentPage + "/" + flag;
		var status = $("#status_search").val();
		var saleStatus = $("#saleStatus_search").val();
		var productName = $("#name-search-input").val();
		if (productName == null || productName == "") {
			productName = 1;
		}
		src = src + "/" + status + "/" + saleStatus + "/" + productName;
		parent.changeIframe(src);
	}
}
/**
 * 商品状态值改变触发事件
 */
function statusChange(element) {
    var status=$(element).val();
    var saleStatus = $("#saleStatus_search").val();
	var productName = $("#name-search-input").val();
	var src = "/SmartyAgriculture/product/index/0/1/prvious";
	if (productName == null || productName == "") {
		productName = 1;
	}
	src = src + "/" + status + "/" + saleStatus + "/" + productName;
	parent.changeIframe(src);
}
/**
 * 商品销售状态值改变触发事件
 */
function saleStatusChanage(element){
	    var status = $("#status_search").val();
	    var saleStatus = $(element).val();
		var productName = $("#name-search-input").val();
		var src = "/SmartyAgriculture/product/index/0/1/prvious";
		if (productName == null || productName == "") {
			productName = 1;
		}
		src = src + "/" + status + "/" + saleStatus + "/" + productName;
		parent.changeIframe(src);
}
/**
 * 商品名字
 */
function productNameChange(){
	var status = $("#status_search").val();
	var saleStatus = $("#saleStatus_search").val();
	var productName = $("#name-search-input").val();
	if (productName == null || productName == "") {
		productName = 1;
	}
	//productName=encodeURI(encodeURI(productName));
	var src = "/SmartyAgriculture/product/index/0/1/prvious";
	src = src + "/" + status + "/" + saleStatus + "/" + productName;
	parent.changeIframe(src);
}
/**
 * 批量修改商品的销售状态
 * @param element
 */
function updateSaleStatusBath(element){
	var saleStatus=$(element).val();
	var idList=getCheckedId();
    var i=saleStatus*1;//+1*1;
	$("#saleStatus option").eq(i).attr("selected",true);
	switch(saleStatus){
	case '0'://普通
		//operate_pop();
		submitChangeByBath(idList,saleStatus);
		break;
	case '1'://特价
		operate_pop();
		postIdToUpdate(idList);
		break;
	case '2'://限时 
		operate_pop();
		$(".show_time").removeClass("hidden");//显示开始时间和结束时间
		postIdToUpdate(idList);
		break;
	case '3'://新品
		//operate_pop();
		submitChangeByBath(idList,saleStatus);
		break;
	case '4'://热卖
		operate_pop();
	    break;
	};
	
}
/**
 * 显示
 */
function operate_pop(){
	$("tr").remove(".item");
    $("#gray").show();
	$("#popup").show();// 查找ID为popup的DIV show()显示#gray
}
/**
 * 获取所有的选中的状态
 */
function getCheckedId(){
	var inputList=$(".checked_input");
	var idList="";
	$.each(inputList,function(i,value){
		var flag=$(this).is(":checked");
		if(flag){
			//选中
			idList=idList+"_"+$(this).attr("data-id");
		}
	});
	return idList;
}
/**
 * 批量商品的销售状态
 * 参数：字符串
 */
function postIdToUpdate(idList){
	$.ajax({
		type : "post",
		url : "product/getProductListByIdList",
		dataType : "json",
		data : {
			//saleStatus : saleStatus,
			idList : idList,
		},
		success : function(data) {
            //添加到商品销售状态编辑界面
//			$("tr").remove(".item");
//            $("#gray").show();
//        	$("#popup").show();// 查找ID为popup的DIV show()显示#gray
			
            $.each(data,function(index,val){
            	var string="<tr class='item'><td>"+val['id']+"</td>"
            	+"<td>"+val['name']+"</td>"
            	+"<td>"+val['price']+"</td>"
            	+"<td><input type='text' class='productList_change_table_input' data-id='"+val['id']+"'/></td>"
            	+"</tr>";
            	$(".productList_change").after(string);
            });
            
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
 * 批量提交修改状态 普通 新品
 */
function submitChangeByBath(idList,saleStatus){
	
	$.ajax({
		type : "post",
		url : "product/updateProductSalStatusByBath",
		dataType : "json",
		data : {
			saleStatus : saleStatus,
			idList : idList,
		},
		success : function(data) {
			//console.log(data);
			alert("修改成功！");
			  //  window.location.reload(false);
			window.location.href = window.location.href; 
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
 * 批量提交修改商品 特价 限时
 */
function submitChangeInPop(){
	var inputList=$(".productList_change_table_input");
	var arrList = new Array();
	$.each(inputList,function(i,val){
		var id=$(this).attr("data-id");
		var price=$(this).val();
		var object={
				'id':id,
				'specialPrice':price
		};
		arrList.push(object);
	});
	var obj = $(arrList).stringify();	
	//console.log(obj);
	var saleStatus=$("#saleStatus").val();
	var endTime="2016/08/26 19:00",openTime="2016/08/26 19:00";
	if(saleStatus==2){
		endTime=$("#datetimepicker_mask_end").val();
		openTime=$("#datetimepicker_mask_open").val();
	}
	$.ajax({
		type : "post",
		url : "product/updateProductSalStatusByBathPrice",
		dataType : "json",
		data : {
			//saleStatus : saleStatus,
			jsonList : obj,
			saleStatus:saleStatus,
			endTime:endTime,
			openTime:openTime	
		},
		success : function(data) {
			alert("修改成功！");
            window.location.href = window.location.href; 
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改失败！");
			console.log(XMLHttpRequest);
			console.log(textStatus);
		}
	});
}
/**
 * 修改商品进货审核状态
 */
function updateInputGoodsStatus(id, status) {
	$.ajax({
		type : "post",
		url : "product/auditInputGoodsStatus",
		dataType : "json",
		data : {
			id : id,
			status : status
		},
		success : function(data) {
		//	alert("修改成功！");
			window.location .href =window.location .href;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改失败！");
			console.log(XMLHttpRequest);
			console.log(textStatus);
			// window.location.reload();
		}
	});
}
$.fn.stringify=function(){
	return JSON.stringify(this);
}