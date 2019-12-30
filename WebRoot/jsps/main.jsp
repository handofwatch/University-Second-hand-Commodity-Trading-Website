<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>main</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/main.css'/>">
	  <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">

  </head>
  
  <body>
  <div class="layui-container">
	  <div class="layui-col" style="height: 120px;margin-top: 5px;border: solid orange;">
		  <iframe frameborder="0" src="<c:url value='/jsps/top.jsp'/>" name="top"></iframe>
	  </div>
	  <div class="layui-col" style="height: 50px;border: 5px orange solid;">
		  <iframe frameborder="0" src="<c:url value='/jsps/search.jsp'/>" name="search"></iframe>
	  </div>
	  <div class="layui-col" style="height: 70px;">
		  <iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>" name="category"></iframe>
	  </div>
	  <div class="layui-col"style="height: 670px;">
		  <iframe frameborder="0" src="<c:url value='/jsps/body.jsp'/>" name="body"></iframe>
	  </div>
  </div>
<%--<table class="table" align="center">--%>
<%--	<tr class="trTop">--%>
<%--		<td colspan="2" class="tdTop">--%>
<%--			<iframe frameborder="0" src="<c:url value='/jsps/top.jsp'/>" name="top"></iframe>--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td class="tdSearch" style="border-bottom-width: 0px;">--%>
<%--			<iframe frameborder="0" src="<c:url value='/jsps/search.jsp'/>" name="search"></iframe>--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td>--%>
<%--			<iframe frameborder="0" src="<c:url value='/CategoryServlet?method=findAll'/>" name="category"></iframe>--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style="border-top-width: 0px;">--%>
<%--			<iframe frameborder="0" src="<c:url value='/jsps/body.jsp'/>" name="body"></iframe>--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--</table>--%>
  </body>
</html>
