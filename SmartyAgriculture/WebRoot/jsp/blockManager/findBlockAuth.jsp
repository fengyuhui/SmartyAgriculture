<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	//分页
	//currentPage就是当前页，allPages表示的就是一共有多少页。这些在后面都会用到  
	int currentPage = 0, allPages = 0;
	if (request.getAttribute("currentPage") != null) {
		currentPage = Integer.parseInt(request.getAttribute(
				"currentPage").toString());
	}
	if (request.getAttribute("allPages") != null) {
		allPages = Integer.parseInt(request.getAttribute("allPages")
				.toString());
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>查看区域管理员</title>

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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/album/album.css" />

<script src="<%=request.getContextPath()%>/js/album/album.js"
	type="text/javascript"></script>
</head>

<body style="    background-color: #fff;">
	<div class="page-content">
		<div class="page-header">
			<h1>
				查看区域管理员 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<div>
					<div id="user-profile-1" class="user-profile row">

						<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>区域管理员编号</th>
													<th>名称</th>
													<th style="width:20%">创建时间</th>
								
												</tr>
											</thead>
                                            <tbody>
													<tr>
														<td>${blockManager.getId()}</td>
														<td>${blockManager.getName()}</td>
														<td>${blockManager.getCreateTime()}</td>
													</tr>
											</tbody>
										 </table>
						</div>
						<div class="table-responsive">
										  <table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>地块号</th>
													<th>地块种植类型</th>
													<th>余量</th>
								
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${blocks}" var="block">
													<tr>
														<td>${block.getBlockId()}</td>
														<td>${block.getBlockName()}</td>
														<td>${block.getStock()}</td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<a href="javascript:void();"
																onclick="dividePage('${allPages}','${currentPage}','first')">&nbsp;
																	首 页 &nbsp;</a>
																	<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','prvious')">&nbsp;&lt;&nbsp;Prev &nbsp;</a>
																<!-- <a
																	href="order/index/${allPages}/${currentPage}/prvious">&nbsp;&lt;&nbsp;
																	Prev &nbsp;</a> -->
																<%
																	for (int i = currentPage - 2; i <= currentPage + 2
																				&& i <= allPages; i++) {
																			if (currentPage == i) {
																%>
																<span class="current"><%=i%></span>
																<%
																	} else if (i > 0) {
																%>
																<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','<%=i%>')"><%=i%></a>
																<%
																	}
																		}
																%>
																<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','next')">&nbsp;Next&nbsp;&gt;&nbsp;</a>
																<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','last')">&nbsp;尾
																	页&nbsp; </a>
															</div>
														</c:if></td>
												</tr>
											</tbody>
										</table>
									</div>		

							<div class="space-20"></div>
								<div class="clearfix form-actions">
											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset" onclick="window.location.href='blockManager/index/0/1/prvious'">
												<i class="icon-undo bigger-110"></i>
												返回
											</button>
										</div>
								</div>
						</div>
						
					</div>
				</div>
				<br>
			</div>
		</div>
	</div>
</body>
</html>
