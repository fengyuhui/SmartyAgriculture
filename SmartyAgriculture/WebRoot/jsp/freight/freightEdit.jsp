<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	//本页面url，翻页用
	String url = "/goodsCard/reviewedDetails/";
	request.setAttribute("url", url);
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
 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
 	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />

  </head>
  
 <body>

								<div class="hr hr-18 dotted hr-double"></div>
								<h4 class="pink">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="javascript:void(0)"  class="green" >运费编辑 </a>
								</h4>							
								<div class="hr hr-18 dotted hr-double"></div>
<form class="form-horizontal" action="product/freightEditPost/${freight.id }"
					method="post" >
					<input value="${freight.id}" style="display:none" name="id"></input>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">运费类型</label>

						<div class="col-sm-9">
							<c:if test="${freight.type== 0}" >
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="市内"  readonly/> <span
								class="help-inline col-xs-12 col-sm-7"> 
							</span>	
							<input style="display:none" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="0" name="type" readonly/>						
							</c:if>
							<c:if test="${freight.type== 1}" >
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="省内"  readonly/> <span
								class="help-inline col-xs-12 col-sm-7"> 
							</span>		
									<input style="display:none" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="1" name="type" readonly/>						
							</c:if>
							<c:if test="${freight.type== 2}" >
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="省内"  readonly/> <span
								class="help-inline col-xs-12 col-sm-7"> 
							</span>							
									<input style="display:none" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="2" name="type" readonly/>	
							</c:if>							
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">首重(kg)</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								name="firstweight" value="${freight.firstweight}"/> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">首重价格(元/kg)</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" 
								class="col-xs-10 col-sm-5" name="firstweightprice" value="${freight.firstweightprice}"/>
						</div>
					</div>
					
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">续重(kg)</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" 
								class="col-xs-10 col-sm-5" name="additionweight" value="${freight.additionweight}"/>
						</div>
					</div>
					
					<div class="space-4" ></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">续重价格(元/kg)</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" 
								class="col-xs-10 col-sm-5" name="additionweightprice" value="${freight.additionweightprice}"/>
						</div>
					</div>
					
     

						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="icon-ok bigger-110"></i> 提交
							</button>

					</div>
					<div class="hr hr-24"></div>
				</form>


		<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

		<!-- <![endif]-->
		<!-- "http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"-->
		
		<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/jquery.dataTables.bootstrap.js"></script>

		<!-- ace scripts -->
		
		<script src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/goodsCard/reviewedDetail.js"></script>
<%-- 
<script>
function exportExcel(){
	var id = $("input[type=hidden]").val();
	alert(id);
    var src = "goodsCard/exportExcel/"+id;
	  $.ajax({ 
	  type:"POST", 
	  url:src,
      contentType : "application/json",
	  success: function(data,response){ 
			alert("文件已成功下载到桌面");
			alert(data);
	  },  
	 error: function(data)   
	 {  
		 alert("文件下载失败，请稍后重试");
	 }
	});
}
</script>--%>
  </body>
</html>
