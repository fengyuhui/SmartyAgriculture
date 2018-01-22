	/**
	 * 提交评论隐藏请求
	 * @param id
	 */
function statusHidden(id){
		$.ajax({
			type : "post",
			url : "comment/updateCommentStatus/"+id+"/1",
			dataType : "json",
			async: false,
			success : function(data) {
				alert("修改成功！");
			  //  window.location.reload();
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
 *审核商品下销售状态 
 *
 */
function commentStatusAudit(id,status){
	$.ajax({
		type : "post",
		url : "comment/updateCommentStatus/"+id+"/"+status,
		dataType : "json",
		async: false,
		success : function(data) {
			alert("审核成功！");
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
function commentStatusAudit2(id,status){
	$.ajax({
		type : "post",
		url : "comment/updateCommentStatus/"+id+"/"+status,
		dataType : "json",
		async: false,
		success : function(data) {
			alert("审核成功！");
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("修改失败！");
			console.log(XMLHttpRequest);
			console.log(textStatus);
			// window.location.reload();
		}
	});
}