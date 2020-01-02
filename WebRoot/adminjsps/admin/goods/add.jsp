<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>add Goods</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/goods/add.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
<script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
<script type="text/javascript">
$(function () {
	
	$("#btn").addClass("btn1");
	$("#btn").hover(
		function() {
			$("#btn").removeClass("btn1");
			$("#btn").addClass("btn2");
		},
		function() {
			$("#btn").removeClass("btn2");
			$("#btn").addClass("btn1");
		}
	);
	
	$("#btn").click(function() {
		var gname = $("#gname").val();
		var price = $("#price").val();
		var cid = $("#cid").val();
		var image_w = $("#image_w").val();
		var image_w2 = $("#image_w2").val();
		var image_b = $("#image_b").val();
		
		if(!gname || !price || !cid || !image_w || !image_b || !image_w2) {
			alert("图名、定价、分类、大图1、大图2、小图都不能为空！");
			return false;
		}
		
		if( isNaN(price)) {
			alert("定价必须是合法小数！");
			return false;
		}
		$("#form").submit();
	});
});


</script>
  </head>
  
  <body>
  <div>
   <p style="font-weight: 900; color: red;">${msg }</p>
   <form action="<c:url value='/GoodsServlet'/>" enctype="multipart/form-data" method="addGoods" id="form">
    <div>
	    <ul>
	    	<li>商品名：　<input id="gname" type="text" name="gname" value="请输入您的商品名" style="width:500px;"/></li>
	    	<li>大图1：　<input id="image_w" type="file" name="image_w"/></li>
			<li>大图2：　<input id="image_w2" type="file" name="image_w2"/></li>
	    	<li>小图：　<input id="image_b" type="file" name="image_b"/></li>
	    	<li>定价：　<input id="price" type="text" name="price" value="59.0" style="width:50px;"/>
	    </ul>
		<hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
		<table>
			<tr>
				<td colspan="3">描述：<input type="text" id="desc" name="desc" value="请您添加商品描述" style="width:100px;"/></td>
			</tr>
			<tr>
				<td>
					一级分类：<select name="cid" id="cid">
						<option value="">====请选择分类====</option>
<c:forEach items="${category }" var="category">
			    		<option value="${category.cid }">${category.cname }</option>
</c:forEach>

					</select>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" id="btn" class="btn" value="发布商品">
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
   </form>
  </div>

  </body>
</html>
