
/**
*分类级别
*/
function selectByType(element){
    var level=$(element).val();
    var src = "/SmartyAgriculture/type/getTypeList/0/1/prvious/";
    src=src+level+"/0";
	parent.changeIframe(src);
}
/**
*根据父类删选
*/
function selectByParent(element){
	 var parentId=$(element).val();
	 var src = "/SmartyAgriculture/type/getTypeList/0/1/prvious/0/";
	 src=src+parentId;
	 parent.changeIframe(src);
}
/**
 * 分页
 */
function dividePage(allPages, currentPage, flag) {
	var level=$("#level_search").val();
	var parentId=$("#parentId_search").val();
	if (flag == "next") {
		if (allPages != currentPage) {
			var src = "/SmartyAgriculture/type/getTypeList/" + allPages + "/"
					+ currentPage + "/" + flag;
			
			src = src + "/" + level + "/" + parentId ;
			parent.changeIframe(src);
		} else {
			alert("已到达最后一页");
		}
	} else {
		var src = "/SmartyAgriculture/type/getTypeList/" + allPages + "/"
				+ currentPage + "/" + flag;
		
		src = src + "/" + level + "/" + parentId ;
		parent.changeIframe(src);
	}
}
/**
*修改商品类别显示状态
*/
function updateTypeIsdisplay(id,element){
	
	var isdisplay = $(element).val();
	
		$.ajax({
			type : "post",
			url : "type/updateTypeIsdisplay",
			dataType : "json",
			data : {
				isdisplay : isdisplay,
				typeId : id,
			},
			success : function(data) {
				alert(data['msg']);
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
}