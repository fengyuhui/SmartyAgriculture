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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/font-awesome.min.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/assets/css/dropzone.css" />

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>



</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				添加一块新的土地 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="block/addBlockPost"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">土地名称(必填)</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="" name="blockName" /> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">土地描述</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="description" value="" name="description" /> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">土地余量</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="stock" value="" name="stock" /> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">土地管理员</label>

						<div class="col-sm-9">
							<select id="managerId" name="managerId" data-default="${blockList.admin}">
								<option value="0">----请选择土地管理员----</option>
								<c:forEach items="${blockManagerList}" var="manager">
									<option value="${manager.getId()}">${manager.getName()}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 土地种植种类 </label>

						<div class="col-sm-9">
							<select id="typeParent" name="typeIdParent" >
								<option value="0">----请选择土地种植类型----</option>
								<c:forEach items="${goodsTypeList}" var="item0">
									<option value="${item0.id}">${item0.name}</option>
								</c:forEach>
							</select> 
							
							<select class="typeSub" id="typeSub-1" nama="typeIdChild">
								<option value="-1">----请选择子土地种植类型----</option>
							</select>
							
							<p id = "typeSub00" style = "display:none;"></p>
							<c:forEach items="${goodsTypeList}" var="item0"  varStatus="status0">
								<select class="typeSub" id="typeSub${status0.index}" >
									<c:forEach items="${item0.subGoodsTypeList}" var="item1">
										<option value="${item1.id}">${item1.name}</option>
									</c:forEach>
								</select>
							</c:forEach>
							
							
							<p id = "typeThird000" style = "display:none;"></p>
							     <b id ="typeThird" >
							     <select class="typeThird" id="typeThird-1" nama="goodId">
								     <option value="1">----请选择种植类型----</option>
							     </select>
							     
							</b>
						</div>
					</div>
					<div class="space-4"></div>


					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id="sm">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="icon-undo bigger-110"></i> 取消
							</button>
						</div>
					</div>

					<div class="hr hr-24"></div>

				</form>
			</div>
		</div>
	</div>
	
	<script>
		$("#typeParent option[value='${blockList.typeIdParent}']").attr("selected",true);
		$(".typeSub option[value='${blockList.typeIdChild}']").attr("selected",true);
	</script>
	<script type="text/javascript">
	//地块种植类型 三级联动设置
	
	$(document).ready(function() {
		var show = false;
		var parent = -1;
		var sub = -1;
		$(".typeSub").hide();
		$("#vip").change(function(){
			if($("#vip option:selected").text() =="是") {
				$("#vipPrice").css("display","inline");
			}
			else {
				$("#vipPrice").css("display","none");
			}
			
		});
		$("#typeParent").change(function() {
			$("#typeParent option").each(function(i, o) {
				$("#typeSub"+i).hide();
				if ($(this).attr("selected")) {
					$(".typeSub").hide();
					parent = i - 1;
					$(".typeSub").attr("name", "");
					$(".typeSub").eq(i).show();
					$(".typeSub").eq(i).attr("name", "typeIdChild");
					$("#typeSub00").text($(this).val());
					if (i != 0){
						show = true;
						$(".typeSub").change();
					}
					else{
					      show = false;
					      $("#typeThird").html('<select class="typeThird" id="typeThird-1" nama="typeIdThird"><option value="1">----请选择子商品种类----</option></select>');
					      
					     }
		

					
					if ($(this).text()=="购物卡") {
						$("#yunfei").css("display","none");
						$("#songda").css("display","none");	

					}
					else {
						$("#yunfei").css("display","inline");
						$("#songda").css("display","inline");	
					}				
					
				}	
			});
			
		});
		
		
		$(".typeSub").change(function() {
			$("#typeThird").html('<c:forEach items="${goodsTypeList}" var="item0" varStatus="status0"><c:forEach items="${item0.subGoodsTypeList}" var="item1" varStatus="status1"><select class="typeThird" id="typeThird${status0.index}${status1.index}"><c:forEach items="${item1.goodsList}" var="item2"><option value="${item2.getId()}">${item2.getName()}</option></c:forEach></select></c:forEach></c:forEach>');
			
			for(var index = 0; index<parent+1; index++){
				$("#typeSub"+index+" option").each(function(i, o) {
				sub = i;
				
				if ($(this).attr("selected")) {
					$(".typeThird").hide();
					$("#typeThird000").text($(this).val());
                    if(show){
                    	$("#typeThird"+parent+sub).show();
    					$(".typeThird").attr("name", "");
    					$("#typeThird"+parent+sub).attr("name", "goodId");
                    }
				}		  
				});
			}
			
		});
		
		$("#typeParent").change();
	});

</script>
<script type="text/javascript">
$("#sm").click(function() {
	var name = $("#form-input-readonly").val();
	var managerId = $("#managerId").val();
	//var typeIdParent = $("#typeParent").val();
	//var typeIdSub = $("#typeSub00").text();
	var goodId = $("#typeThird000").text();
	var stock = $("#amount").val();
	var description = $("#description").val();
	var f = true;
	if(!f)
		return false;
	if(name == "") {
		alert("请填写所添加的地块的名称再提交！");
		return false;
	}

	
	if(stock == "" || !reg_stock.test(stock)) {
		alert("请填写地块余量,并以正确的格式！");
		return false;
	}

	if(typeIdParent == "" || typeIdParent == "0") {
		alert("请选择商品所属类别!");
		return false;
	}
	if(typeIdSub == "" || typeIdSub == "-1") {
		alert("请选择商品所属类别!");
		return false;
	}	
	if(goodId == "" || typeIdThird == "-1") {
		alert("请选择商品所属类别!");
		return false;
	}	

});
</script>

</body>
	
</html>
