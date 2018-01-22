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
<!-- page specific plugin styles -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/jquery.gritter.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap-editable.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />

</head>

<body style="    background-color: #fff;">
	<div class="page-content">
		<div class="page-header">
			<h1>
				用户详情 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<div>
					<div id="user-profile-1" class="user-profile row">
						<div class="col-xs-12 col-sm-3 center">
							<div>
								<span class="profile-picture"> <img id="avatar"
									class="editable img-responsive" alt="Alex's Avatar"
									src="${user.userInfo.portrait}" style="width: 150px;" />
								</span>

								<div class="space-4"></div>

								<div
									class="width-80 label label-info label-xlg arrowed-in arrowed-in-right">
									<div class="inline position-relative">
										<i class="icon-circle light-green middle"></i> &nbsp; <span
											class="white">${user.userInfo.name}</span>
									</div>
								</div>
							</div>

							<div class="space-6"></div>

							<div class="hr hr12 dotted"></div>

							<div class="clearfix">
								<div class="grid2">
									<span class="bigger-175 blue">${user.userInfo.money}</span> <br /> 余额
								</div>

								<div class="grid2">
									<span class="bigger-175 blue">${user.userInfo.score}</span> <br /> 积分
								</div>
							</div>

							<div class="hr hr16 dotted"></div>
						</div>
						<!-- 编辑用户信息链接 -->
						<a class="green" href="user/edit/${user.userInfo.id}" style="float: right;
    margin-right: 30px;">  <i
							class="icon-pencil bigger-130"></i>
						</a>
						<div class="col-xs-12 col-sm-9">

							<div class="space-12"></div>

							<div class="profile-user-info profile-user-info-striped">
								<div class="profile-info-row">
									<div class="profile-info-name">用户名</div>

									<div class="profile-info-value">
										<span class="editable" id="username">${user.userInfo.name}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">手机号</div>

									<div class="profile-info-value">
										<span class="editable" id="age">${user.userInfo.phone}</span>
									</div>
								</div>


								<div class="profile-info-row">
									<div class="profile-info-name">用户类型</div>

									<div class="profile-info-value">
										<span class="editable" id="login"><c:if
												test="${user.userInfo.userType==1||user.userInfo.userType==0}">
							 普通用户
							</c:if> <c:if test="${user.userInfo.userType==1}">
							会员用户
							</c:if></span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">用户状态</div>

									<div class="profile-info-value">
										<span class="editable" id="login"><c:if
												test="${user.userInfo.status==0}">
							正常状态
							</c:if> <c:if test="${user.userInfo.status==1}">
							禁用状态
							</c:if></span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">注册时间</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${user.createTimeString}</span>
									</div>
								</div>
							</div>

							<div class="space-20"></div>
						</div>
					</div>
				</div>
				<br>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<div class="hr hr-18 dotted hr-double"></div>
				<h4 class="pink">
					<i class="icon-hand-right icon-animated-hand-pointer blue"></i> <a
						href="javascript:void(0)" role="button" class="green"
						data-toggle="modal"> 订单记录 </a>
				</h4>
				<div class="hr hr-18 dotted hr-double"></div>
				<div class="nav-search" id="nav-search">
					<!--  <form class="form-search">
									<span class="input-icon"> <input type="text"
										placeholder="订单号 ..." class="nav-search-input"
										id="nav-search-input" autocomplete="off" /> <i
										class="icon-search nav-search-icon"></i>
									</span> <span class="input-icon"> <input type="text"
										placeholder="水果名 ..." class="nav-search-input"
										id="nav-search-input" autocomplete="off" /> <i
										class="icon-search nav-search-icon"></i>
									</span>
								</form>-->
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
										<th>购买时间</th>

										<th>商品名称</th>
										<th>收货人</th>
										<th>联系方式</th>
										<th>省市县</th>
										<th>详细地址</th>
										<th class="hidden-480">订单状态</th>


									</tr>
								</thead>

								<tbody>
									<c:forEach items="${orderView}" var="item">
										<tr>
											<td><a href="javascript:void(0)">${item.orderView.id} </a></td>
											<td>${item.orderView.money}</td>
											<td>${item.orderView.num}</td>
											<td>${item.dateString}</td>
											<td>${item.orderView.name}</td>
											<td>${item.addressname}</td>
											<td>${item.phone}</td>
											<td>${item.province}</td>
											<td>${item.detailAddress}</td>
											<td class="hidden-480"><c:if
													test="${item.orderView.status==1}">
													<span class="label label-sm label-warning">未付款</span>
												</c:if> <c:if
													test="${item.orderView.status==2||item.orderView.status==3}">
													<select name="order_status"
														onchange="updateOrderStatus('${item.orderView.id}',this)"><option
															value="2"
															<c:if test="${item.orderView.status==2}"> selected='selected'</c:if>>未发货</option>
														<option value="3"
															<c:if test="${item.orderView.status==3}"> selected='selected'</c:if>>已发货</option></select>
												</c:if> <c:if test="${item.orderView.status==4}">
													<span class="label label-sm label-warning">已收货</span>
												</c:if> <c:if
													test="${item.orderView.status==5||item.orderView.status==6}">
													<select name="order_status"
														onchange="updateOrderStatus('${item.orderView.id}',this)"><option
															value="5"
															<c:if test="${item.orderView.status==5}"> selected='selected'</c:if>>退货中</option>
														<option value="6"
															<c:if test="${item.orderView.status==6}"> selected='selected'</c:if>>已退货</option></select>
												</c:if></td>


										</tr>
									</c:forEach>
									<tr>
										<td colspan="10" style="text-align:left"><c:if
												test="${allPages>1}">
												<div align="right" class="viciao">
													<a href="user/detail/${user.userInfo.id}/${allPages}/${currentPage}/first">&nbsp;
														首 页 &nbsp;</a> <a
														href="user/detail/${user.userInfo.id}/${allPages}/${currentPage}/prvious">&nbsp;&lt;&nbsp;
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
													<a href="user/detail/${user.userInfo.id}/${allPages}/${currentPage}/<%=i%>"><%=i%></a>
													<%
														}
															}
													%>
													<a href="user/detail/${user.userInfo.id}/${allPages}/${currentPage}/next">&nbsp;Next&nbsp;&gt;&nbsp;</a>
													<a href="user/detail/${user.userInfo.id}/${allPages}/${currentPage}/last">&nbsp;尾
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
	</div>
	<script src="<%=request.getContextPath()%>/js/base.js"></script>
	<script src="<%=request.getContextPath()%>/js/common.js"></script>
</body>
</html>
