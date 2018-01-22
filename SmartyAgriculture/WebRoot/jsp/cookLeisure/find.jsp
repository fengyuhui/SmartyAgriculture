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

<title>休闲美食信息详情</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- page specific plugin styles -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap-editable.css" />
<!--	
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
-->	
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/find.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/album/album.css" />

<script src="<%=request.getContextPath()%>/js/album/album.js"
	type="text/javascript"></script>

</head>

<body style="    background-color: #fff;">
	<div class="page-content">
		<div class="page-header">
			<h1>
				<!--休闲美食信息详情 <small> <i class="icon-double-angle-right"></i></small></h1>-->
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<div>
					<div id="user-profile-1" class="user-profile row">

						<div class="col-xs-12 col-sm-9">

							<div class="space-12"></div>

							<div class="profile-user-info profile-user-info-striped">
								<div class="profile-info-row">
									<!--<div class="profile-info-name">标题</div>-->

									<div class="profile-info-value">
										<span class="editable" id="messageName">${messageName}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<!--<div class="profile-info-name">简介</div>-->

									<div class="profile-info-value">
										<span class="editable" id="subTitle">${subTitle}</span>
									</div>
								</div>
								<!--<div class="profile-info-row">
									<div class="profile-info-name">信息状态</div>

									<div class="profile-info-value">
										<span class="editable" id="status">${status}</span>
									</div>
								</div>
								-->
								<div class="profile-info-row">
									<!--<div class="profile-info-name">美食专家</div>-->

									<div class="profile-info-value">
										<span class="editable" id="dietitian">${dietitian}</span>
									</div>
								</div>
								<!--
								<div class="profile-info-row">
									<div class="profile-info-name">图标</div>

									<div class="profile-info-value">
										<img src="${icon}" class="type_icon" />
									</div>
								</div>
								-->
								<div class="profile-info-row">
									<!--<div class="profile-info-name">信息详情</div>-->

									<div class="profile-info-value">
										<span class="editable" id="currentMessage">${currentMessage}</span>
									</div>
								</div>
							</div>

					<div class="space-4"></div>

					<!-- <div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn" type="reset" onclick="onclick=history.go(-1);">
								<i class="icon-undo bigger-110"></i> 返回
							</button>
						</div>
					</div> -->

							<div class="space-20"></div>
						</div>
					</div>
				</div>
				<br>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/base.js"></script>
	<script src="<%=request.getContextPath()%>/js/common.js"></script>
	<script src="<%=request.getContextPath()%>/js/product/ueditor.js"></script>
</body>
</html>
