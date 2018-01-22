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
	String url = "/goodsCard/reviewedDetails/";
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
								<div class="hr hr-18 dotted hr-double"></div>
								<h4 class="pink">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="goodsCard/reviewed/0/1/prvious"  class="green" > 已审核 </a>
									&nbsp;&nbsp;&nbsp;
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="goodsCard/unreviewed/0/1/prvious"  class="green" > 未审核 </a>
								</h4>							
								<div class="hr hr-18 dotted hr-double"></div>

						</div><!-- #nav-search -->
								<div class="row">
									<div class="col-xs-12">	
										<div class="table-responsive">
										<div style="float: right;"><a href="goodsCard/exportExcel/${id}" style="font-size:16px;">导出excel</a></div>
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead class="table-header">
													<tr>
														<th class="center">卡号</th>
														<th class="center">密码</th>
														<th class="center">面值</th>																						
													</tr>

												</thead>

												<tbody class="center">
												<c:forEach items="${records}" var="item">
													<tr>
														<td>
															${item.number}
														</td>
														<td >
															${item.passwd}
														</td>
														<td>
															${item.money}
														</td>
													</tr>
												</c:forEach>
												<tr >
					<td colspan="10" style="text-align:left">
						<c:if test="${allPages>1}">
							<div align="right" class="viciao">
								<a href="javascript:void();" onclick="dividePage('${allPages}','${currentPage}','first','${url}',${id})">&nbsp; 首 页 &nbsp;</a>
								 <a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','prvious','${url}',${id})">&nbsp;&lt;&nbsp; Prev &nbsp;</a>
								<%
									for (int i = currentPage - 2; i <= currentPage + 2
												&& i <= allPages; i++) {
											if (currentPage == i) {
								%>
								<span class="current"><%=i%></span>
								<%
											} else if (i > 0) {
								%>
								<a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','<%=i%>','${url}',${id})"><%=i%></a>
								<%
											}
									}
								%>
								<a href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','next','${url}',${id})">&nbsp;Next&nbsp;&gt;&nbsp;</a> <a
									href="javascript:void();"onclick="dividePage('${allPages}','${currentPage}','last','${url}',${id})">&nbsp;尾 页&nbsp; </a>
							</div>
						</c:if>
						
					</td>
				</tr>
												</tbody>
											</table>
							<a class="btn btn-link" href="goodsCard/reviewed/0/1/prvious">返回</a>
							<input type="hidden" value="${id}">
										</div>
									</div>
								</div>
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->


		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

		<!-- <![endif]-->
		
		<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->
		
		<script src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/goodsCard/reviewedDetail.js"></script>
<%-- 
<script>
function exportExcel(){
	var id = $("input[type=hidden]").val();
	alert(id);
    var src = "goodsCard/exportExcel/"+id;
	  $.ajax({ 
	  type:"POST", 
	  url:src,
      contentType : "application/json",
	  success: function(data,response){ 
			alert("文件已成功下载到桌面");
			alert(data);
	  },  
	 error: function(data)   
	 {  
		 alert("文件下载失败，请稍后重试");
	 }
	});
}
</script>--%>
  </body>
</html>
