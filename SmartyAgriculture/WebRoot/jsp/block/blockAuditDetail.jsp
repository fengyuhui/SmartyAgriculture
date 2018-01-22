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
			<a href="" role="button" class="green"
				data-toggle="modal"onclick="JavaScript:history.back(-1);"> 返回</a>/
				审核土地 <small> <i class="icon-double-angle-right"></i>
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
									<div class="profile-info-name">土地名称</div>

									<div class="profile-info-value">
										<span class="editable" id="name">${block.getBlockName()}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">土地种植类型</div>

									<div class="profile-info-value">
										<span class="editable" id="name">${block.getGoodName()}</span>
									</div>
								</div>
                                <div class="profile-info-row">
									<div class="profile-info-name">管理员id</div>

									<div class="profile-info-value">
										<span class="editable" id="adminName">${block.getManagerId()}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">管理员名称</div>

									<div class="profile-info-value">
										<span class="editable" id="adminName">${block.getManagerName()}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">描述</div>

									<div class="profile-info-value">
										<span class="editable" id="description">${block.getDescription()}</span>
									</div>
								</div>
								<div class="profile-info-row">
									<div class="profile-info-name">余量</div>

									<div class="profile-info-value">
										<span class="editable" id="description">${block.getStock()}</span>
									</div>
								</div>

								<div class="profile-info-row">
									<div class="profile-info-name">创建时间</div>

									<div class="profile-info-value">
										<span class="editable" id="dateString">${block.getCreateDate()}</span>
									</div>
								</div>
								
								<div class="profile-info-row">
									<div class="profile-info-name">审核</div>

									<div class="profile-info-value">
										<input type="radio" name="auditStatus" value="1"/>审核通过
										<input type="radio" name="auditStatus" value="0"/>审核不通过
										<div> 
										<input id="submit" type="button" value="确  定" style="margin-left: 35px;
    background-color: #438eb9;
    font-size: 18px;
    margin-top: 10px;color:white;" onclick="updateAuditStatus()" data-id="${block.getBlockId()}"/></div>
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
	</div>
</body>

<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>
<script>
function updateAuditStatus(){
	var id= $("#submit").attr("data-id");
	var auditStatus=$('input:radio:checked').val();
	if(auditStatus==undefined){
		alert("请选择审核状态");
	}
	else{
		var url="blockAudit/updateBlockAuditStatus/"+id+"/"+auditStatus;
		$.ajax({ 
	        type:"POST", 
	        url:url,
	    	dataType : "json",
			contentType : "application/json",
			//data : JSON.stringify(schemeMap),
			success: function(data){  
			   alert("审核成功！");
			   setTimeout(function(){window.location.href="blockAudit/auditIndex/0/1/prvious";},3000);
	        },  
	        error: function()   
	        {  
	            alert("审核失败！");
	        } 
			});
	}
	
}
</script>

</html>
