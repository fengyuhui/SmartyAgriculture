<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>订单查询</title>

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
							<h4 class="pink" style="text-align: center;font-size: 3em;">
								<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-table" role="button" class="green"
									data-toggle="modal"> 中远农场订单查询 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
						  <div class="nav-search" id="nav-search" style="text-align: center;">
								<form class="form-search" method="post" action="interface/order/getOrderDetailToView">
									<input type="text"  name="orderId" value="" placeholder="请输入您的订单号查询订单"
									style="height: 7%;width: 50% !important;">
									<input type="submit" id="submit" class="submit_button" value="提  交"  style="height:7%">
								</form>
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
	<div class="popup" id="popup">

		<div class="top_nav" id='top_nav'>
			<div align="center">
				<span>填写运单号</span> <a class="guanbi"></a>
			</div>
		</div>

		<div class="min">

			<div class="tc_login">

				<div class="right" align="center">
					<span class="span_all">运单号:</span>
					
						 <input type="text" id="tracking"  name="tracking"/>
					<br>
					<br>
					<div align="center">
						<input type="submit" id="submit_changeSaleStauts" class="button"
							value="提  交" onclick="submitChangeInPop(this)" data-id="" style="margin-top: 0px;">
					</div>
				</div>

			</div>

		</div>
	</div>
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
	<script>
		$(function() {  
			var message = '${message}';
			if(message != ''){
				alert( message );
			}
		});  	
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
	<script
		src="<%=request.getContextPath()%>/js/order/orderList.js"></script>
</body>
</html>
