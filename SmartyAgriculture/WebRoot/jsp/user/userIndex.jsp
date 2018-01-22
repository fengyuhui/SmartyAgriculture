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
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="#modal-table" role="button" class="green" data-toggle="modal"> 会员列表 </a>
								</h4>
								<div class="hr hr-18 dotted hr-double"></div>
								<div class="nav-search" id="nav-search">
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
						</div><!-- #nav-search -->
								<div class="row">
									<div class="col-xs-12">
										
										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead class="table-header">
													<tr>
														<th class="center">用户编号</th>
														<th>用户名</th>
														<th>手机号</th>
														<th class="hidden-480">账户余额</th>
														<th>
															<i class="icon-time bigger-110 hidden-480"></i>
															积分
														</th>
														<th>用户状态</th>
														<th>管理操作</th>
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${userList}" var="item">
														<tr>
														<td class="center">
															${item.id}
														</td>

														<td>
															<a href="javascript:void(0);">${item.name} </a>
														</td>
														<td>${item.phone}</td>
														<td class="hidden-480">${item.money}</td>
														<td>${item.score}</td>
														<td>
														<select name="status" onchange="updataUserStatus('${item.id}',this)">
														<option value="0" <c:if test="${item.status==0}"> selected="true"</c:if>>正常</option>
														<option value="1" <c:if test="${item.status==1}"> selected="true"</c:if>>禁用</option>
														</select>
														</td>
														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看用户信息操作 -->
																<a class="blue" href="user/detail/${item.id}/0/1/prvious">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>
																<!-- 编辑用户信息操作 -->
																<a class="green" href="user/edit/${item.id}">
																	<i class="icon-pencil bigger-130"></i>
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
		<script src="<%=request.getContextPath()%>/js/user/userIndex.js"></script>
   </script>
</body>
</html>
