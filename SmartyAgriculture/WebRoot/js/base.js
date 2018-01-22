// JavaScript Document
function checkAll(form, name) {
	for ( var i = 0; i < form.elements.length; i++) {
		var e = form.elements[i];
		if (e.name.match(name)) {
			e.checked = form.elements['chkall'].checked;
		}
	}
}
/**
*取消
*/
function goBack(){
	self.location = document.referrer;
}
//比较开始时间和结束时间
function checkEndTime(starttime,endtime){
	starttime=starttime.replace("年","-");
	starttime=starttime.replace("月","-");
	starttime=starttime.replace("日","");
	endtime=endtime.replace("年","-");
	endtime=endtime.replace("月","-");
	endtime=endtime.replace("日","");
	var start=new Date(starttime.replace("-", "/").replace("-", "/"));
	var end=new Date(endtime.replace("-", "/").replace("-", "/"));
	if(end<start){
	return false;
	}
	return true;
}