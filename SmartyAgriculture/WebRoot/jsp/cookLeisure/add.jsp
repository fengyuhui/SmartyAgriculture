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

<title>添加休闲与美食</title>

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
	href="<%=request.getContextPath()%>/css/cookLeisure/addCookLeisure.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/cookLeisure/addCookLeisure.js"></script>
<!-- ueditor -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"> </script>

</head>

<body>
	<div class="page-content">
		<div class="page-header">
			<h1>
				添加休闲美食信息 <small> <i class="icon-double-angle-right"></i>
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<form class="form-horizontal" action="cookLeisure/addPost"
					method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">标题</label>

						<div class="col-sm-9">
							<input type="text" class="col-xs-10 col-sm-5"
								id="form-input-readonly" value="" name="messageName" placeholder="必填"/> <span
								class="help-inline col-xs-12 col-sm-7"> <label
								class="middle"> <input class="ace" type="checkbox"
									id="id-disable-check" />
							</label>
							</span>
						</div>
					</div>
					<div class="form-group">
                                                            <label class="col-sm-3 control-label no-padding-right"
                                                                      for="form-input-readonly">简介</label>

                                                            <div class="col-sm-9">
                                                                      <textarea class="col-xs-10 col-sm-5"
                                                                                id="Intro" value="" name="subTitle" 
                                                                                onKeyDown="textdown(event)" onKeyUp="textUp()" 
                                                                                clos="100" rows="5" placeholder="100字以内"></textarea>
                                                                                 <span
                                                                                class="help-inline col-xs-12 col-sm-7"> <label
                                                                                class="middle"> <input class="ace" type="checkbox"
                                                                                          id="id-disable-check" />
                                                                      </label>
                                                                      </span>
                                                            </div>
                                                  </div>
                                                  
                                                  <div class="form-group">
                                                            <label class="col-sm-3 control-label no-padding-right"
                                                                      for="form-input-readonly">营养专家</label>

                                                            <div class="col-sm-9">
                                                                      <input type="text" class="col-xs-10 col-sm-5"
                                                                                id="form-input-readonly" value="" name="dietitian" /> <span
                                                                                class="help-inline col-xs-12 col-sm-7"> <label
                                                                                class="middle"> <input class="ace" type="checkbox"
                                                                                          id="id-disable-check" />
                                                                      </label>
                                                                      </span>
                                                            </div>
                                                  </div>

					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">是否置顶</label>
						<div class="col-sm-9">
							<select id="topOrNot" name="topOrNot">
								<option value="0">否</option>								
								<option value="1">是</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-input-readonly">图片</label>
						<div class="col-sm-9">
							<input type="file" id="first_file" name="file"></input>
						</div>
						
					</div>					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 信息类型 </label>

						<div class="col-sm-9">
							<select id="messageType" name="messageType" id="messageType" >
								<option value="-1">----请选择信息类型----</option>
									<option value="1">有机知识</option>
									<option value="2">营养健康</option>
									<option value="3">美食烹饪</option>
								</select>
						</div>
					</div>			

					<div class="space-4"></div>                 
					
	<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-2"> 信息详情 </label>

						<div class="col-sm-9">
							 <script id="editor" type="text/plain" style="width:1024px;height:500px;" name="currentMessage"></script>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" id="judge">
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

<script type="text/javascript">


//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ue = UE.getEditor('editor');


function isFocus(e){
    alert(UE.getEditor('editor').isFocus());
    UE.dom.domUtils.preventDefault(e)
}
function setblur(e){
    UE.getEditor('editor').blur();
    UE.dom.domUtils.preventDefault(e)
}
function insertHtml() {
    var value = prompt('插入html代码', '');
    UE.getEditor('editor').execCommand('insertHtml', value)
}
function createEditor() {
    enableBtn();
    UE.getEditor('editor');
}
function getAllHtml() {
    alert(UE.getEditor('editor').getAllHtml())
}
function getContent() {
    var arr = [];
    arr.push("使用editor.getContent()方法可以获得编辑器的内容");
    arr.push("内容为：");
    arr.push(UE.getEditor('editor').getContent());
    alert(arr.join("\n"));
}
function getPlainTxt() {
    var arr = [];
    arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
    arr.push("内容为：");
    arr.push(UE.getEditor('editor').getPlainTxt());
    alert(arr.join('\n'))
}
function setContent(isAppendTo) {
    var arr = [];
    arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
    UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
    alert(arr.join("\n"));
}
function setDisabled() {
    UE.getEditor('editor').setDisabled('fullscreen');
    disableBtn("enable");
}

function setEnabled() {
    UE.getEditor('editor').setEnabled();
    enableBtn();
}

function getText() {
    //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
    var range = UE.getEditor('editor').selection.getRange();
    range.select();
    var txt = UE.getEditor('editor').selection.getText();
    alert(txt)
}

function getContentTxt() {
    var arr = [];
    arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
    arr.push("编辑器的纯文本内容为：");
    arr.push(UE.getEditor('editor').getContentTxt());
    alert(arr.join("\n"));
}
function hasContent() {
    var arr = [];
    arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
    arr.push("判断结果为：");
    arr.push(UE.getEditor('editor').hasContents());
    alert(arr.join("\n"));
}
function setFocus() {
    UE.getEditor('editor').focus();
}
function deleteEditor() {
    disableBtn();
    UE.getEditor('editor').destroy();
}
function disableBtn(str) {
    var div = document.getElementById('btns');
    var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
    for (var i = 0, btn; btn = btns[i++];) {
        if (btn.id == str) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        } else {
            btn.setAttribute("disabled", "true");
        }
    }
}
function enableBtn() {
    var div = document.getElementById('btns');
    var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
    for (var i = 0, btn; btn = btns[i++];) {
        UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
    }
}

function getLocalData () {
    alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
}

function clearLocalData () {
    UE.getEditor('editor').execCommand( "clearlocaldata" );
    alert("已清空草稿箱")
}

</script>
<script type="text/javascript">
	
	/**文件选择的按钮
	 */
	$(".file").change(function() {
		var objUrl = getObjectURL(this.files[0]);
		//console.log("objUrl = "+objUrl) ;
		if (objUrl) {
			$(this).parent().next().attr("src", objUrl);
			$(this).parent().next().width("200");
			$(".continuePicture").show();
			var file=$(this).val();
			$(this).prev().prev().children().val(file);
			
		}
	});
	/**文件选择的按钮
	 */
	$("#first_file").change(function() {

			$(".continuePicture").show();
	
		}
	);
</script>
</body>
<script type="text/javascript">
    String.prototype.endsWith=function(endStr){
      var d=this.length-endStr.length;
      return (d>=0&&this.lastIndexOf(endStr)==d)
    }
</script>

<script type="text/javascript">
function textdown(e)
{	textevent = e;
	if(textevent.keyCode == 8)
		{
		return;
		}
	if(document.getElementById('Intro').value.length >= 100) 
	{
		alert("字数达上限");
		if(!document.all)
		{
			textevent.preventDefault();
		}
		else
		{
		textevent.returnValue = false;
		}
	}
}
	function textup()
	{
		var s = document.getElementById('Intro').value;
		//判断ID为text的文本区域字数是否超过100个
		if(s.length > 100) 
	{
		document.getElementById('Intro').value=s.substring(0,100);
	}
}
</script>
<script type="text/javascript">
$("#judge").click(function() {
	var messageType = $("#messageType").val();
	var name = $("#form-input-readonly").val();
	var Intro = $("#Intro").val();
	var pic = $("#first_file").val();
	
	if(name == "") {
		alert("请填写标题！");
		return false;
	}
	if (Intro == ""){
		alert("请填写信息简介！");
		return false;
	}
	if(messageType == "" || messageType == "-1") {
		alert("请选择信息类型！");
		return false;
	}
	if(pic == "" || (!pic.endsWith(".png") && !pic.endsWith(".jpeg") &&!pic.endsWith("jpg")) ) {
		alert("没有上传图片或者图片不是所要求的格式！");
		return false;
	}
});
</script>
</html>
