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
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/order/editOrderStatus.css" />
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

								<a role="button" class="green"
									href="<%=request.getContextPath()%>/product/productStatistics/0/1/prvious/0/0/0/${goodsId}"
									data-toggle="modal"> ${goodsName} 销售额统计 </a>/ <i
									class="icon-hand-right icon-animated-hand-pointer blue"></i> <a
									role="button" class="green" data-toggle="modal"
									style="color: #438eb9!important;font-size: 26px;"> 进货统计 </a>
								<div style="float: right;">当前商品库存：${stock}${unit}</div>
							</h4>
							<div class="hr hr-18 dotted hr-double"></div>
							<div class="nav-search" id="nav-search">
								<form class="form-search" id="exportExcel">
									<span class="span_margin"> 统计时间:</span> <input
										class="m-wrap span3" type="text" id="starttime"
										data-date-format="yyyy-mm-dd" readonly size="16" /> <span
										class="add-on"><i class="icon-calendar"></i> </span> 至 <input
										class="m-wrap span3" type="text" id="endtime"
										data-date-format="yyyy-mm-dd" readonly size="16" /> <span
										class="add-on"><i class="icon-calendar"></i> </span> <input
										type="button" class="submit_button" value="提  交"
										onclick="TimeChange(${goodsId})" />
									<div style="float: right;">
										<a href="javascript:void(0);"
											onclick="exportExcel('${goodsId}')" style="font-size:16px;">导出excel</a>
									</div>
							</div>

							<div id="main_zhuzhuangtu" style="height:400px;"></div>
							<!-- #nav-search -->
							<div class="row">
								<div class="col-xs-12">

									<div class="table-responsive">
										<table id="sample-table-2"
											class="table table-striped table-bordered table-hover">
											<thead class="table-header">
												<tr>
													<th>时间</th>
													<th>进货数量</th>
													<th>进货次数</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${totalSalesStatisticsList}" var="item">
													<tr>
														<td><a href="javascript:void(0);">${item.showTime} </a></td>

														<td>${item.num}</td>
														<td>${item.input_num}</td>
													</tr>
												</c:forEach>
												<tr>
													<td colspan="10" style="text-align:left"><c:if
															test="${allPages>1}">
															<div align="right" class="viciao">
																<a href="javascript:void();"
																	onclick="dividePage('${goodsId}','${allPages}','${currentPage}','first')">&nbsp;
																	首 页 &nbsp;</a> 
																	<a href="javascript:void();"
																	onclick="dividePage('${goodsId}','${allPages}','${currentPage}','prvious')">&nbsp;&lt;&nbsp;Prev &nbsp;</a>
																
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
																	onclick="dividePage('${goodsId}','${allPages}','${currentPage}','<%=i%>')"><%=i%></a>
																<%
																	}
																		}
																%>
																<a href="javascript:void();"
																	onclick="dividePage('${goodsId}','${allPages}','${currentPage}','next')">&nbsp;Next&nbsp;&gt;&nbsp;</a>
																<a href="javascript:void();"
																	onclick="dividePage('${goodsId}','${allPages}','${currentPage}','last')">&nbsp;尾
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
	
	<!--[if !IE]> -->

		<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

	<!-- <![endif]-->
	<!-- /.main-container -->
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->

	<script
		src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>

	<!-- <![endif]-->
	


	<script type="text/javascript">
		if ("ontouchend" in document)
			document
					.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="<%=request.getContextPath()%>/js/echarts/echarts.js"></script>
	<script src="<%=request.getContextPath()%>/js/echarts/config.js"></script>
	<script type="text/javascript">
	  var starttime2=${starttime};
	  var endtime2=${endtime};
	  if(starttime2!=0){
			 starttime2=starttime2.replace("年","-");
				starttime2=starttime2.replace("月","-");
				starttime2=starttime2.replace("日","");
				endtime2=endtime2.replace("年","-");
				endtime2=endtime2.replace("月","-");
				endtime2=endtime2.replace("日","");
		 }
	  var goodsId="${goodsId}";
	    //柱状图
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main_zhuzhuangtu'));
        /*  var starttime = $("#starttime").val();
    	var endtime = $("#endtime").val();
       var status=$("#orderStatus").val();
        if(status==""){
       	status=0;
        }
        if (starttime == null || endtime == "") {
    		starttime=0;
    		endtime=0;
    	}*/
      myChart.setOption(option);
      var src = "product/echartsProductInputStatistics/"+ starttime2+"/"+endtime2+"/"+goodsId;
   $.ajax({ 
	        type:"POST", 
            url:src,
	    	dataType : "json",
			contentType : "application/json",
			success: function(data){  
			   myChart.setOption({
				   title : {
						text : ' 商品进货统计',// 图标标题
					},
		              xAxis: {
		            	  name : '时间',
		                  data: data.time
		              },
		              yAxis: {//纵坐标的数据
		              	name : '进货数量', //纵坐标的名字
		              },
		              series: [{
		                  // 根据名字对应到相应的系列
		                  name: '销售额',
		                  data: data.input
		              }]
		          });
           },  
           error: function()   
           {  
               alert("数据获取失败");
           }
   });
   
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
	<script
		src="<%=request.getContextPath()%>/css/assets/js/bootstrap-datetimepicker.js"></script>
	<script
		src="<%=request.getContextPath()%>/css/assets/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<!-- ace scripts -->

	<script
		src="<%=request.getContextPath()%>/css/assets/js/ace-elements.min.js"></script>
	<script src="<%=request.getContextPath()%>/css/assets/js/ace.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/js/statistics/productInputStatistics.js"></script>
	<script src="<%=request.getContextPath()%>/js/base.js"></script>
	<script type="text/javascript">
    $(function(){
	 $("#starttime").datetimepicker({
	  format:"yyyy-mm-dd",
	  showMeridian:true,
	  autoclose:true,
	  language:"zh-CN",
	  pickDate:true,
	  minView:2,
	  pickTime:true,
	  todayBtn:true
	 }).on('changeDate',function(ev){	 
	  var starttime=$("#starttime").val();
	  var endtime=$("#endtime").val();
	  if(endtime!=""){
		  if(!checkEndTime(starttime,endtime)){
					$("#endtime").val('');
					alert("开始时间大于结束时间");
					return ;
			}
	  }
	  $("#endtime").datetimepicker('setStartDate',starttime);
	  });
	  
	 $("#endtime").datetimepicker({
		  format:"yyyy-mm-dd",
		  showMeridian:true,
		  autoclose:true,
		  language:"zh-CN",
		  pickDate:true,
		  minView:2,
		  pickTime:true,
		  todayBtn:true
	 }).on('changeDate',function(ev){
	     var starttime=$("#starttime").val();
		 var endtime=$("#endtime").val();
		 if(starttime!=""&&endtime!=""){
			if(!checkEndTime(starttime,endtime)){
				$("#endtime").val('');
				alert("开始时间大于结束时间");
				return ;
			}
		 }
		 $("#starttime").datetimepicker('setEndDate',endtime);
		 $("#starttime").datetimepicker('hide');
	 });
	 if(starttime2!=0){
		 starttime2=starttime2.replace("年","-");
			starttime2=starttime2.replace("月","-");
			starttime2=starttime2.replace("日","");
			endtime2=endtime2.replace("年","-");
			endtime2=endtime2.replace("月","-");
			endtime2=endtime2.replace("日","");
		 $("#starttime").val(starttime2);
		// $("#starttime").datetimepicker('update',new Date());
		 $("#endtime").val(endtime2);
		 //$("#endtime").datetimepicker('update',new Date());
	 }
	 
	});	
</script>
</body>
</html>
