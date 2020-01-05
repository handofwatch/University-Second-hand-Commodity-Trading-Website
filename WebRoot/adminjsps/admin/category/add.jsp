<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript">
		function checkForm() {
			if(!$("#cname").val()) {
				alert("分类名不能为空！");
				return false;
			}
			if(!$("#cdesc").val()) {
				alert("分类描述不能为空！");
				return false;
			}
			return true;
		}
	</script>
<style type="text/css">
	body {background: rgb(254, 238, 189);}
</style>
  </head>
  
  <body>
  <div style="margin:0 auto;width:200px;margin-top: 50px;font-size: 28px">
    <h3>添加分类</h3>
    <h1></h1>
  </div>
  <div style="margin-left: 480px">
    <p style="font-weight: 900; color: red">${msg }</p>
    <form action="<c:url value='/admin/AdminCategoryServlet'/>" method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value="add"/>
		<div style="width:100%; background-color:#feeebd; height: 32px; "></div>
    	分类名称：<input type="text" name="cname" id="cname"/><br/>
		<div style="width:100%; background-color:#feeebd; height: 24px; "></div>
    	分类描述：<textarea rows="5" cols="50" name="cdesc" id="cdesc"></textarea><br/>
		<div style="width:100%; background-color:#feeebd; height: 50px; "></div>
    	<input style="margin-left: 168px" type="submit" value="添加分类"/>
    	<input style="margin-left: 40px" type="button" value="返回" onclick="history.go(-1)"/>
    </form>
  </div>
  </body>
</html>
