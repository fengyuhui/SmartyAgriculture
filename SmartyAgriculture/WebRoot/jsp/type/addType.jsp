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
	src="<%=request.getContextPath()%>/js/product/addType.js"></script>
</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				添加商品分类 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="type/addTypePost"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 类别级别 </label>

						<div class="col-sm-9">
							<select name="level" onchange="changeLevel(this)" id="level">
								<option value="0">请选择类别级别</option>
								<option value="1">一级</option>
								<option value="2">二级</option>
							</select>
						</div>
					</div>
					<div class="form-group" style="display:none;" id="parentId_div">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 一级级别 </label>
						<div class="col-sm-9">
							<select name="parentid" id="parentId">
								<option value="0">请选择一级类别</option>
								<c:forEach items="${parentList}" var="item">
									<c:if test="${not (item.id == 2 || item.id == 44)}"> 		
										<option value="${item.id}">${item.name}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">类别名称</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="" name="name" /> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">商品图片</label>

						<div class="col-sm-9">
							<!-- <div id="box">
								<div id="test"></div>
							</div>
							 -->
							<div>
								<div class="line">
									<input type="file" id="first_file" name="file"></input>
								</div>
								<img src="" class="showImage"> <br class="br">
							</div>
						</div>
					</div>

					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id="sm">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
						</div>
					</div>
					<div class="hr hr-24"></div>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
    String.prototype.endsWith=function(endStr){
      var d=this.length-endStr.length;
      return (d>=0&&this.lastIndexOf(endStr)==d)
    }
</script>
<script type="text/javascript">
$("#sm").click(function() {
	var level = $("#level").val();
	var parentId = $("#parentId").val();
	var name = $("#form-input-readonly").val();
	var pic = $("#first_file").val();
	if(name == "") {
		alert("请填写所添加的产品类别的名称再提交！");
		return false;
	}
	if(pic == "" || (!pic.endsWith(".png") && !pic.endsWith(".jpeg") &&!pic.endsWith("jpg")) ) {
		alert("没有上传产品图片或者上传的文件不是所要求的格式！");
		return false;
	}	
	if(level != "1" && level != "2") {
		alert("请选择所要添加的产品的类别级别");
		return false;
	}
	else {
		if(level == "2") {
			if(parentId == ""|| parentId =="0") {
				alert("请选择所要添加的产品的一级类别");
				return false;
			}
		}
	}
});
</script>
</html>
