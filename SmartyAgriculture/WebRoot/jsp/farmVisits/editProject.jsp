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
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/farmVisits/fileinput.min.css" />

<script src="<%=request.getContextPath()%>/js/album/album.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/farmVisits/fileinput.min.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/css/assets/js/bootstrap.min.js"
	type="text/javascript"></script>

</head>

<body>

		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<div class="main-content" style="margin-left:0px;">
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="hr hr-18 dotted hr-double"></div>
								<h4 class="pink">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="#modal-table" role="button" class="green"
										data-toggle="modal" style="color: #438eb9!important;font-size: 26px;">编辑项目</a>
								</h4>								
								<div class="hr hr-18 dotted hr-double"></div>
								<!-- #nav-search -->
								<div class="row">
									<div class="col-xs-12">
									
								
									
									<!-- 表单开始 -->
									<form class="form-horizontal" action="farmVisits/updatePost/${id}"
										method="post" enctype="multipart/form-data">
									  <div class="form-group">
									  	<div class="heading"> 
									    	<label class="control-label" for="inputEmail">
									    		项目标题 </label>
										</div>
										<div class="controls input-append">
										  <input class="span2" id="appendedInputButton" type="text" name="title" value="${title}" required pattern=".{1,255}"
										   placeholder="1~255个字符" style="width:80%;" readonly="readonly">
										  <button class="btn btn-warning btn-xs" id="btnEdit" type="button" onclick="editTitle()">
										  	<span class="glyphicon glyphicon-edit"></span>修改</button>
										</div>
									  </div>
									  
									  <div class="form-group">
										  <div class="heading"> 
									    	<label class="control-label" for="inputEmail">
									    		项目描述 </label>
										</div>
										<div class="controls input-append">
										  <textarea class="span2" id="appendedInputButton" name="detail" required maxlength="1000"
											placeholder="1000字以内" style="width:80%;" readonly="readonly">${detail}</textarea>
										  <button class="btn btn-warning btn-xs" id="btnEdit" type="button" onclick="editDetail()">
										  	<span class="glyphicon glyphicon-edit"></span>修改</button>
										</div>
									  </div>
									  
									  <div class="form-group">    
									    <!-- 按钮组 -->
									    <div class="heading"> 
									    		<div>
									    			<label class="control-label" for="inputPassword">
									    				参观类型</label>
									    		</div>
									    		<div>
													<button id="build" type="button" class="btn btn-success btn-xs" onclick="append()"> 
													<span class="glyphicon glyphicon-plus"></span>添加 
													</button> 
													<button id="btnEdit" type="button" class="btn btn-warning btn-xs" onclick="edit()"> 
													<span class="glyphicon glyphicon-edit"></span>修改 
													</button> 
													<button id="btnDel" type="button" class="btn btn-danger btn-xs" onclick="delTr()"> 
													<span class="glyphicon glyphicon-minus" ></span>删除 
													</button> 
												</div>
										</div> 
									    <div class="controls input-append" >
									      <!-- 表格开始 -->
									      <table class="table table-bordered" id="ta" style="width:80%;">
											<thead>
											<tr>
											<th class="center" style="background-color: transparent;">
												<label>全选
											    	<input type="checkbox" id="checkAll" >
												</label>
											</th>
											<th class="center" style="width:20%; background-color: transparent;">类型编号</th>
											<th style="background-color: transparent;">类型标题</th>
											<th style="background-color: transparent;">价格</th>
											</tr>
											</thead>
											
											<tbody >
											<c:forEach items="${typeList}" var="type">
											<tr>
											<td class="center"> 
											      <input type="checkbox" name="ckb">     
											</td>
											<td class="center"> 
											<input id="tm1" type="text" style="width: 80%; border: 0;"
												name="typeId" value="${type.id}" readonly="readonly">
											      
											</td>
											<td>
											<input id="tm1" type="text" style="width: 100%; border: 0;" required pattern=".{1,255}"
												placeholder="1~255个字符" name="typeTitle" value="${type.title}" readonly="readonly">
											</td> 
											<td>
											<input type="text" style="width: 100%; border: 0;" required pattern="^\+?(?:[1-9]\d*(?:\.\d{1,2})?|0\.(?:\d[1-9]|[1-9]\d))$"
								placeholder="价格在0.00-99999.99之间" name="price" value="${type.price}" readonly="readonly">
											</td> 
											</tr>
											</c:forEach> 
											</tbody>
										</table> 
									      <!-- 表格结束 -->
									    </div>
									  </div>
									  
									 <!-- 修改图片开始 -->
									<div class="form-group" style="width:80%;" id="imgC"> 
									 <div class="heading"> 
									  <div>
									    <label class="control-label" for="inputEmail">编辑图片</label>
									    </div>
									    <div>
									<button id="build" type="button" class="btn btn-success btn-xs" onclick="addImg()"> 
									<span class="glyphicon glyphicon-plus"></span>添加 
									</button> 
									<button id="btnDel" type="button" class="btn btn-danger btn-xs" onclick="delImg()"> 
									<span class="glyphicon glyphicon-minus" ></span>删除 
									</button> 
									<label>
									    <input type="checkbox" id="checkAll2" name="ckbAll">&nbsp;&nbsp;全选
									</label>
									</div>
									</div></br>
									
									<div class="heading" id="upload"> 
									 										  
									</div>
									
									<div class="controls input-append" id="imgSubc">
									  <div class="row">
									
									
									  
									  <!-- 循环展示图片 -->
									  <c:forEach items="${pictureList}" var="item">
									    <div class="col-sm-6 col-md-3">
									    <input type="checkbox" id="ckb" name="ckbImg">
										<input type="hidden" name="oldImgPath" value="${item}">
									        <a href="javascript:void(0);" class="thumbnail">
									            <img src="${item}"
									 				alt="通用的占位符缩略图">
									        </a>
									    </div>
									    </c:forEach>
									    <!-- 展示结束 -->
									  </div>
									 
									 
									</div>
									  </div>
									  <!-- 修改图片结束 -->
									
									
										
									  <div class="form-group">
									    <div class="controls">
									  	 <button type="submit" class="btn">提交</button>
									    </div>
									  </div>
									</form>
									 
									</div>
								</div>	
									
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->
			</div><!-- /.main-container-inner -->
		</div><!-- /.main-container -->
		
	<script src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>
	<script type="text/javascript">
		// 全选按钮，选择参观类型
		$(function(){  
			var $list = $("table");
        	$list.on("change","#checkAll",function(){
                $cks = $list.find("input[type='checkbox']").not("#checkAll");
          		this.checked ? $cks.prop("checked",true) : $cks.prop("checked",false)          		
        	});
    	})
    	// 全选按钮2,选择图片
		$(function(){  
			var $list = $("#imgC");
        	$list.on("change","#checkAll2",function(){
                $cks = $list.find("input[type='checkbox']").not("#checkAll2");
          		this.checked ? $cks.prop("checked",true) : $cks.prop("checked",false)          		
        	});
    	})
		
		// 添加表格
		function append(){
			var strAppend = "<tr><td class='center'><input type='checkbox' name='ckb'></td>\
							<td class='center'>不可填</td>\
							<td><input type='text' style='width: 100%; border: 0; color: #007799;' name='typeTitleNew' value=''></td>\
							<td><input type='text' style='width: 100%; border: 0; color: #007799;' name='priceNew' value=''></td></tr>";
			$("tbody").append(strAppend); 
		}
		// 编辑表格
		function edit(){
			$('tbody').find('[type="text"]').not('[name="typeId"]').removeAttr('readonly');
		}
		// 编辑标题
		function editTitle(){
			$("form").find('input[name="title"]').removeAttr('readonly');
		}
		// 编辑项目描述
		function editDetail(){
			$("form").find('textarea[name="detail"]').removeAttr('readonly');
		}
		// 删除表格的行
		function delTr(){
			//获取选中的复选框，然后循环遍历删除
		    var ckbs=$("input[name='ckb']:checked");
		    if(ckbs.size()==0){
		    	alert("要删除指定行，需选中要删除的行！");
		        return;
		    }
			var msg = "您真的确定要删除吗？\n\n请确认！"; 
			if (confirm(msg)==true){ 
			    ckbs.each(function(){
			     	$(this).parent().parent().remove();
			    });
			}else{ 
				return; 
			} 
			
		}
		// 删除图片
		function delImg(){
			//获取选中的复选框，然后循环遍历删除
		    var ckbs=$("input[name='ckbImg']:checked");
		    if(ckbs.size()==0){
		    	alert("要删除指定图片，需选中要删除的图片！");
		        return;
		    }
			var msg = "您真的确定要删除吗？\n\n请确认！"; 
			if (confirm(msg)==true){ 
			    ckbs.each(function(){
			     	$(this).parent().remove();
			    });
			}else{ 
				return; 
			} 
			
		}
		
		/* $(document).ready(function(){
			var txt1 = "<div class='controls input-append'>\
							<label class='col-sm-3 control-label no-padding-right' for='form-input-readonly'>\
								农场图片</label>	\
							<div class='col-sm-9'>\
								<input type='file' name='file[]'>\
							</div>\
						</div>";
			$("#build").click(function(){
				$("#imgSubc").before(txt1);
			}); 
		});*/
		function addImg(){
			var txt1 = "<div class='heading'></div><div class='controls input-append'>\
			<input type='checkbox' id='ckb' name='ckbImg'>\
			<input class='span2' id='appendedInputButton' type='file' style='width:80%;' name='file' required></div>"
			$("#upload").append(txt1);
		} 
		
		
		
	</script>
</body>
</html>
