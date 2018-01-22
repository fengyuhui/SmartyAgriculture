<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  <script type="text/javascript">
  var str = "{a:123,n:456}";
	var json = eval('(' + str+')');
  
	function p() {}
	p.prototype.name = "hello world";
	p.prototype.age = "18";
	p.prototype.getName = function() {return this.name;}
	p.prototype.getAge = function() {return this.age;}
	var pp = new p();
	pp.name = "额";
	alert(p.prototype === pp.__proto__);
	alert(pp.__proto__.name);
  </script>
   </body>
</html>
