<%@page import="cn.bupt.smartyagl.entity.autogenerate.Goods"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//request.setAttribute("", );
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>编辑农场信息 </title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/assets/css/dropzone.css" />

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>

<!-- ueditor -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>
</head>

<body>

	<div class="page-content">
		<div class="page-header">
			<h1>
				编辑信息<small> <i class="icon-double-angle-right">${addition.title}</i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="addition/saveEdit"
					method="post" enctype="multipart/form-data">
					<input value="${addition.id}" type="hidden" name="id" />
					<input value="${addition.title}" type="hidden" name="title" />
					<div class="space-4"></div>
						<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2" >信息详情 </label>
						<div class="col-sm-9">
							 <script id="editor" type="text/plain" style="width:1024px;height:500px;" name="content" >
								${addition.content}
							</script>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<a class="btn" type="reset" href="addition/toEdit">
								<i class="icon-undo bigger-110">返回</i> 
							</a>
						</div>
					</div>

					<div class="hr hr-24"></div>

				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');
/*var content='${addition.content}';
ue.ready(function(){
	ue.setContent(content);
});*/
</script>
<script type="text/javascript">
	//信息类型 二级联动设置
	$(document).ready(function() {
		$("#messageType").change(function() {
			$("#messageType option").each(function(i, o) {
				if ($(this).attr("selected")) {
					$(".typeSub").attr("name", "");
					$(".typeSub").hide();
					$(".typeSub").eq(i).show();
					$(".typeSub").eq(i).attr("name", "messageType");
				}
			});
		});
		$("#messageType").change();
		//设置信息种类的默认选中值
		i=$("#messageType").attr("data-default");
		document.getElementById("messageType").options[i].selected="selected";
		//console.log(j);
		//document.getElementById("typeSub_show").options[j].selected="selected";
		var select = $(".typeSub").eq(i).children();  
		//console.log(select);
		for(var i=0; i<select.length; i++){  
		    if(select[i].value == j){ 
		    //	console.log(select[i].value);
		        select[i].selected = true;  
		        break;  
		    }  
		}  
	});
	//是否置顶 二级联动设置
	$(document).ready(function() {
		$("#topOrNot").change(function() {
			$("#topOrNot option").each(function(i, o) {
				if ($(this).attr("selected")) {
					$(".typeSub").attr("name", "");
					$(".typeSub").hide();
					$(".typeSub").eq(i).show();
					$(".typeSub").eq(i).attr("name", "topOrNot");
				}
			});
		});
		$("#topOrNot").change();
		//设置是否置顶的默认选中值
		i=$("#topOrNot").attr("data-default");
		document.getElementById("topOrNot").options[i].selected="selected";
		//console.log(j);
		//document.getElementById("typeSub_show").options[j].selected="selected";
		var select = $(".typeSub").eq(i).children();  
		//console.log(select);
		for(var i=0; i<select.length; i++){  
		    if(select[i].value == j){ 
		    //	console.log(select[i].value);
		        select[i].selected = true;  
		        break;  
		    }  
		}  
	});
	/**文件选择的按钮
	 */
	$(".file").change(function() {
		var objUrl = getObjectURL(this.files[0]);
		//console.log("objUrl = "+objUrl) ;
		if (objUrl) {
			$(this).parent().next().attr("src", objUrl);
			$(this).parent().next().width("200");
			$(".continuePicture").show();
			var file=$(this).val();
			$(this).prev().prev().children().val(file);
			
		}
	});
	/**文件选择的按钮
	 */
	$("#first_file").change(function() {

			$(".continuePicture").show();
	
		}
	);
	$(function(){
		//选择默认值
		var cookLeisureTypeValue = ${cookLeisure.cookLeisureType};
		$("#type option[value="+typeValue+"]").attr("selected", true);
		//选择默认值
		var topOrNotValue = ${cookLeisure.topOrNot};
		$("#top option[value="+topValue+"]").attr("selected", true);
	});
</script>
</html>
