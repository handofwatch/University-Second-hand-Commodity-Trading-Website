<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>商品详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
	<script src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/desc.css'/>">
	<script src="<c:url value='/jsps/js/book/desc.js'/>"></script>
  </head>
  
  <body>
  <div class="divBookName">${goods.gname }</div>
  <div>
    <img align="top" src="<c:url value='/${goods.image_w }'/>" class="img_image_w"/>
    <div class="divBookDesc">
	    <ul>
	    	<li>商品编号：${goods.gid }</li>
				<li>价格：<span class="price_n">&yen;${goods.price }</span></li>
		<hr class="hr1"/>
		<table>
			<tr>
				<td colspan="3">
<%--					卖家：${goods.author }--%>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					描述：${goods.gdesc }
				</td>
			</tr>
		</table>
		<div class="divForm">
			<form id="form1" action="<c:url value='/CartItemServlet'/>" method="post">
				<input type="hidden" name="method" value="add"/>
				<input type="hidden" name="gid" value="${goods.gid }"/>
  			</form>
  			<a id="btn" href="javascript:$('#form1').submit();"></a>
  		</div>
			<div class="divForm">
				<form id="form2" action="<c:url value='/OrderServlet'/>" method="post">
					<input type="hidden" name="method" value="buyNow"/>
					<input type="hidden" name="gid" value="${goods.gid}"/>
					<input type="hidden" name="gname" value="${goods.gname}"/>
					<input type="hidden" name="image_w" value="${goods.image_w}"/>
					<input type="hidden" name="image_b" value="${goods.image_b}"/>
					<input type="hidden" name="price" value="${goods.price}"/>
					<input type="hidden" name="gdesc" value="${goods.gdesc}"/>
<%--					<input type="hidden" name="image_w2" value="${goods.image_w2}"/>--%>
<%--					<input type="hidden" name="cid" value="${goods.cid}"/>--%>
<%--					<input type="hidden" name="gstatus" value="${goods.gstatus}"/>--%>
				</form>
				<a id="btn2" href="javascript:$('#form2').submit();">立即购买</a>
			</div>
		</ul>
	</div>
  </div>
  </body>
</html>
