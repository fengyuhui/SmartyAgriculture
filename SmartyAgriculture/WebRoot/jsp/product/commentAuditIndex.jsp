<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/product/editSaleStatus.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/jquery.datetimepicker.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/product/comment.css" />
</head>
<body>
	<div id="gray"></div>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<div class="main-content" style="margin-left:0px;">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div class="hr hr-18 dotted hr-double"></div>
							<h4 class="pink">
								<a
									href="<%=request.getContextPath()%>/comment/commentIndex/0/1/prvious"
									role="button" class="green" data-toggle="modal">评论列表 </a>/ <i
									class="icon-hand-right icon-animated-hand-pointer blue"></i><a
									href="" role="button" class="green" data-toggle="modal"
									style="color: #438eb9!important;font-size: 26px;"> 审核评论操作 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<!-- #nav-search -->
							<div class="row">
								<div class="col-xs-12">

									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<!--  <th class="center td_max_width"><label> <input
															type="checkbox" class="ace" /> <span class="lbl"></span>
													</label></th>
													-->
													<th>评论编号</th>
													<th>商品名称</th>
													<th>用户名称</th>
													<th>是否匿名</th>
													<th><i class="icon-time bigger-110 hidden-480"></i> 级别
													</th>
													<th>状态</th>
													<th>浏览次数</th>
													<th style="width:100px">内容</th>
													<th>审核</th>
													<th>管理操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${commentList}" var="item">
													<tr>
														<!--  <td class="center td_max_width"><label> <input
																type="checkbox" class="ace checked_input"
																data-id="${item.id}" /> <span class="lbl"></span>
														</label></td>-->
														<td>${item.id}</td>

														<td>${item.goodsName}</td>
														<td>${item.userName}</td>
														<td class="hidden-480"><c:if
																test="${item.isAnonymous==0}">
																<span class="label label-sm label-success"> 非匿名</span>
															</c:if> <c:if test="${item.isAnonymous==1}">
																<span class="label label-sm label-inverse"> 匿名</span>
															</c:if></td>
														<td><c:choose>
																<c:when test="${item.level==1}">全部</c:when>
																<c:when test="${item.level==2}">有图</c:when>
																<c:when test="${item.level==3}">好评</c:when>
																<c:when test="${item.level==4}">中评</c:when>
																<c:when test="${item.level==5}">差评</c:when>

															</c:choose></td>
														<td><c:choose>
																<c:when test="${item.commentStatus==0}">可见</c:when>
																<c:when test="${item.commentStatus==1}">隐藏待审核</c:when>
															</c:choose></td>
														<td>${item.viewNum}</td>
														<td><span class="content">${item.content}</span></td>
														<td><label> <input name="form-field-radio"
																type="radio" class="ace" onchange="commentStatusAudit(${item.id},2)"/> <span class="lbl">
																	通过</span>
														</label> <label> <input name="form-field-radio"
																type="radio" class="ace" onchange="commentStatusAudit(${item.id},0)"/> <span class="lbl">
																	拒绝</span>
														</label></td>
														<td>
															<div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看商品信息操作 -->
																<a class="blue" href="comment/commentAuditDetail/${item.id}">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>
															</div>
														</td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
																<a
																	href="comment/commentAuditIndex/${allPages}/${currentPage}/first">&nbsp;
																	首 页 &nbsp;</a> <a
																	href="comment/commentAuditIndex/${allPages}/${currentPage}/prvious">&nbsp;&lt;&nbsp;
																	Prev &nbsp;</a>
																<%
																	for (int i = currentPage - 2; i <= currentPage + 2
																				&& i <= allPages; i++) {
																			if (currentPage == i) {
																%>
																<span class="current"><%=i%></span>
																<%
																	} else if (i > 0) {
																%>
																<a
																	href="comment/commentAuditIndex/${allPages}/${currentPage}/<%=i%>"><%=i%></a>
																<%
																	}
																		}
																%>
																<a
																	href="comment/commentAuditIndex/${allPages}/${currentPage}/next">&nbsp;Next&nbsp;&gt;&nbsp;</a>
																<a
																	href="comment/commentAuditIndex/${allPages}/${currentPage}/last">&nbsp;尾
																	页&nbsp; </a>
															</div>
														</c:if></td>
												</tr>
											</tbody>
										</table>
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
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>

	<!-- <![endif]-->


	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="<%=request.getContextPath()%>/js/product/comment.js"></script>
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
