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
	<div style="border: 5px solid #efeae5;width: 880px;height: 280px;margin-top: 20px;">
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
			<div style="margin-left: 50px;margin-top: 20px;font-size: 20px;font-weight: bolder;color:#b83f18">
			<c:choose>
				<c:when test="${goods.gstatus eq 1 }">等待买家付款</c:when>
				<c:when test="${goods.gstatus eq 2 }">请您发货</c:when>
				<c:when test="${goods.gstatus eq 3 }">正在等待买家确认收货</c:when>
				<c:when test="${goods.gstatus eq 4 }">交易已成功</c:when>
				<c:when test="${goods.gstatus eq 5 }">正在出售</c:when>
			</c:choose>
			</div>
		</ul>
	</div>
</div>
</body>
</html>
