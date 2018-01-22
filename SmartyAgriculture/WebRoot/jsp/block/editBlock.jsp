


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

<title>编辑地块</title>

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
				编辑地块信息 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="block/editPost"
					method="post" enctype="multipart/form-data">
					<input type="hidden" name="blockId" value='${blockInfo.getBlockId()}'>
					<input type="hidden" name="postType" value=1>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">地块号</label>
						<div class="col-sm-9">
							${blockInfo.getBlockId() }
						</div>
					</div>
                                                                                <div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">地块名称(必填)</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" name="blockName" value="${blockInfo.getBlockName()} " style="width:25%"/>							
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">土地描述</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="description" name="description" value="${blockInfo.getDescription()} " style="width:25%"/>							
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">土地余量</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="stock" name="stock" value="${blockInfo.getStock()} " style="width:25%"/>							
						</div>
					</div>	

					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2">土地管理员 </label>
						<div class="col-sm-9">
							<select id="managerId" name="managerId" data-default="${blockList.adminId}">
								<c:forEach items="${blockManagerList}" var="item0">
								  <c:if test="${item0.getId()==blockInfo.getManagerId()}">
								    <option value="${blockInfo.getManagerId()}" selected="selected">${blockInfo.getManagerName()}</option>
								  </c:if>
								  <c:if test="${item0.getId()!=blockInfo.getManagerId()}">
								  <option value="${item0.getId()}">${item0.getName()}</option>
								  </c:if>
								</c:forEach>
							</select>
						</div>
					</div>
					

					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2">土地种植类型 </label>

						<div class="col-sm-9">
							<select id="typeParent" name="typeIdParent" >
								<c:forEach items="${goodsTypeList}" var="item0">
									<c:if test="${item0.id==good.getTypeIdParent()}">						
                                       <option value="${item0.id}" selected="selected">${item0.name}</option>
							         </c:if>
							         <c:if test="${item0.id!=good.getTypeIdParent()}">
							           <option value="${item0.id}">${item0.name}</option>
							         </c:if>
								</c:forEach>
							</select>
							
							<p id = "typeSub00" style = "display:none;"></p>
							<c:forEach items="${goodsTypeList}" var="item0"  varStatus="status0">
								<select class="typeSub" id="typeSub${status0.index}" >
									<c:forEach items="${item0.subGoodsTypeList}" var="item1">
										<c:if test="${item1.id==good.getTypeIdChild()}">						
                                          <option value="${item1.id}" selected="selected">${item1.name}</option>
							            </c:if>
							            <c:if test="${item1.id!=good.getTypeIdChild()}">
							              <option value="${item1.id}">${item1.name}</option>
							            </c:if>
									</c:forEach>
								</select>
							</c:forEach>
							
							
							<p id = "typeThird000" style = "display:none;"></p>
							     <b id ="typeThird" >
							 							     
							     </b>
						</div>
					</div>
					
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id = "sm">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="button" onclick="window.location.href='block/index/0/1/prvious'">
								<i class="icon-undo bigger-110"></i> 返回
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
					parent = i;
					$(".typeSub").attr("name", "");
					$(".typeSub").eq(i).show();
					$(".typeSub").eq(i).attr("name", "typeIdChild");
					$("#typeSub00").text($(this).val());
					show = true;
					$(".typeSub").change();
				}	
			});
			
		});
		
		
		
		$(".typeSub").change(function() {
			$("#typeThird").html('<c:forEach items="${goodsTypeList}" var="item0" varStatus="status0"><c:forEach items="${item0.subGoodsTypeList}" var="item1" varStatus="status1"><select class="typeThird" id="typeThird${status0.index}${status1.index}"><c:forEach items="${item1.goodsList}" var="item2"><c:if test="${item2.getId()==good.getId()}"><option value="${item2.getId()}" selected="selected">${item2.getName()}</option></c:if><c:if test="${item2.getId()!=good.getId()}"><option value="${item2.getId()}">${item2.getName()}</option></c:if></c:forEach></select></c:forEach></c:forEach>');
			
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
	//var typeIdParent = $("#typeParent").val();
	//var typeIdSub = $("#typeSub00").text();
	var goodId = $("#typeThird000").text();
	var stock = $("#stock").val();
	var description = $("#description").val();
	var managerId = $("#managerId").val();
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
		alert("请选择土地所属类别!");
		return false;
	}
	if(typeIdSub == "" || typeIdSub == "-1") {
		alert("请选择土地所属类别!");
		return false;
	}	
	if(typeIdThird == "" || typeIdThird == "-1") {
		alert("请选择土地所属类别!");
		return false;
	}	

});
</script>

</body>

</html>
