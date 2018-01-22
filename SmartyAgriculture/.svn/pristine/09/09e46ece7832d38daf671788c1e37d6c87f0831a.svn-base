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
				添加管理员信息 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="adminUser/addAdmin"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">管理员名称(必填)</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="" name="name" /> <span
								class="help-inline col-xs-12 col-sm-7"> 
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">密码</label>

						<div class="col-sm-9">
							<input type="password" class="col-xs-10 col-sm-5"
								id="form-input-readonly" name="passwd" value="" name="description" />							
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">超级管理员权限</label>

						<div class="col-sm-9">					
						 <c:forEach items="${authList}" varStatus="i" var="item" >  
							<c:if test="${item.type==1}">       
								<input type="checkbox" name="autherList[]" value='${item.id}' />${item.title}
								<br>							
                            </c:if>
						 </c:forEach>  
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">审核员</label>

						<div class="col-sm-9">					
						 <c:forEach items="${authList}" varStatus="i" var="item" >  
							<c:if test="${item.type==2}">       
								<input type="checkbox" name="autherList[]" value='${item.id}' />${item.title}
								<br>							
                            </c:if>
						 </c:forEach>  
						</div>
					</div>
					<div class="space-4"></div>
						<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">操作员</label>

						<div class="col-sm-9">					
						 <c:forEach items="${authList}" varStatus="i" var="item" >  
							<c:if test="${item.type==3}">       
								<input type="checkbox" name="autherList[]" value='${item.id}' />${item.title}
								<br>							
                            </c:if>
						 </c:forEach>  
						</div>
					</div>
					<div class="space-4"></div>

					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="button" onclick="window.location.href='adminUser/index/0/1/prvious'">
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

</html>
