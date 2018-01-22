<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>添加管理员</title>

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
				发布轮播图 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="advertise/addPost"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">标题</label>
						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" name="title" value="" name="description" style="width:30%"/>							
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">轮播图类型</label>

						<div class="col-sm-9">
							<select name="type" name="type" id="type">
								<option value="-1">----请选择轮播图类型----</option>
								<option value="1">特价</option>								
								<option value="2">限时商品</option>
								<option value="3">新品</option>
								<option value="4">热卖</option>
								<option value="5">我们农场</option>
								<option value="6">生态技术</option>
								<option value="7">参观农场</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">是否置顶</label>
						<div class="col-sm-9">
							<select name="top">
								<option value="0">否</option>								
								<option value="1">是</option>
							</select>
						</div>
					</div>
						<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">轮播图</label>

						<div class="col-sm-9">
							<input type="file" name="file" id="first_file" required="true">
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id="judge">
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
$("#judge").click(function() {
	var name = $("#form-input-readonly").val();
	var type = $("#type").val();
	var pic = $("#first_file").val();
	
	if(name == "") {
		alert("请填写标题！");
		return false;
	}
	if(type == "" || type == "-1") {
		alert("请选择信息类型！");
		return false;
	}
	if(pic == "" || (!pic.endsWith(".png") && !pic.endsWith(".jpeg") &&!pic.endsWith("jpg")) ) {
		alert("没有上传图片或者图片不是所要求的格式！");
		return false;
	}
});
</script>
</html>
