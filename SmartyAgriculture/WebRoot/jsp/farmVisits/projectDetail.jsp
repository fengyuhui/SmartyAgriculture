<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/album/album.css" />

<script src="<%=request.getContextPath()%>/js/album/album.js"
	type="text/javascript"></script>

</head>

<body style="    background-color: #fff;">
	<div class="page-content">
		<div class="page-header">
			<h1>
				项目详情 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<div>
					<div id="user-profile-1" class="user-profile row">

						<div class="col-xs-12 col-sm-9">

							<div class="space-12"></div>

							<div class="profile-user-info profile-user-info-striped">
								<div class="profile-info-row">
									<div class="profile-info-name">项目编号</div>

									<div class="profile-info-value">
										<span class="editable" id="username">${id}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">项目标题</div>

									<div class="profile-info-value">
										<span class="editable" id="">${title}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">项目描述</div>

									<div class="profile-info-value">
										<span class="editable" id="dis">${detail}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">创建时间</div>

									<div class="profile-info-value">
										<span class="editable" id="time"><fmt:formatDate value="${createTime}" type="both"/></span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name">参观类型</div>

									<div class="profile-info-value">
										<div class="row">
									<div class="col-xs-12">
										
										<div class="table-responsive">
											<table id="sample-table-2" class="table table-striped table-bordered table-hover">
												<thead class="table-header">
													<tr>
														<th class="center" style="width:20%">类型编号</th>
														<th>类型标题</th>
														<th >价格</th>
														
													
													</tr>
												</thead>

												<tbody>
												<c:forEach items="${typeList}" var="type">
													<tr>
														<td class="center">
															${type.id}
														</td>

														<td>
															${type.title}
														</td>											
														<td>
															${type.price}
														</td>											
														
														
													</tr>
												</c:forEach>	
												
										
			
												</tbody>
											</table>
										</div>
									</div>
								</div>
								</div>
							</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name">农场图片</div>

									<div class="profile-info-value">
										<div id="album">
											<div id="pic">
												<a href="javascript:void(0);" rel="lightbox" id="ShowLightBox"><img
													src="${pictureList[0]}" alt="农场图片"
													id="placeholder" /></a>
											</div>
											<p id="desc"></p>
											<div id="thumbs">
												<ul>
													<c:forEach items="${pictureList}" var="item">
													<li><a onclick="return showPic(this);" href="${item}"
														title="农场图片"><img src="${item}"
															alt="农场图片" /></a></li>
													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="space-20"></div>
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
				</div>
				<br>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/js/base.js"></script>
</body>
</html>
