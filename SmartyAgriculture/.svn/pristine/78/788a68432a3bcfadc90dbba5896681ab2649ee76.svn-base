<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<!-- page specific plugin styles -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap-editable.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />

</head>

<body style="    background-color: #fff;">
<div class="page-content">
<div class="page-header">
							<h1>
								编辑用户信息
								<small>
									<i class="icon-double-angle-right"></i>
								</small>
							</h1>
						</div><!-- /.page-header -->
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<form class="form-horizontal" action="user/update/${user.id}" method="post" enctype="multipart/form-data">
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-input-readonly">用户编号 </label>

										<div class="col-sm-9">
											<input readonly="" type="text" class="col-xs-10 col-sm-5" id="form-input-readonly" name="userId" value="${user.id}" />
											<span class="help-inline col-xs-12 col-sm-7">
												<label class="middle">
													<input class="ace" type="checkbox" id="id-disable-check" />
												</label>
											</span>
										</div>
									</div>
									<div class="space-4"></div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 用户名</label>

										<div class="col-sm-9">
											<input type="text" id="form-field-1" name="userName" placeholder="${user.name}" class="col-xs-10 col-sm-5" />
										</div>
									</div>

									<div class="space-4"></div>

									<!--  <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> Password Field </label>

										<div class="col-sm-9">
											<input type="password" id="form-field-2" name="password"  placeholder="Password" class="col-xs-10 col-sm-5" />
											<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle">Inline help text</span>
											</span>
										</div>
										
									</div>
									-->
									<div class="space-4"></div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-4">手机号码</label>

										<div class="col-sm-9">
											<input class="input-sm" type="text" id="form-field-4" name="phoneNumber"  placeholder="${user.phone}" />
											<div class="space-2"></div>

											<div class="help-block" id="input-size-slider"></div>
										</div>
									</div>

				
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="icon-ok bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset" onclick="goBack()">
												<i class="icon-undo bigger-110"></i>
												取消
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
