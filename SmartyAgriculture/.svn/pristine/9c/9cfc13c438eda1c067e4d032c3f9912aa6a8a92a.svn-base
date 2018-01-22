
/**
 * 商品状态改变触发事件
 */
function updateStatus(id, element) {
	var status = $(element).val();
	if(status!=0){
		$.ajax({
			type : "post",
			url : "goodsRejected/dealRejected/"+id+"/"+status,
			dataType : "json",
			success : function(data) {
				console.log(data);
				alert("修改成功！");
				setTimeout(function(){window.location.reload();},900);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("修改失败！");
				console.log(XMLHttpRequest);
				console.log(textStatus);
				// window.location.reload();
			}
		});
	}
	else{
		alert("请选择操作");
	}
	
}