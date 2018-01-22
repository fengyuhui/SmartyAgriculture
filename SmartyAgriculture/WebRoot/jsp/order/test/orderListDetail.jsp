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
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/order/editOrderStatus.css" />

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
									data-toggle="modal"> 订单详情 </a>
							</h4>
							

							<!-- #nav-search -->
							<div class="row">
								<div class="col-xs-12">

									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>订单号</th>
													<th>订单总金额</th>
								
												</tr>
											</thead>
                                            <tbody>
													<tr>
														<td>${orderMaster.orderId }</td>
														<td>${orderMaster.amount }</td>
													</tr>
											</tbody>
										</table>
										</div>
										<div class="table-responsive">
										  <table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>商品id</th>
													<th>商品名称</th>
													<th>单价</th>
													<th>数量</th>
								
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${orderDetailList}" var="item">
													<tr>
														<td>${item.detaiId}</td>
														<td>${item.money}</td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<a href="javascript:void();"
																onclick="dividePage('${allPages}','${currentPage}','first')">&nbsp;
																	首 页 &nbsp;</a>
																	<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','prvious')">&nbsp;&lt;&nbsp;Prev &nbsp;</a>
																<!-- <a
																	href="order/index/${allPages}/${currentPage}/prvious">&nbsp;&lt;&nbsp;
																	Prev &nbsp;</a> -->
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
							
						<div class="clearfix form-actions">
						  <div class="col-md-offset-3 col-md-9">
							&nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="goBack()">
								<i class="icon-undo bigger-110"></i> 返回
							</button>
						  </div>
					   </div>
					   <script src="<%=request.getContextPath()%>/js/base.js"></script>
	                   <script src="<%=request.getContextPath()%>/js/common.js"></script>
					
					
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
	<script src="<%=request.getContextPath()%>/js/order/orderList.js"></script>
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
