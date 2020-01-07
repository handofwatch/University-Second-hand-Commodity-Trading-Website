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
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/goods/desc.css'/>">
	<script src="<c:url value='/jsps/js/goods/desc.js'/>"></script>
  </head>
  
  <body>
  <div class="divBookName">${goods.gname }</div>
  <div>
    <img align="top" src="<c:url value='/${goods.image_w }'/>" class="img_image_w"/>
	  <img  src="<c:url value='/${goods.image_w2 }'/>" class="img_image_w2"/>
    <div class="divBookDesc">
	    <ul>
	    	<li>商品编号：${goods.gid }</li>
				<li>价格：<span class="price_n">&yen;${goods.price }</span></li>
		<hr class="hr1"/>
		<table>
			<tr>
				<td colspan="3">
<%--					卖家：${goods.User }--%>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					描述：${goods.gdesc }
				</td>
			</tr>
		</table>
			<c:choose>
				<c:when test="${goods.gstatus eq 1 }">等待买家付款</c:when>
				<c:when test="${goods.gstatus eq 2 }">请您发货</c:when>
				<c:when test="${goods.gstatus eq 3 }">正在等待买家确认收货</c:when>
				<c:when test="${goods.gstatus eq 4 }">交易已成功</c:when>
				<c:when test="${goods.gstatus eq 5 }">正在出售</c:when>
			</c:choose>

<c:if test="${goods.gstatus eq 5}">
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
				</form>
				<a id="btn2" href="javascript:$('#form2').submit();"></a>
			</div>
</c:if>
		</ul>
	</div>
  </div>
  </body>
</html>
