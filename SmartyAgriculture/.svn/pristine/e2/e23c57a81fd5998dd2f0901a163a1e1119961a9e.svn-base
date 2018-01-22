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

<title>农场相册页</title>

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
				修改相册图 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="album/updatePost"
					method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value='${album.id}' >
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">相册标题</label>
						<div class="col-sm-9">
							${album.photoName}						
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">是否置顶</label>
						<div class="col-sm-9">
							<c:if test="${album.top ==1}">
							  已置顶
							</c:if>
							<c:if test="${album.top ==0}">
								未置顶
							</c:if>
						</div>
					</div>
						<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">相册图</label>

						<div class="col-sm-9">
							<img src="${album.pictures}"}>
						</div>
					</div>
					<div class="space-4"></div>


					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn" type="reset" onclick="onclick=history.go(-1);">
								<i class="icon-undo bigger-110"></i> 返回
							</button>
						</div>
					</div>

					<div class="hr hr-24"></div>

				</form>
			</div>
		</div>
	</div>
</body>
<script>
	$(function(){
		
		//选择默认值
		var topValue = ${album.top};
		$("#top option[value="+topValue+"]").attr("selected", true);
	});
</script>
</html>
