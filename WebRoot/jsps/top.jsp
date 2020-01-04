<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>top</title>
	  <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	body {
		background: #FF5722;
		margin: 0px;
		color: #ffffff;
	}
	a {
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
	} 
	a:hover {
		text-decoration: underline;
		color: #ffffff;
		font-weight: 900;
	}
</style>
  </head>
  
  <body>
<div style="text-align: center;font-size: 40px;margin-top: 15px">大学生二手商品交易平台</div>
<div style="font-size: 12pt;margin-top: 25px;margin-left: 10px;">

<%-- 根据用户是否登录，显示不同的链接 --%>
<c:choose>
	<c:when test="${empty sessionScope.sessionUser }">
	<div class="layui-row">
		  <a href="<c:url value='/jsps/user/login.jsp'/>" target="_parent">
			  <i class="layui-icon layui-icon-prev" style="font-size: 16px; color: white;"></i>
			  用户登录
		  </a> |&nbsp;
		  <a href="<c:url value='/jsps/user/regist.jsp'/>" target="_parent">
			  用户注册
			  <i class="layui-icon layui-icon-next" style="font-size: 16px; color: white;"></i>
		  </a>
	</div>
	</c:when>
	<c:otherwise>
<%--		您好：${sessionScope.sessionUser.loginname }&nbsp;&nbsp;|&nbsp;&nbsp;--%>
<%--		<a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">查看购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;--%>
<%--		<a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">查看订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;--%>
<%--		<a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;--%>
<%--		<a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">退出</a>--%>
	<div class="layui-row">
		您好!
		<i class="layui-icon layui-icon-username" style="font-size: 15px; color: white;"></i>
			${sessionScope.sessionUser.loginname }&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/CartItemServlet?method=myCart'/>" target="body">
			<i class="layui-icon layui-icon-cart-simple" style="font-size: 18px; color: white;"></i>
			查看购物车
		</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/OrderServlet?method=myOrders'/>" target="body">
			<i class="layui-icon layui-icon-list" style="font-size: 18px; color: white;"></i>
			查看订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/GoodsServlet?method=addPre'/>" target="body">
			<i class="layui-icon layui-icon-list" style="font-size: 18px; color: white;"></i>
			发布商品</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/GoodsServlet?method=myGoods'/>" target="body">
			<i class="layui-icon layui-icon-list" style="font-size: 18px; color: white;"></i>
			我的商品</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/jsps/user/pwd.jsp'/>" target="body">
			<i class="layui-icon layui-icon-edit" style="font-size: 18px; color: white;"></i>
			修改密码</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/UserServlet?method=quit'/>" target="_parent">
			<i class="layui-icon layui-icon-close" style="font-size: 18px; color: white;"></i>
			退出</a>
	</div>
	</c:otherwise>
</c:choose>



</div>
  </body>
</html>
