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

<body style="background-color: #fff;">
	<div class="page-content">
		<div class="page-header">
			<h1>
				评论详情 <small> <i class="icon-double-angle-right"></i>
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
									<div class="profile-info-name">商品名称</div>

									<div class="profile-info-value">
										<span class="editable" id="username">${commentView.goodsName}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">用户名称</div>

									<div class="profile-info-value">
										<span class="editable" id="age">${commentView.userName}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">是否匿名</div>

									<div class="profile-info-value">
										<c:if test="${commentView.isAnonymous==0}">
											<span class="label label-sm label-success"> 非匿名</span>
										</c:if>
										<c:if test="${commentView.isAnonymous==1}">
											<span class="label label-sm label-inverse"> 匿名</span>
										</c:if>
										</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">级别</div>

									<div class="profile-info-value">
										<span class="editable" id="about"><c:choose>
												<c:when test="${commentView.level==1}">全部</c:when>
												<c:when test="${commentView.level==2}">有图</c:when>
												<c:when test="${commentView.level==3}">好评</c:when>
												<c:when test="${commentView.level==4}">中评</c:when>
												<c:when test="${commentView.level==5}">差评</c:when>

											</c:choose></span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">浏览次数</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${commentView.viewNum}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">评论内容</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${commentView.content}</span>

									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">评论图片</div>

									<div class="profile-info-value">
										<div id="album">
											<div id="pic">
												<a href="javascript:void(0);" rel="lightbox"
													id="ShowLightBox"><img src="${pictureList[0]}"
													alt="评论图片" id="placeholder" /></a>
											</div>
											<p id="desc"></p>
											<div id="thumbs">
												<ul>
													<c:forEach items="${pictureList}" var="item">
														<li><a onclick="return showPic(this);" href="${item}"
															title="评论图片"><img src="${item}" alt="评论图片" /></a></li>
													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">审核</div>

									<div class="profile-info-value">
										<label> <input name="form-field-radio" type="radio"
											class="ace" onchange="commentStatusAudit2(${commentView.id},2)" /> <span
											class="lbl"> 通过</span>
										</label> <label> <input name="form-field-radio" type="radio"
											class="ace" onchange="commentStatusAudit2(${commentView.id},0)" /> <span
											class="lbl"> 拒绝</span>
										</label>
									</div>
								</div>


							</div>
							<div class="space-20"></div>
						</div>
					</div>
					<div style="width: 200px;margin: 0px auto;">
						<button class="btn btn-primary" onclick="goBack()">返 回</button>
					</div>
				</div>
				<br>
			</div>
		</div>
	</div>
	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/js/product/comment.js"></script>
</body>
<script type="text/javascript">
	function goBack() {
		//	javascript:history.go(-1);
		self.location = document.referrer;
	}
</script>

</html>
