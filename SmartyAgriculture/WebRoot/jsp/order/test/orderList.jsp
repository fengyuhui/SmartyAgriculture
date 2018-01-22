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
									data-toggle="modal"> 订单列表 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<div class="nav-search" id="nav-search">
								<form class="form-search" id="exportExcel">
									<!--	<span class="span_margin">订单状态:</span><select
										name="goodsStatus" class="input_border" id="status_search" data-checked="${status}" onchange="statusChange(this)"><option
											value="3" >--请选择订单状态--</option>
										<option value="0">--在 售--</option>
										<option value="1">--下 架--</option>
									</select> <span class="span_margin">销售状态:</span><select
										name="goodsStatus" class="input_border" id="saleStatus_search" data-checked="${saleStatus}" onchange="saleStatusChanage(this)"
										><option
											value="0">--请选择商品销售状态--</option>
										<option value="1">--特 价--</option>
										<option value="2">--限 时--</option>
										<option value="3">--新 品--</option>
										<option value="4">--热 卖--</option>
									</select>
									-->
									<span class="span_margin">购买时间:</span> <input
										class="m-wrap span3" type="text" id="starttime"
										data-date-format="yyyy-mm-dd" readonly size="16" /> <span
										class="add-on"><i class="icon-calendar"></i> </span> 至 <input
										class="m-wrap span3" type="text" id="endtime"
										data-date-format="yyyy-mm-dd" readonly size="16" /> <span
										class="add-on"><i class="icon-calendar"></i> </span> <input
										type="button" class="submit_button" value="提  交"
										onclick="orderTimeChange()">
										<%-- 
										<div class="progress" style="display: none">
											<div class="progress-bar" style="width:20%"></div>
										</div>--%>
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
												<c:forEach items="${orderView}" var="item">
													<tr>
														<td>${item.orderView.id}</td>
														<td>${item.orderView.money}</td>
														<td>${item.dateString}</td>
														<td>${item.addressname}</td>
														<td>${item.phone}</td>
														<td>${item.province}</td>
														<td>${item.detailAddress}</td>
														<td class="hidden-480"><c:if
																test="${item.orderView.status==1}">
																<span class="label label-sm label-warning">未付款</span>
															</c:if> 
															<c:if
																test="${item.orderView.status==2||item.orderView.status==3}">
																<select name="order_status"
																	onchange="updateOrderStatus('${item.orderView.id}',this,${item.orderView.pickUp})"><option
																		value="2"
																		<c:if test="${item.orderView.status==2}"> selected='selected'</c:if>>未发货</option>
																	<option value="3"
																		<c:if test="${item.orderView.status==3}"> selected='selected'</c:if>>已发货</option></select>
															</c:if>

															<c:if test="${item.orderView.status==4}">
																<span class="label label-sm label-warning">已收货</span>
															</c:if> <c:if
																test="${item.orderView.status==5||item.orderView.status==6}">
																<select name="order_status"
																	onchange="updateOrderStatus('${item.orderView.id}',this)"><option
																		value="5"
																		<c:if test="${item.orderView.status==5}"> selected='selected'</c:if>>退货中</option>
																	<option value="6"
																		<c:if test="${item.orderView.status==6}"> selected='selected'</c:if>>已退货</option></select>
															</c:if>
															<c:if test="${item.orderView.status==7}">
																<span class="label label-sm label-warning">交易成功</span>
															</c:if>
															</td>
															<td>
															<div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看订单信息操作 -->
																<a class="blue"
																	href="order/getOrderDetail/${item.orderView.id}"> <i
																	class="icon-zoom-in bigger-130"></i>
																</a></div>
															</td>


													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
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
					<span class="span_all">运单号:</span> <input type="text" id="tracking"
						name="tracking" /> <br> <br>
					<div align="center">
						<input type="submit" id="submit_changeSaleStauts" class="button"
							value="提  交" onclick="submitChangeInPop(this)" data-id=""
							style="margin-top: 0px;">
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
