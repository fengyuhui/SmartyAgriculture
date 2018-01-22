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

<title>智慧农业</title>

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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/product/addProduct.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/product/addProduct.js"></script>


</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				编辑商品信息 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="type/saveTypeEdit"
					method="post" enctype="multipart/form-data">
					<input value="${typeDetail.id}" style="display:none" name="id"></input>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">类型名称</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="${typeDetail.name}" name="name" placeholder="${goodsList.name}"/> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<!-- 
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">类型级别</label>

						<div class="col-sm-9">
							<select name="level" id="level" data-default="${typeDetail.level}">
							
								<option value="1" <c:if test="${typeDetail.level==1}"> selected="selected"</c:if>>一级</option>								
								<option value="2" <c:if test="${typeDetail.level==2}"> selected="selected"</c:if>>二级</option>	
							</select>
						</div>
					</div>
	 					-->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">类型图片</label>

						<div class="col-sm-9">
							<div>
								<div class="line">
										<input type="file" id="first_file" name="typePicture"></input>
								</div>
								<img src=""  class="showImage">
							</div>
						</div>
					</div>

					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id="sm">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="goBack()">
								<i class="icon-undo bigger-110"></i> 取消
							</button>
						</div>
					</div>

					<div class="hr hr-24"></div>

				</form>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/base.js"></script>
</body>
<script type="text/javascript">
$("#sm").click(function() {
	var name = $("#form-input-readonly").val();
	if(name == "") {
		alert("请填写所修改的产品类别的名称再提交！");
		return false;
	}

});
</script>
</html>
