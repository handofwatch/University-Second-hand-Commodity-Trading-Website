<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>body.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <div style="margin: 0 auto;width:335px;">
      <h1>欢迎您，管理员${sessionScope.admin.adminname }!</h1>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/admin/AdminCategoryServlet?method=findAll'/>">·分类管理</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/adminjsps/admin/goods/main.jsp'/>">·商品管理</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/admin/AdminOrderServlet?method=findAll'/>">·订单管理</a></span>
  </div>
  <div style="font-size: 30px;font-family: 黑体;margin: 50px;">
      <span><a target="_top" href="<c:url value='/adminjsps/login.jsp'/>">·退出当前账号</a></span>
  </div>
  <div style="margin: 0 auto;width:394px">
      <img src="<c:url value='/images/tongji.jpg'/>" class="img"/>
  </div>
  </body>
</html>
