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
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/product/editSaleStatus.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/jquery.datetimepicker.css" />

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
									data-toggle="modal" style="color: #438eb9!important;font-size: 26px;"> 商品列表 </a>/
							    <a href="<%=request.getContextPath()%>/productAudit/auditIndex/0/1/prvious" role="button" class="green"
									data-toggle="modal"> 审核商品操作 </a>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<div class="nav-search" id="nav-search">
								<form class="form-search">
									<span class="span_margin">商品状态:</span><select
										name="goodsStatus" class="input_border" id="status_search"
										data-checked="${status}" onchange="statusChange(this)"><option
											value="3">--请选择商品状态--</option>
										<option value="0">--在 售--</option>
										<option value="1">--下 架--</option>
									</select> <span class="span_margin">销售状态:</span><select
										name="goodsStatus" class="input_border" id="saleStatus_search"
										data-checked="${saleStatus}"
										onchange="saleStatusChanage(this)"><option value="-1">--请选择商品销售状态--</option>
										<option value="0">--普通--</option>
										<option value="1">--特 价--</option>
										<option value="2">--限 时--</option>
										<option value="3">--新 品--</option>
										<option value="4">--热 卖--</option>
									</select> <span class="span_margin">商品名称</span></span><span class="input-icon">
										<input type="text" placeholder="${productName}"
										class="nav-search-input" id="name-search-input"
										autocomplete="off" /> <i class="icon-search nav-search-icon"></i>
									</span> <input type="button" id="submit" class="submit_button"
										value="提  交" onclick="productNameChange()">
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
													<th class="center td_max_width"><label> <input
															type="checkbox" class="ace" /> <span class="lbl"></span>
													</label></th>
													<th>商品编号</th>
													<th>商品名称</th>
													<th>价格</th>
													<th>库存</th>
													<th><i class="icon-time bigger-110 hidden-480"></i> 积分
													</th>

													<th>商品父类型</th>
													<th>商品子类型</th>
													<th class="hidden-480">商品状态</th>
													<th class="hidden-480">销售状态</th>
													<th>管理操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${goodsList}" var="item">
													<tr>
														<td class="center td_max_width"><label> <input
																type="checkbox" class="ace checked_input"
																data-id="${item.id}" /> <span class="lbl"></span>
														</label></td>
														<td>${item.id}</td>

														<td><a href="javascript:void(0);">${item.name} </a></td>
														<td>${item.price}</td>
														<td>${item.stock}</td>
														<td>${item.score}</td>
														<td>${item.typeParent}</td>
														<td>${item.typeChild}</td>

														<!--  <td class="hidden-480"><c:if test="${item.status==0}"><span
															class="label label-sm label-success"> 在  售</span></c:if><c:if
																	test="${item.status==1}"><span
															class="label label-sm label-inverse"> 下  架 </span></c:if></td>
															-->
														<td><select class="status" name="status"
															onchange="updateGoodsStatus('${item.id}',this)">
																<option value="${item.status}">
																	<c:if test="${item.status==0}">在 售</c:if>
																	<c:if test="${item.status==1}">下 架</c:if></option>
																<c:if test="${item.status==0}">
																	<option value="1">下 架</option>
																</c:if>
																<c:if test="${item.status==1}">
																	<option value="0">在 售</option>
																</c:if>
														</select></td>
														<td class="hidden-480 sale_status"><c:if
																test="${item.saleStatus==0}">
																<span class="label label-sm label-warning">普通</span>
															</c:if> <c:if test="${item.saleStatus==1}">
																<span class="label label-sm label-warning">特 价</span>
															</c:if> <c:if test="${item.saleStatus==2}">
																<span class="label label-sm label-limit">限 时</span>
															</c:if> <c:if test="${item.saleStatus==3}">
																<span class="label label-sm label-new">新 品</span>
															</c:if> <c:if test="${item.saleStatus==4}">
																<span class="label label-sm label-hot">热 卖</span>
															</c:if></span> <!-- 编辑销售状态信息操作 -->
															<!--  <div class=" action-buttons edit_sale">
																<a class="green" href="javascript:void(0);"
																	onclick="onclickSaleStatus('${item.id}')"> <i
																	class="icon-pencil "></i>
																</a>
															</div>--></td>

														<td>
															<div
																class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
																<!-- 查看商品信息操作 -->
																<a class="blue"
																	href="product/getProductDetail/${item.id}"> <i
																	class="icon-zoom-in bigger-130"></i>
																</a>
																<!-- 编辑商品信息操作 -->
																<c:if test="${item.auditStatus==0}">
																	<a class="green" href="product/toProductEdit/${item.id}">
																		<i class="icon-pencil bigger-130"></i>
																	</a>
																</c:if>
																<!-- 进货 -->
																<a class="blue"
																	href="product/inputproduct/${item.id}"> <i
																	class="icon-inbox bigger-130"></i>
																</a>
																<!-- 统计商品信息 -->
																<a class="blue"
																	href="product/productStatistics/0/1/prvious/0/0/0/${item.id}"> <i
																	class="icon-bar-chart bigger-130"></i>
																</a>
																<c:if test="${item.auditStatus == 0}">
																<!-- 删除商品操作 -->
																 	<a class="red"
																	href="product/deleteRequest?id=${item.id}"> <i
																	class="icon-remove bigger-130"></i>
																	<!-- icon-trash bigger-130 -->
																</a>
																</c:if>
																 
															</div>
														</td>
													</tr>
												</c:forEach>
												<tr>
													<td class="td_max_width center"><select
														name="saleStatus" id="saleStatusOnbath"
														onchange="updateSaleStatusBath(this)">
															<option value="-1">销售状态</option>
															<option value="0">普通</option>
															<option value="1">特 价</option>
															<option value="2">限 时</option>
															<option value="3">新 品</option>
															<!-- <option value="4">热 卖</option> -->

													</select></td>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
																<a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','first')">&nbsp;
																	首 页 &nbsp;</a> <a href="javascript:void();"
																	onclick="dividePage('${allPages}','${currentPage}','prvious')">&nbsp;&lt;&nbsp;
																	prev &nbsp;</a>
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
				<span>修改商品销售状态</span> <a class="guanbi"></a>
			</div>
		</div>

		<div class="min">

			<div class="tc_login">

				<div class="right">
					<div><span class="span_all">销售状态:</span>
					<select name="saleStatus" id="saleStatus"
								onchange="updateSaleStatusByChange(this)">
									<option value="-1">--请选择销售状态---</option>
									<!--  <option value="0">普通</option>-->
									<option value="1">特 价</option>
									<option value="2">限 时</option>
									<!--  <option value="3">新 品</option>-->
									<!--  <option value="4">热 卖</option>-->

							</select></div>
					<div class="hidden show_time" >
						<span class="span_all">开始时间:</span> <input type="text" value=""
							id="datetimepicker_mask_open" />
					</div>
					<div class="hidden show_time">
						<span class="span_all">截至时间:</span> <input type="text" value=""
							id="datetimepicker_mask_end" />
					</div>
					</br>
					<table class="table table-striped table-bordered table-hover">
						<tr class="productList_change">
							<th>商品编号</th>
							<th>商品名称</th>
							<th>商品价格</th>
							<th>特价</th>
						</tr>
					</table>

					<div align="center">
						<input type="submit" id="submit_changeSaleStauts" class="button"
							value="提  交" onclick="submitChangeInPop(this)" data-id="" style="margin-top: 0px;">
					</div>
				</div>

			</div>

		</div>
	</div>

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
	<script src="<%=request.getContextPath()%>/js/product/productIndex.js"></script>
	<script src="<%=request.getContextPath()%>/js/jquery.datetimepicker.js"></script>
	<script>
		$('#datetimepicker_mask_open').datetimepicker({
			mask : '9999/19/39 29:59'
		});
		$('#datetimepicker_mask_end').datetimepicker({
			mask : '9999/19/39 29:59'
		});
	</script>
	<script>
		jQuery(function($) {
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});
		});
	</script>
</body>
</html>
