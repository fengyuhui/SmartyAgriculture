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
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">

<title>订单查询</title>


<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">

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
							<h4 class="pink">
								<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
								<a href="#modal-table" role="button" class="green"
									data-toggle="modal"> 订单详情 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
								<form class="form-search" method="post" action="interface/order/getOrderDetailToView">
									<input type="text" name="orderId" value="" placeholder="请输入您的订单号查询订单"
									style="height: 5%;width: 40% !important;">
									<input type="submit" id="submit" class="submit_button" 
							value="提  交"  style="height:5%">
								</form>
							<!-- #nav-search -->
							<div class="row" style="margin-top:5px">
								<div class="col-xs-12">

									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>订单号</th>
													<th>价格</th>
													
													<th>购买时间</th>

													
													<th>收货人</th>
													<th>联系方式</th>
													<th>省市县</th>
													<th>详细地址</th>
													<th class="hidden-480">订单状态</th>
													<th>操作</th>


												</tr>
											</thead>

											<tbody>
													<tr>
														<td><a href="#">${map.orderView.orderId} </a></td>
														<td>${map.orderView.amount}</td>
														<td> <fmt:formatDate value="${map.orderView.buyTime}" type="both" /></td>
														
														<td>${map.address.name}</td>
														<td>${map.address.phone}</td>
														<td>${map.address.province}</td>
														<td>${map.address.detailAddress}</td>
														<td class="hidden-480"><c:if
																test="${map.orderView.status==1}">
																<span class="label label-sm label-warning">未付款</span>
															</c:if> 
												            <c:if
																test="${map.orderView.status==2||map.orderView.status==3}">
																<select name="order_status"
																	onchange="updateOrderStatus(''${map.orderView.orderId},this,${map.orderView.pick_up})"><option
																		value="2"
																		<c:if test="${map.orderView.status==2}"> selected='selected'</c:if>>未发货</option>
																	<option value="3"
																		<c:if test="${map.orderView.status==3}"> selected='selected'</c:if>>已发货</option></select>
															 </c:if> 
															 <c:if test="${map.orderView.status==4}">
																<span class="label label-sm label-warning">已收货</span>
															 </c:if> 
															 <c:if
																test="${map.orderView.status==5||map.orderView.status==6}">
																<select name="order_status" onchange="updateOrderStatus('${map.orderView.orderId}',this)"><option value="5"
																		<c:if test="${map.orderView.status==5}"> selected='selected'</c:if>>退货中</option>
																	<option value="6"
																		<c:if test="${map.orderView.status==6}"> selected='selected'</c:if>>已退货</option></select>
															 </c:if>
														   </td>
														   <td>
															<div class="visible-md visible-lg hidden-xs action-buttons">
																<!-- 查看订单信息操作 -->
																<a class="blue"
																	href="order/getOrderDetail/${map.orderView.orderId}">
																	<i class="icon-zoom-in bigger-130"></i>
																</a>
															</div>
															</td>
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

	<script>
		$(function() {  
			var message = '${message}';
			if(message != ''){
				alert( message );
			}
		});  	
	</script>
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
	<script
		src="<%=request.getContextPath()%>/js/order/orderList.js"></script>
</body>
</html>
