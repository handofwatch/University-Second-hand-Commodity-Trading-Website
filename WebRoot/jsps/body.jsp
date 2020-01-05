<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="">
    
    <title>body</title>
    
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
a {text-decoration: none;}
</style>
  </head>
  
  <body>
  <div style="margin: 0 auto;width:500px;">
      <h1>欢迎使用大学生二手商品交易平台!</h1>
  </div>
<%--    <a href="http://www.itcast.cn/" target="_top">--%>
<%--      <img src="http://localhost:8080/goods/images/itcast_link.gif" border="0" style="border:1px #DCD8D7 solid;"/>--%>
<%--    </a>--%>
<%--    <br/>--%>
<%--    <a href="http://subject.csdn.net/zhangxx/" target="_top">--%>
<%--      <img src="http://localhost:8080/goods/images/mhzxxls.jpg" border="0"/>--%>
<%--    </a>--%>
<%--    <a href="http://www.csdn.net/" target="_top">--%>
<%--      <img src="http://localhost:8080/goods/images/csdn.jpg" border="0"/>--%>
<%--    </a>--%>
  <div style="margin: 0 auto;width:200px;">
      <h1>您可以：</h1>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/CartItemServlet?method=myCart'/>">·查看购物车</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/OrderServlet?method=myOrders'/>">·查看订单</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/GoodsServlet?method=addPre'/>">·发布商品</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/GoodsServlet?method=myGoods'/>">·查看我的商品</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/jsps/user/pwd.jsp'/>">·修改密码</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/UserServlet?method=quit'/>">·退出当前账号</a></span>
  </div>
  <div style="margin-left: 320px">
      <img src="<c:url value='/images/tongji.jpg'/>" class="img"/>
  </div>
  </body>
</html>
