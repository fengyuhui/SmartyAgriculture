<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/upload" + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<base href="<%=basePath%>"></base>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
	<title>中远农场</title>
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
		.container{
			width: 100%;
			height: 100%;
			position: absolute;
			left: 0;
			top: 0%;
		}
		.container .page{
			height: 100%;
			position: relative;
		}
	
		.xiangxiatishi{
			position: fixed;
			bottom: 20px;
			left: 50%;
			-webkit-transform:translateX(-50%);
			-webkit-animation:dong 1s linear 0s infinite alternate;
		}

		@-webkit-keyframes dong{
			from{
				bottom:20px;
			}
			to{
				bottom: 60px;
			}
		}
	</style>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/TBAlbum/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/TBAlbum/jquery.touchSwipe.min.js"></script>
	<script type="text/javascript">
		$(document).ready(
			function() {
				var nowpage = 0;
				//给最大的盒子增加事件监听
				$(".container").swipe(
					{
						swipe:function(event, direction, distance, duration, fingerCount) {
							 if(direction == "up"){
							 	nowpage = nowpage + 1;
							 }else if(direction == "down"){
							 	nowpage = nowpage - 1;
							 }
							 
							 
							 if(nowpage > ${imageList.size()}){
							 	nowpage = ${imageList.size()};
							 }
							
							 if(nowpage < 0){
							 	nowpage = 0;
							 }

							$(".container").animate({"top":nowpage * -100 + "%"},400);

							$(".page").eq(nowpage).addClass("cur").siblings().removeClass("cur");
						}
					}
				);
			}
		);
	</script>
 
</head>
<body onmousewheel="return false;">
	<div class="container">
		<c:forEach items="${imageList}" var="img">
			<div class="page">
				<img class="no1" src="${img}" style="height:100%;width:100%"/>
			</div>
		</c:forEach>
	</div>

	<img  class="xiangxiatishi" src="<%=request.getContextPath()%>/images/prompt.png" />

</body>
</html>