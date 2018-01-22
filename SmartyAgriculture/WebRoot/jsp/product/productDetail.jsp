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

<body style="    background-color: #fff;">
	<div class="page-content">
		<div class="page-header">
			<h1>
				商品详情 <small> <i class="icon-double-angle-right"></i>
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
										<span class="editable" id="username">${goodsList.name}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">描述</div>

									<div class="profile-info-value">
										<span class="editable" id="age">${goodsList.title}</span>
									</div>
								</div>
								
								 <div class="profile-info-row">
									<div class="profile-info-name">单位商品重量</div>

									<div class="profile-info-value">
										<span class="editable" id="age">${goodsList.weight}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">商品扫码价格</div>

									<div class="profile-info-value">
										<span class="editable" id="login">${goodsList.vipPrice}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">商品价格</div>

									<div class="profile-info-value">
										<span class="editable" id="login">${goodsList.price}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">积分</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${goodsList.score}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">商品种类</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${goodsList.typeParent}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">子商品种类</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${goodsList.typeChild}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">购买数量</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${goodsList.buyNum}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">所属地块</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${blockName}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">商品状态</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${status}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">销售状态</div>

									<div class="profile-info-value">
										<span class="editable" id="about">${saleStatus}</span>
									</div>
								</div>
								<%-- 
								<c:if test="${flag }">
						<div class="profile-info-row">
<div class="profile-info-name">已设运费模板</div>
							<div class="profile-info-value" >
								<c:forEach items="${freightList}" var="vo" varStatus="mainIndex">
									<input class="input-sm" type="text" id="form-field-4"
										placeholder="${vo.province}" name="update_province"
										value="${vo.province}" readonly/>
									<input class="input-sm" type="text" id="form-field-4"
										placeholder="${vo.city}" name="update_city" value="${vo.city}" readonly/>
									<input class="input-sm" type="text" id="form-field-4"
										placeholder="${vo.money}" name="update_money" value="${vo.money}" readonly/>
								<div class="help-block" id="input-size-slider"></div>
								</c:forEach>
							</div>
						</div>

					</c:if>--%>
								<div class="profile-info-row">
									<div class="profile-info-name">商品图片</div>

									<div class="profile-info-value">
										<div id="album">
											<div id="pic">
												<a href="javascript:void(0);" rel="lightbox" id="ShowLightBox"><img
													src="${pictureList[0]}" alt="商品图片"
													id="placeholder" /></a>
											</div>
											<p id="desc"></p>
											<div id="thumbs">
												<ul>
													<c:forEach items="${pictureList}" var="item">
													<li><a onclick="return showPic(this);" href="${item}"
														title="商品图片"><img src="${item}"
															alt="商品图片" /></a></li>
													</c:forEach>
												</ul>
											</div>
										</div>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">信息详情</div>

									<div class="profile-info-value">
										<span class="editable" id="goodsDetail">${goodsDetail}</span>
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
	<script src="<%=request.getContextPath()%>/js/common.js"></script>
</body>
</html>
