<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	//本页面url，翻页用
	String url = "/camera/index/";
	request.setAttribute("url", url);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>广告页</title>
    
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
									<a href="camera/add" role="button" class="green" data-toggle="modal"> 添加摄像头 </a>
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
														<th class="center">编号</th>
														<th>摄像头标题</th>
														<th>IP地址</th>														
														<th>端口</th>
														<th>登录名</th>
														<th>设备密码</th>
														<th>设备端口</th>
														<th>是否置顶</th>
														<th>管理操作</th>
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${cameraList}" var="item">
													<tr>
														<td class="center">
															${item.id}
														</td>

														<td>
															<a >${item.title} </a>
														</td>
														<td>
															<a >${item.ip} </a>
														</td>														
														<td>
															<a >${item.port} </a>
														</td>
														<td>
															<a >${item.name} </a>
														</td>
														<td>
															<a >${item.passwd } </a>
														</td>
														<td>
															<a >${item.device_port } </a>
														</td>
														<td>
															<select onchange="changeTop(${item.id},this)">
																<option value='1'  <c:if test="${item.is_top == 1}">selected="selected"</c:if> >已置顶</option>
																<option value='0' <c:if test="${item.is_top == 0}">selected="selected"</c:if> >未置顶</option>
															</select>
														</td>																											
														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看信息操作 -->
																<a class="blue" href="camera/find?id=${item.id}">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>
																<!-- 编辑信息操作 -->
																<!--
																<c:if test="${item.status ==0}">															
																<a class="green" href="camera/update?id=${item.id}">
																	<i class="icon-pencil bigger-130"></i>
																</a>		
																</c:if>
																-->
																<!-- 删除 -->
																<a class="green" href="camera/delete?cameraId=${item.id}">
																	<i class="icon-remove bigger-130"></i>
																</a>
															</div>
														</td>
													</tr>
												</c:forEach>
												<tr >
					<td colspan="10" style="text-align:left">
						<c:if test="${allPages>1}">
							<div align="right" class="viciao">
								<a href="javascript:void();" onclick="dividePage('${allPages}','${currentPage}','first','${url}')">&nbsp; 首 页 &nbsp;</a>
								 <a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','prvious','${url}')">&nbsp;&lt;&nbsp; Prev &nbsp;</a>
								<%
									for (int i = currentPage - 2; i <= currentPage + 2
												&& i <= allPages; i++) {
											if (currentPage == i) {
								%>
								<span class="current"><%=i%></span>
								<%
											} else if (i > 0) {
								%>
								<a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','<%=i%>','${url}')"><%=i%></a>
								<%
											}
									}
								%>
								<a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','next','${url}')">&nbsp;Next&nbsp;&gt;&nbsp;</a> <a
									href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','last','${url}')">&nbsp;尾 页&nbsp; </a>
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
		<!-- "http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"-->


		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");		
			$(function() {  
				var resultMsg = '${resultMsg}';
				if(resultMsg!='')
					alert(resultMsg);
			});  
			
			function changeTop(id,selectElement){
				var top = $(selectElement).val();
				$.post("camera/top", 
				{ "cameraId": id, "topId": top },
			    function(data){				
					alert(data['resultMsg']);
			    });								
			}
		</script>
		<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/adminUser/common.js"></script>
   </script>
</body>
</html>
