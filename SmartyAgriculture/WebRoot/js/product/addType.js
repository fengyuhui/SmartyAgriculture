/**
 *改变类别级别的函数
 */
function changeLevel(element){
	var level=$(element).val();
	if(level=='2'){
		$("#parentId_div").css("display","block");
	}
	else{
		$("#parentId_div").css("display","none");
	}
}