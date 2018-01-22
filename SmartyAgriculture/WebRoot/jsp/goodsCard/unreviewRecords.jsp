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
	String url = "/goodsCard/unreviewed/";
	request.setAttribute("url", url);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>
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
									<a href="goodsCard/reviewed/0/1/prvious"  class="green" > <span>已审核</span> </a>
									/
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="goodsCard/unreviewed/0/1/prvious"  class="green" > <span style="font-size:26px;color:#438eb9" >未审核</span> </a>
								</h4>							
								<div class="hr hr-18 dotted hr-double"></div>

						</div><!-- #nav-search -->
								<div class="row">
									<div class="col-xs-12">
										<form id="form" action="goodsCard/doReview" method="post">
										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead class="table-header">
													<tr>
														<th class="center">id</th>
														<th>面值</th>
														<th>张数</th>
														<th>操作人</th>
														<th>操作时间</th>														
														<th>审核</th>																								
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${records}" var="item">
													<tr>
														<td class="center">
															${item.getId()}
														</td>
														<td>
															${item.getDenomination()}
														</td>
														<td>
															${item.getCardNum()}
														</td>
														<td>
															${item.getUserName()}
														</td>
														<td>
															<fmt:formatDate type="both" value="${item.getOper_time()}" />
														</td>
														<td>
															<input type="checkbox" name="review" value="${item.getId()}" />
														</td>
													</tr>
												</c:forEach>
												<tr>
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
		<c:if test="${! empty records}">
        	<input type="submit" class="btn btn-info btn-block" value="审核" id="sub"/> 
    	</c:if>
    	</form>
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

<script type="text/javascript">
$("#sub").click(function(){
 if($("input[type=checkbox]:checked").length == 0) {
  alert("在提交前请至少选中一项要审核的记录！")
  return false;
 }
 
})
</script>
		

		<!-- <![endif]-->

		<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->
		
		<script src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/goodsCard/common.js"></script>
    <script type="text/javascript">
    	$("#form").submit(function() {
    		$("input[type=submit]").attr("disabled",true);
    	});
    	
    </script>		
  </body>
</html>
