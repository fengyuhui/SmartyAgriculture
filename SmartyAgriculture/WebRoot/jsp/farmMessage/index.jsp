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
	String url = "/farmMessage/index/";
	request.setAttribute("url", url);
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
								<h4 class="pink">
									
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="farmMessage/judge/0/1/prvious" role="button" class="green" data-toggle="modal"> 信息审核列表 </a>
								</h4>
								<!--<div class="hr hr-18 dotted hr-double"></div>
								<h4 class="pink">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="farmMessage/add" role="button" class="green" data-toggle="modal"> 添加农场信息 </a>
									&nbsp;&nbsp;&nbsp;
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="farmMessage/judge/0/1/prvious" role="button" class="green" data-toggle="modal"> 信息审核列表 </a>
								</h4>		-->					
								<!--<div class="hr hr-18 dotted hr-double"></div>
								<form class="form-search">
								<span class="span_margin">标题：</span></span><span class="input-icon">
									<input type="text" placeholder="${title}" class="nav-search-input" id="title-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
								<input type="button" id="submit" class="submit_button" value="提交" onclick="searchByuserNameAndphoneNumber()">
							</form> -->
						</div><!-- #nav-search -->
								<div class="row">
									<div class="col-xs-12">
										
										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead class="table-header">
													<tr>
														<th class="center">农场信息编号</th>
														<th>标题</th>
														<th>信息类型</th>
														
														<th>创建时间</th>
														<th>状态</th>																									
														<th>管理操作</th>
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${farmMessageList}" var="item">
													<tr>
														<td class="center">
															${item.id}
														</td>

														<td>
															<a >${item.messageName} </a>
														</td>
														<td>
															<c:if test="${item.messageType ==1}">
															 我们农场
															</c:if>
															<c:if test="${item.messageType ==2}">
															 生态技术
															</c:if>
															<c:if test="${item.messageType ==3}">
															 休闲活动
															</c:if>
														</td>
														<!--<td>
															<select onchange="changeTop(${item.id},this)">
																<option value='1'  <c:if test="${item.topOrNot == 1}">selected="selected"</c:if> >已置顶</option>
																<option value='0' <c:if test="${item.topOrNot == 0}">selected="selected"</c:if> >未置顶</option>
															</select>
														</td> -->
														
														<td><fmt:formatDate value="${item.creatTime}" type="both" /></td>
														
														<td>
															<c:if test="${item.status ==0}">
															 正常 
															</c:if>
															<c:if test="${item.status ==3}">
															 正常有草稿
															</c:if>
														</td>
																											
														<td>
															<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看农场信息操作 -->
																<a class="blue" href="farmMessage/find?id=${item.id}">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>
																<c:if test="${item.status ==0}">
																<!-- 编辑农场信息操作 -->
																<a class="green" href="farmMessage/update/${item.id}">
																	<i class="icon-pencil bigger-130"></i>
																</a>
																</c:if>
																<!-- 删除 
																<a class="green" href="farmMessage/deleteRequest?id=${item.id}">
																	<i class="icon-remove bigger-130"></i>
																</a>-->
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

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

		<!-- <![endif]-->


		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");		
			$(function() {  
				var resultMsg = '${resultMsg}';
				if(resultMsg!='')
					alert(resultMsg);
			});  
			
			function changeTop(id,selectElement){
				var topOrNot = $(selectElement).val();
				$.post("farmMessage/topOrNot", 
				{ "id": id, "topId": topOrNot },
			    function(data){				
					alert(data['resultMsg']);
			    });								
			}
		</script>
		
		<script src="<%=request.getContextPath()%>/js/farmMessage/farmMessageIndex.js"></script>
		
		<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->
		
		<script src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/base.js"></script>
		<script src="<%=request.getContextPath()%>/js/common.js"></script>
   </script>
</body>
</html>
