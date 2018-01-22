<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>编辑附加信息</title>

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
<c:if test="${!empty oper_res && oper_res eq 'ok' }">
	<script type="text/javascript">
		alert("编辑成功，待审核");
	</script>

</c:if>
<c:if test="${!empty oper_res && oper_res eq 'fail' }">
	<script type="text/javascript">
		alert("编辑失败");
	</script>

</c:if>
<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				选择编辑
			</h1>


		</div>
		<div class="table-responsive">
		<table id="sample-table-2"
			class="table table-striped table-bordered table-hover">
			<thead class="table-header">
				<tr>
					<th class="center">id</th>
					<th>title</th>
					<th>查看与编辑</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${records}" var="item">
					<tr>
						<td class="center">${item.id}</td>
						<td>${item.title}</td>
						<td>
							
							<a href="addition/toDetail?id=${item.id}&comeFrom=sss" title="查看详情">
								<span class="icon-zoom-in bigger-130"></span>
							</a>
							<c:if test="${item.status == 0 }">
							<a href="addition/chooseEdit?id=${item.id }" > 
								<span class="icon-pencil bigger-130" title="编辑信息"></span>
							</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="10" style="text-align:left"></td>
				</tr>
			</tbody>
		</table>
		</div>
</div>
<!--  
		<ul class="nav nav-tabs nav-justified" role="tablist">
			<c:forEach var="item" items="${records }">
				<c:if test="${item.status == 0}">
					<li role="presentation"><a
						href="addition/chooseEdit?id=${item.id }">${item.title}</a></li>
				</c:if>
			</c:forEach>
		</ul>
		-->	
</body>
</html>
