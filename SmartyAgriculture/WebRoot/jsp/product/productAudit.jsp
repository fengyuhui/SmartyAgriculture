<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<!-- basic styles -->
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
 	
 	<!-- ace styles -->
 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace-skins.min.css" />

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
								
								<a href="<%=request.getContextPath()%>/product/index/0/1/prvious/3/-1/1" role="button" class="green"
									data-toggle="modal" > 商品列表 </a>/
							   <i class="icon-hand-right icon-animated-hand-pointer blue"></i>
							    <a href="#modal-table" role="button" class="green"
									data-toggle="modal"style="color: #438eb9!important;font-size: 26px;"> 审核商品操作 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<div class="row">
								<div class="col-xs-12">

									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>商品编号</th>
													<th>商品名称</th>
													<th>价格</th>
													<th>库存</th>
													<th>编辑时间
													</th>

													<th>商品父类型</th>
													<th>商品子类型</th>
													<th class="hidden-480">类型</th>
													<th>管理操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${goodsList}" var="item">
													<tr>
														<td>${item.id}</td>

														<td><a href="javascript:void(0)">${item.name} </a></td>
														<td>${item.price}</td>
														<td>${item.stock}</td>
														<td><fmt:formatDate value="${item.createTime}" type="both" /></td>
														<td>${item.typeParent}</td>
														<td>${item.typeChild}</td>
														<td>
														<c:if test="${item.auditStatus==1}">
														添加
														</c:if>
														<c:if test="${item.auditStatus==2}">
														编辑
														</c:if>
														<c:if test="${item.auditStatus==4}">
														删除
														</c:if>
														</td>
														<td>
															<div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看商品信息操作 -->
																<a class="blue"
																	href="productAudit/getProductDetail/${item.id}"> <i
																	class="icon-zoom-in bigger-130"></i>
																</a>
															</div>
														</td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
																<a href="productAudit/auditIndex/${allPages}/${currentPage}/first"
																	>&nbsp;
																	首 页 &nbsp;</a> <a href="productAudit/auditIndex/${allPages}/${currentPage}/prvious"
																	>&nbsp;&lt;&nbsp;
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
																<a href="productAudit/auditIndex/${allPages}/${currentPage}/<%=i%>"
												><%=i%></a>
																<%
																	}
																		}
																%>
																<a href="productAudit/auditIndex/${allPages}/${currentPage}/next"
																	>&nbsp;Next&nbsp;&gt;&nbsp;</a>
																<a href="productAudit/auditIndex/${allPages}/${currentPage}/last"
																	>&nbsp;尾
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
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>

	<!-- <![endif]-->


	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
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
	<script src="<%=request.getContextPath()%>/js/product/productIndex.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.datetimepicker.js"></script>
	<script>
		jQuery(function($) {
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});
		});
	</script>
</body>
</html>
