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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/product/addProduct.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/product/addProduct.js"></script>
<!-- ueditor -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js">	
</script>
<script type="text/javascript" charset="utf-8"
	src="<%=request.getContextPath()%>/js/product/area.js">	
</script>
</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				添加商品信息 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="product/addProductPost"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">商品名称</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="" name="name" placeholder="必填(请不要在名称中包含/等特殊字符)"/> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">商品简述</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="" name="title" /> <span
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
							for="form-field-1"> 商品价格</label>

						<div class="col-sm-9">
							<input type="text" id="price" placeholder="必填"
								class="col-xs-10 col-sm-5" name="price" />
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">是否有扫码价格</label>

						<div class="col-sm-9">
							<select name="hasVipPrice" id="vip">
							<option value="true">是</option>
							<option value="false" selected="selected">否</option>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group" style="display:none" id="vipPrice">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1"> 商品扫码价格</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" placeholder="必填"
								class="col-xs-10 col-sm-5" name="vipPrice" />
						</div>
					</div>

					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1"> 商品单位</label>

						<div class="col-sm-9">
							<input type="text" id="form-field-1" placeholder="每份"
								class="col-xs-10 col-sm-5" name="unit" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">单位商品重量(kg)</label>

						<div class="col-sm-9">
							<input type="text" id="weight" placeholder="请填入单位商品的重量"
								class="col-xs-10 col-sm-5" name="weight" />
						</div>
					</div>					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 商品种类 </label>

						<div class="col-sm-9">
							<select id="typeParent" name="typeIdParent" >
								<option value="0">----请选择商品种类----</option>
								<c:forEach items="${goodsTypeList}" var="item0">
									<option value="${item0.id}">${item0.name}</option>
								</c:forEach>
							</select> <select class="typeSub">
								<option value="-1">----请选择子商品种类----</option>
							</select>
							<c:forEach items="${goodsTypeList}" var="item0">
								<select class="typeSub" id="typeSub">
									<c:forEach items="${item0.subGoodsTypeList}" var="item1">
										<option value="${item1.id}">${item1.name}</option>
									</c:forEach>
								</select>
							</c:forEach>
						</div>
					</div>
					<div class="space-4"></div>

					<div class="form-group" id="songda">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">送达时间</label>

						<div class="col-sm-9">
							<input class="input-sm" type="date" id="form-field-4"
								placeholder="" name="send_Time " />
							<div class="space-2"></div>

							<div class="help-block" id="input-size-slider"></div>
						</div>
					</div>
					<div class="form-group" id="yunfei">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">默认运费</label>

						<div class="col-sm-9">
							<input class="input-sm" type="text" id="form-field-4"
								placeholder="请输入默认运费" name="freigth" />
                <%--             <button type="button" style="margin-left:20px;" onclick="showTracking()">设置运费模板</button>--%>
							<div class="help-block" id="input-size-slider"></div>
						</div>
					</div>
					<%-- 
					<div class="form-group" id="tracking">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">运费模板</label>
						<div class="col-sm-9" id="tracking_model">
							<select id="s_province" name="province" class="province"></select>   <select
								id="s_city" name="city"></select>   

							<input class="input-sm" type="text" id="form-field-4"
								placeholder="运费" name="money" />
							<div class="space-2"></div>

							<div class="help-block" id="input-size-slider">
							<button type="button" style="margin-left:20px;" onclick="addTracking()" id="addTrackingButton">添加运费模板</button></div>
						</div>
					</div>--%>
					<div class="space-4"></div>

					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">商品库存</label>

						<div class="col-sm-9">
							<input class="input-sm" type="text" id="stock"
								placeholder="必填" name="stock" />
							<div class="space-2"></div>

							<div class="help-block" id="input-size-slider"></div>
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">赠送积分</label>

						<div class="col-sm-9">
							<input class="input-sm" type="text" id="form-field-4"
								placeholder="" name="score" />
							<div class="space-2"></div>

							<div class="help-block" id="input-size-slider"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 所属地块 </label>

						<div class="col-sm-9">
							<select name="blockId">
								<option value="0">----请选择所属地块----</option>
								<c:forEach items="${blockList}" var="item0">
									<option value="${item0.getBlockId()}">${item0.getBlockName()}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-4">商品图片</label>

						<div class="col-sm-9">
							<!-- <div id="box">
								<div id="test"></div>
							</div>
							 -->
							<div>
								<div class="line">
									<!--  <span class="span"> <input name="" type="text"
										id="viewfile"
										onmouseout="onmouseoutHide()"
										class="inputstyle" />
									</span> <label for="unload"
										onmouseover="onmouseoutShow()"
										class="file1">&nbsp;浏 览</label> <input type="file"
										onchange="this.style.display='none';"
										class="file"/>-->
									<input type="file" id="first_file" name="productPicture"></input>
								</div>
								<img src="" class="showImage"> <br class="br">
								<button class="continuePicture"
									onclick="return continuePicture()">继续添加</button>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 商品详情 </label>

						<div class="col-sm-9">
							<script id="editor" type="text/plain"
								style="width:1024px;height:500px;" name="goodsDetail"></script>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id="sm">
								<i class="icon-ok bigger-110"></i> 提交
							</button>
							&nbsp; &nbsp; &nbsp;
						</div>
					</div>

					<div class="hr hr-24"></div>

				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor('editor');
	var content='${goodsList.goodsDetail}';
	ue.ready(function(){
	ue.setContent(content);
});
</script>

<script type="text/javascript">


	//商品类型 二级联动设置
	$(document).ready(function() {
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
				if ($(this).attr("selected")) {
					$(".typeSub").attr("name", "");
					$(".typeSub").hide();
					$(".typeSub").eq(i).show();
					$(".typeSub").eq(i).attr("name", "typeIdChild");
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
		$("#typeParent").change();


	});
	/**文件选择的按钮
	 */
	$(".file").change(function() {
		var objUrl = getObjectURL(this.files[0]);
		//console.log("objUrl = "+objUrl) ;
		if (objUrl) {
			$(this).parent().next().attr("src", objUrl);
			$(this).parent().next().width("200");
			$(".continuePicture").show();
			var file = $(this).val();
			$(this).prev().prev().children().val(file);

		}
	});
	/**文件选择的按钮
	 */
	$("#first_file").change(function() {

		$(".continuePicture").show();

	});
	

</script>
<script type="text/javascript">
    String.prototype.endsWith=function(endStr){
      var d=this.length-endStr.length;
      return (d>=0&&this.lastIndexOf(endStr)==d)
    }
</script>
<script type="text/javascript">
$("#sm").click(function() {
	var price = $("#price").val();
	var hasVip = $("#vip").val();
	var name = $("#form-input-readonly").val();
	var weight = $("#weight").val();
	var reg_wei = /^\d+(\.\d)?$/;
	var pic = $("input[type=file]");
	var stock = $("#stock").val();
	var typeIdParent = $("#typeParent").val();
	var typeIdSub = $("#typeSub").val();
	var reg_price = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
	var reg_stock =  /^[1-9]+[0-9]*]*$/;
	var f = true;
	if(weight == ""||(!reg_wei.test(weight) )) {
		alert("未输入单位商品的重量或输入格式不符合要求")
		return false;	
	}	
	pic.each(function(i){
		var path = $(this).val();
		if(path == "" || (!path.endsWith(".png") && !path.endsWith(".jpeg") && !path.endsWith("jpg")) ) {
			alert("未上传商品图片，或者上传的文件不是所要求的图片格式!");
			f = false;
			return f;
		}
	});
	if(!f)
		return false;
	if(name == "") {
		alert("请填写所添加的商品的名称再提交！");
		return false;
	}

	
	if(stock == "" || !reg_stock.test(stock)) {
		alert("请填写商品库存,并以正确的格式！");
		return false;
	}
	if(price == "" || !reg_price.test(price)) {
		alert("未填写商品价格或所填入的内容不符合格式要求！");
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
	if(hasVip == "true") {
		var vipPrice = $("#form-field-1").val();
		if(vipPrice == "" || !reg_price.test(vipPrice)) {
			alert("未填写扫码价格或格式不符合要求！");
			return false;
		}
	}

});
</script>
</html>
