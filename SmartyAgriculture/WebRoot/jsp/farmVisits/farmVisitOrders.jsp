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

<title>中远农庄</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/order/editOrderStatus.css" />
<script src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/farmVisits/farmVisitOrder.js"></script>
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
									data-toggle="modal">参观农场订单列表 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<div class="nav-search" id="nav-search">
								<form class="form-search" id="exportExcel">
									<span class="span_margin">参观时间:</span> <input
										class="m-wrap span3" type="text" id="starttime"
										data-date-format="yyyy-mm-dd" readonly size="16" /> <span
										class="add-on"><i class="icon-calendar"></i> </span> 至 <input
										class="m-wrap span3" type="text" id="endtime"
										data-date-format="yyyy-mm-dd" readonly size="16" /> <span
										class="add-on"><i class="icon-calendar"></i> </span> <input
										type="button" class="submit_button" value="提  交"
										onclick="orderTimeChange()">

									<div style="float: right;"><a href="javascript:void(0);" onclick="exportExcel()" style="font-size:16px;">导出excel</a></div> 	
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
													<th>订单号</th>
													<th>价格</th>
													<th>数量</th>
													<th>参观时间</th>
													<th>参观项目名称</th>
													<th>参观人姓名</th>
													<th>联系方式</th>
													<th>参观留言</th>
													<th class="hidden-480">订单状态</th>


												</tr>
											</thead>

											<tbody>
												<c:forEach items="${orderView}" var="item">
													<tr>
														<td>${item.id}</td>
														<td>${item.money}</td>
														<td>${item.num}</td>
														<td><fmt:formatDate type="both" value="${item.visitTime}" /></td>
														<td>${item.title}</td>
														<td>${item.name}</td>
														<td>${item.phone}</td>
														<td>${item.message}</td>
														<td class="hidden-480">
														<c:if test="${item.orderStatus==1}">
																<span class="label label-sm label-warning">未付款</span>
														</c:if>
														<c:if test="${item.orderStatus==2 ||item.orderStatus==3||item.orderStatus==4}">
															<select id="status" onchange="updateOrderStatus(${item.id},this)">
																<option value="2">已付款</option>
																<option value="3">已完成体验</option>
																<option value="4">已退款</option>
															</select>
															<script type="text/javascript">
																$("#status option[value='${item.orderStatus}']").attr("selected",true);
															</script>
														</c:if> 
														</td>


													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
																<a href="javascript:void();"
																onclick="dividePage('${allPages}','${currentPage}','first')">&nbsp;
																	首 页 &nbsp;</a> <a
																	href="order/index/${allPages}/${currentPage}/prvious">&nbsp;&lt;&nbsp;
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

	<!--[if !IE]> -->



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
		src="<%=request.getContextPath()%>/css/assets/js/bootstrap-datetimepicker.js"></script>
	<script
		src="<%=request.getContextPath()%>/css/assets/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
	
	<script type="text/javascript">
	var starttime2=${starttime};
	var endtime2=${endtime};
	
    $(function(){
	 $("#starttime").datetimepicker({
	  format:"yyyy-mm-dd",
	  showMeridian:true,
	  autoclose:true,
	  language:"zh-CN",
	  pickDate:true,
	  minView:2,
	  pickTime:true,
	  todayBtn:true
	 }).on('changeDate',function(ev){	 
	  var starttime=$("#starttime").val();
	  var endtime=$("#endtime").val();
	  if(endtime!=""){
		  if(!checkEndTime(starttime,endtime)){
					$("#endtime").val('');
					alert("开始时间大于结束时间");
					return ;
			}
	  }
	  console.log(starttime);
	  $("#endtime").datetimepicker('setStartDate',starttime);
	  });
	  
	 $("#endtime").datetimepicker({
		  format:"yyyy-mm-dd",
		  showMeridian:true,
		  autoclose:true,
		  language:"zh-CN",
		  pickDate:true,
		  minView:2,
		  pickTime:true,
		  todayBtn:true
	 }).on('changeDate',function(ev){
	     var starttime=$("#starttime").val();
		 var endtime=$("#endtime").val();
		 if(starttime!=""&&endtime!=""){
			if(!checkEndTime(starttime,endtime)){
				$("#endtime").val('');
				alert("开始时间大于结束时间");
				return ;
			}
		 }
		 $("#starttime").datetimepicker('setEndDate',endtime);
		 $("#starttime").datetimepicker('hide');
	 });
	 if(starttime2!=0){
		 starttime2=starttime2.replace("年","-");
			starttime2=starttime2.replace("月","-");
			starttime2=starttime2.replace("日","");
			endtime2=endtime2.replace("年","-");
			endtime2=endtime2.replace("月","-");
			endtime2=endtime2.replace("日","");
		 $("#starttime").val(starttime2);
		// $("#starttime").datetimepicker('update',new Date());
		 $("#endtime").val(endtime2);
		 //$("#endtime").datetimepicker('update',new Date());
	 }
	 
	});
	//比较开始时间和结束时间
	function checkEndTime(starttime,endtime){
		starttime=starttime.replace("年","-");
		starttime=starttime.replace("月","-");
		starttime=starttime.replace("日","");
		endtime=endtime.replace("年","-");
		endtime=endtime.replace("月","-");
		endtime=endtime.replace("日","");
		var start=new Date(starttime.replace("-", "/").replace("-", "/"));
		var end=new Date(endtime.replace("-", "/").replace("-", "/"));
		if(end<start){
		return false;
		}
		return true;
    }	
</script>
</body>
</html>
