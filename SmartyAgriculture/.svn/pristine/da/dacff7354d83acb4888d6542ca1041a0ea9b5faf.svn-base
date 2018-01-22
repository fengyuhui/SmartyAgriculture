<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手机支付产品示例</title>
  <link rel="stylesheet" href="css/jquery-ui.min.css">
  <script src="js/jquery-1.11.2.min.js"></script>
  <script src="js/jquery-ui.min.js"></script>
  <script src="js/demo.js"></script>
  <script>
  	$(function() {
	    setApiDemoTabs("#tabs-df");
	    setApiDemoTabs("#tabs-dfBatch");
	  });
  </script>
  <link rel="stylesheet" href="css/demo.css">
</head>

<body style="background-color:#e5eecc;">
<div id="wrapper">
<div id="header">
<h2>手机支付产品示例</h2>

</div>

<div id="tabs-api">
  <ul>
    <li><a href="#tabs-api-1">前言</a></li>
    <li><a href="#tabs-api-2">手机控件支付后台交易样例</a></li>
    <li><a href="#tabs-api-3">常见开发问题</a></li>
  </ul>
  
  <div id="tabs-api-1">
    <jsp:include  page="/jsp/unipay/introduction.jsp"/>
  </div>

  <div id="tabs-api-2">
	<div id="tabs-df">
	  <ul>
	    <li><a href="#tabs-df-1">说明</a></li>
	    <li><a href="/jsp/unipay/consume.jsp">消费（获取tn)</a></li>
	    <li><a href="/jsp/unipay/consumeUndo.jsp">消费撤销</a></li>
	    <li><a href="/jsp/unipay/refund.jsp">退货</a></li>
		<li><a href="/jsp/unipay/query.jsp">交易状态查询</a></li>
		<li><a href="/jsp/unipay/file_transfer.jsp">对账文件下载</a></li>
	  </ul>
	  <div id="tabs-df-1">
	     <jsp:include  page="/jsp/unipay/comsume_intro.jsp"/>
	  </div>
	</div>
  </div>

  <div id="tabs-api-3">
    <jsp:include  page="/jsp/unipay/devlopHelp.jsp"/>
  </div>
	  
  </div> <!-- end of tabs-api-->
</div><!-- end of wrapper-->
 
 
</body>
</html>