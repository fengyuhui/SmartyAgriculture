<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />

</head>
<c:if test="${!empty oper_res && oper_res eq 'ok' }">
	<script type="text/javascript">
		alert("审核成功");
	</script>

</c:if>
<c:if test="${!empty oper_res && oper_res eq 'fail' }">
	<script type="text/javascript">
		alert("审核失败");
	</script>

</c:if>
<body>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<div class="main-content" style="margin-left:0px;">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="hr hr-18 dotted hr-double"></div>
							<h2 class="pink">
								<i class="icon-hand-right icon-animated-hand-pointer blue">待审信息</i>
							</h2>
							<div class="hr hr-18 dotted hr-double"></div>

						</div>
						<!-- #nav-search -->
						<div class="row">
							<div class="col-xs-12">
								<form action="addition/doReview" method="post">
									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th class="center">id</th>
													<th>title</th>
													<th>操作人</th>
													<th>详情</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${records}" var="item">
													<tr>
														<td class="center">${item.id}</td>
														<td>${item.title}</td>
														<td>${item.name}</td>
														<td><a href="addition/toDetail?id=${item.id}&comeFrom=rev">查看编辑详情</a>
														</td>
														<td><a href="addition/doReview?id=${item.id }"> <i
																class="icon-ok bigger-130"></i>
														</a> <a href="addition/unDo?id=${item.id }"> <i
																class="icon-remove bigger-130"> </i>
														</a></td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"></td>
												</tr>
											</tbody>
										</table>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container-inner -->


	<!-- basic scripts -->

	<!--[if !IE]> -->

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

	<!-- <![endif]-->

	<script
		src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

	<!-- page specific plugin scripts -->

	<script
		src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

	<!-- ace scripts -->

	<script
		src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
	<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>

</body>
</html>
