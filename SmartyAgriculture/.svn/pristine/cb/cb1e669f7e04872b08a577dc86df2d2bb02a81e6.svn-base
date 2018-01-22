<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生成购物卡</title>
    
    <link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/assets/css/bootstrap.min.css">
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery/jquery-1.7.1.min.js"></script>


  </head>
  
  <body>
  	<div class="container">
		<form action="goodsCard/generateCards" method="post" >
			<table class="table">
				<tr>
					<th>面值</th>
					<th>张数</th>
					<th>操作</th>
				</tr>
				<tr>
					<td>
						<select name="deno" class="form-control">
							<option value="100">100</option>
							<option value="300">300</option>
							<option value="500">500</option>
						</select>
					</td>
					<td>
						<input type="text" name="num" class="form-control" placeholder="请输入张数"/>
					</td>
					<td>
						<input type="submit" class="btn btn-default" value="提交" />
					</td>
				</tr>
			</table>
		<!--  
			<span class="label label-default"><lable>面值</lable></span>
			<div class="btn-group" data-toggle="buttons">
				<label class="btn btn-default">
					<input type="radio" name="deno" value="100" />100
				</label>
				
				<label class="btn btn-default">
					<input type="radio" name="deno" value="300" />300
				</label>
				
				<label class="btn btn-default">
					<input type="radio" name="deno" value="500" />500
				</label>
			</div>
			<br />
			<span class="label label-default"><lable>张数</lable></span>
			<input type="text" name="num" placeholder="请输入张数"/> <br />
			<input type="submit" class="btn btn-default" value="提交" />-->
			</form>
	</div>
	<script type="text/javascript">
		$("input[type=submit]").click(function(){
			var cardNum = $("input[name=num]").val();
			if(cardNum==""){
				alert("请输入张数再提交!");
				return false;
			}
			else {
				var format =  /^[1-9]+[0-9]*]*$/ ;
				if(!format.test(cardNum) ) {
					alert("请输入正整数");
					return false;
				}
				else {
					alert("面值："+$("select option:selected").text()
					+"张数："+$("input[name=num]").attr("value")+"申请成功");
				}
			}
		});
	</script>
  </body>
</html>
