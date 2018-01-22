<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />

  </head>
  
 <body>
		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<div class="main-content" style="margin-left:0px;">
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="hr hr-18 dotted hr-double"></div>
								<h4 class="pink">
									<a href="<%=request.getContextPath()%>/block/index/0/1/prvious" role="button" class="green" data-toggle="modal"> 土地列表 </a>/
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
							    <a href="#modal-table" role="button" class="green"
									data-toggle="modal"style="color: #438eb9!important;font-size: 26px;"> 审核土地操作 </a>
							
								</h4>
								<div class="hr hr-18 dotted hr-double"></div>
								<!-- <div class="nav-search" id="nav-search">
							  <form class="form-search">
								<span class="span_margin">用户名：</span></span><span class="input-icon">
									<input type="text" placeholder="${userName}" class="nav-search-input" id="userName-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
								<span class="span_margin">手机号：</span></span><span class="input-icon">
									<input type="text" placeholder="${phoneNumber}" class="nav-search-input" id="phoneNumber-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
								<input type="button" id="submit" class="submit_button" value="提交" onclick="searchByuserNameAndphoneNumber()">
							</form>
							
						</div> --> <!-- #nav-search -->
								<div class="row">
									<div class="col-xs-12">
										
										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead class="table-header">
													<tr>
														<th class="center">土地编号</th>
														<th>土地种植类型</th>
														<th>管理员名称</th>
														<th>余量</th>
														<th>编辑时间</th>
														<th class="hidden-480">类型</th>
														<th>管理操作</th>
													</tr>
												</thead>

												<tbody>
													<c:forEach items="${blockList}" var="item">
														<tr>
														<td class="center">
															${item.getBlockId()}
														</td>

														<td>
															${item.getGoodName() }
														</td>
														<td>${item.getManagerName() }</td>
														<td>${item.getStock() }</td>
														<td><fmt:formatDate value="${item.getCreateDate()}" type="both" /></td>
														<td>
														<c:if test="${item.getAuditStatus()==1}">
														添加
														</c:if>
														<c:if test="${item.getAuditStatus()==2}">
														编辑
														</c:if>
														<c:if test="${item.getAuditStatus()==4}">
														删除
														</c:if>
														</td>
														<td>
															<div class="visible-md visible-lg hidden-xs action-buttons">
																<!-- 查看土地信息操作 -->
																<a class="blue" href="blockAudit/auditBlockDetail/${item.getBlockId()}">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>
															</div>
														</td>
													</tr>
													</c:forEach>
												<tr >
					<td colspan="10" style="text-align:left">
						<c:if test="${allPages>1}">
							<div align="right" class="viciao">
								<a href="javascript:void();" onclick="dividePage('${allPages}','${currentPage}','first')">&nbsp; 首 页 &nbsp;</a>
								 <a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','prvious')">&nbsp;&lt;&nbsp; Prev &nbsp;</a>
								<%
									for (int i = currentPage - 2; i <= currentPage + 2
												&& i <= allPages; i++) {
											if (currentPage == i) {
								%>
								<span class="current"><%=i%></span>
								<%
											} else if (i > 0) {
								%>
								<a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','<%=i%>')"><%=i%></a>
								<%
											}
									}
								%>
								<a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','next')">&nbsp;Next&nbsp;&gt;&nbsp;</a> <a
									href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','last')">&nbsp;尾 页&nbsp; </a>
							</div>
						</c:if>
						
					</td>
				</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--
		 "http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"
		-->
		<!--[if !IE]> -->
		
		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

		<!-- <![endif]-->


		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/block/blockIndex.js"></script>
   </script>
</body>
</html>
