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
<style type="text/css">
.type_icon {
	height: 35px;
}
</style>
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
								<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-table" role="button" class="green"
									data-toggle="modal"> 产品分类列表 </a>
								<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
								<a href="type/typeReview" class="green" role="button">产品分类审核</a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<div class="nav-search" id="nav-search">
								<form class="form-search">
									<span class="span_margin">分类级别:</span><select
										name="goodsStatus" class="input_border" id="level_search"
										onchange="selectByType(this)"><option value="0">请选择分类级别</option>
										<option value="1"
											<c:if test="${level==1}"> selected="selected"</c:if>>一级</option>
										<option value="2"
											<c:if test="${level==2}"> selected="selected"</c:if>>二级</option>
									</select> <span class="span_margin">一级级别:</span><select
										name="goodsStatus" class="input_border" id="parentId_search"
										onchange="selectByParent(this)"><option value="0">请选择分类级别</option>
										<c:forEach items="${parentList}" var="item">
											<c:choose>
												<c:when test="${parentId==item.id}">
													<option value="${item.id}" selected="selected">${item.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${item.id}">${item.name}</option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</form>
							</div>
							<!-- #nav-search -->
							<div class="row">
								<div class="col-xs-12">
									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>类别编号</th>
													<th>类别名称</th>
													<th>分类图片</th>
													<th>分类级别</th>
													<th>父类型</th>
													<th>是否显示</th>
													<th>管理操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${goodsTypeList}" var="item">  <!-- goodsTypeList -->
													<tr>
														<td>${item.id}</td>

														<td>${item.name}</td>
														<td><img src="${item.picture}" class="type_icon" /></td>
														<td>${item.level}级</td>
														<td>${item.parentName}</td>
														<Td><select class="status" name="isdisplay"
															onchange="updateTypeIsdisplay('${item.id}',this)">
																<option value="${item.isdisplay}">
																	<c:if test="${item.isdisplay==false}">不显示</c:if>
																	<c:if test="${item.isdisplay==true}">显示</c:if></option>
																<c:if test="${item.isdisplay==true}">
																	<option value="0">不显示</option>
																</c:if>
																<c:if test="${item.isdisplay==false}">
																	<option value="1">显示</option>
																</c:if>
														</select>
														</Td>
														<td>
															<div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 编辑类型信息操作 -->
																<c:if test="${item.status == 0}">
																<a class="green" href="type/jumpToTypeEdit/${item.id}">
																	<i class="icon-pencil bigger-130"></i>
																</a>
																</c:if>
												 				<c:if test="${item.level==1 }"> 
																<!-- 统计商品类型信息 -->
																<a class="blue"
																	href="type/parentTypeStatistics/0/1/prvious/0/0/0/${item.id}"> <i
																	class="icon-bar-chart bigger-130"></i>
																</a>
															  	</c:if>  
																
															</div>
														</td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
																<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','first')">&nbsp;
																	首 页 &nbsp;</a> <a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','prvious')">&nbsp;&lt;&nbsp;
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
	<script src="<%=request.getContextPath()%>/js/product/typeIndex.js"></script>
</body>
</html>
