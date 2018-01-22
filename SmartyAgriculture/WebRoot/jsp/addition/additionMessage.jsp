<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>${item.title}</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
	$(window).load(function(){
 
    $("body img").addClass("img-responsive center-block");
 
})
	</script>
	<style type="text/css">
		*{
			margin: 0;
			padding: 0;
		}
		html,body{
			width: 100%;
			height: 100%;
			overflow: hidden; 
		}
	</style>
  </head>
  <!-- -->
  <body>
${item.content}
  </body>
</html>
