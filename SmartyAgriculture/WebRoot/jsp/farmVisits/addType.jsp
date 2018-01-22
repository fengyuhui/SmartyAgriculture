<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<title>添加参观农场类型</title>

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



</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				添加参观农场项目 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form id="f" class="form-horizontal" action="farmVisits/addTypePost"
					method="post" enctype="multipart/form-data">
					
					<!-- 项目标题 -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">项目标题</label>
						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5" required pattern=".{1,255}" 
								placeholder="1~255个字符" id="form-input-readonly" value="" name="projectTitle" /> <span
								class="help-inline col-xs-12 col-sm-7"> 
							</span>
						</div>
					</div>
				
					<!-- 项目描述 -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">项目描述</label>
						<div class="col-sm-9">
							<textarea class="col-xs-10 col-sm-5" required maxlength="1000"
								placeholder="1000字以内" id="form-input-readonly" name="detail" /></textarea> 
							
						</div>
					</div>
					<!-- 农场图片 -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">农场图片</label>
						<div class="col-sm-9">
							<input type="file" name="file" required>
						</div>
					</div>
					
					
					<div class="form-group" id="btn1">
					<div >
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-primary btn-sm" type="button" >
							继续添加
							</button>							
						</div>
					</div>
					</div>
					
					
					<!-- 参观类型 -->				
					<div class="space-4"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">参观类型标题(必填)</label>
						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5" required pattern=".{1,255}"
								placeholder="1~255个字符" id="form-input-readonly" value="" name="title" /> <span
								class="help-inline col-xs-12 col-sm-7"> 
							</span>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">价格(必填)</label>
						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5" required pattern="^\+?(?:[1-9]\d*(?:\.\d{1,2})?|0\.(?:\d[1-9]|[1-9]\d))$"
								placeholder="价格在0.00-99999.99之间" id="form-input-readonly" name="price" value="" name="description" />							
						</div>
					</div>
					<div class="space-4"></div>

					<div class="form-group" id="btn2">
						<div >
							<div class="col-md-offset-3 col-md-9">
								<button class="btn btn-primary btn-sm" type="button">
								继续添加参观类型
								</button>							
							</div>
						</div>
					</div>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="icon-undo bigger-110"></i> 取消
							</button>
						</div>
					</div>

					<div class="hr hr-24"></div>

				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	$(document).ready(function(){
		var txt1 = "<div class='form-group'>\
						<label class='col-sm-3 control-label no-padding-right' for='form-input-readonly'>\
							农场图片</label>	\
						<div class='col-sm-9'>\
							<input type='file' name='file' required>\
						</div>\
					</div>";
		var txt2 ="<div class='form-group'>\
						<label class='col-sm-3 control-label no-padding-right'\
						 for='form-input-readonly'>参观类型标题(必填)</label>\
						<div class='col-sm-9'>\
						<input type='text' class='col-xs-10 col-sm-5' required pattern='.{1,255}' \
						id='form-input-readonly' value='' name='title' />\
						<span	class='help-inline col-xs-12 col-sm-7'>\
						</span>\
						</div>\
					</div>";
		var txt3 = "<div class='form-group'>\
						<label class='col-sm-3 control-label no-padding-right'\
							for='form-input-readonly'>价格(必填)</label>\
						<div class='col-sm-9'> \
							<input type='text' class='col-xs-10 col-sm-5' \
							required pattern='^\+?(?:[1-9]\d*(?:\.\d{1,2})?|0\.(?:\d[1-9]|[1-9]\d))$' \
									placeholder='价格在0.00-99999.99之间' id='form-input-readonly' \
									name='price' value='' name='description' />\
						</div>\
					</div>\
					<div class='space-4'></div>";
					
					
	  	$("#btn1").click(function(){
	    	$("#btn1").before(txt1);
	  	});
	  	$("#btn2").click(function(){
	    	$("#btn2").before(txt2,txt3);
	  	});
	});
</script>

</html>
