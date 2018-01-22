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
<!-- ueditor -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js">	
</script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/product/area.js">	
</script>
</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
			  商品进货 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="product/inputProductPost"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">商品名称</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="${goods.name}" name="name" readonly="readonly"/> <span
								class="help-inline col-xs-12 col-sm-7" >
							</span>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
					    <input value="${goods.id }" type="hidden" name="goods_id"/>
						<label  class="col-sm-3 control-label no-padding-right"
							for="form-field-1"> 进货数量</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" placeholder="请输入进货数量"
								class="col-xs-10 col-sm-5" name="num" /><span>（单位：${goods.unit}）</span>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="goBack()">
								<i class="icon-undo bigger-110"></i> 返回
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
</html>
