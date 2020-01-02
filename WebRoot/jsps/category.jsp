<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>category</title>
	<base target="body">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">

	  <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">
	  <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	  <script type="text/javascript" src="<c:url value='/menu/category.js'/> "></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
<script language="javascript">
	// function Category(objName){
	// 	this.obj=objName;
	// 	this.categoryList=[];
	// }
	// Category.prototype.getCategory=function(categoryName,url,frameName){
	// 	var len=this.categoryList.length;
	// 	this.categoryList[len]=new categoryItem(categoryName,url,frameName);
	// }
	// function categoryItem(itemName,url,frameName) {
	// 	this.itemName=itemName;
	// 	this.url=url;
	// 	this.frameName=frameName;
	// }
	// Category.prototype.toString=function(){
	// 	var str="";
	// 	for(var i in this.categoryList){
	// 		str += '<li class=\"layui-nav-item\" onclick=\"skip(\''+this.categoryList[i].url+'\',\''+this.categoryList[i].frameName+'\')">'+'<a href="">'+this.categoryList[i].itemName+'</a></li>'
	// 	}
	// 	return str;
	// }
	// function skip(url, frameName) {
	// 	if(parent[frameName]) {
	// 		parent[frameName].location.href=url;
	// 	} else {
	// 		location.href=url;
	// 	}
	// }

	var categorybar = new Category("categorybar");
	$(function() {
		<c:forEach items="${category}" var="category">
			categorybar.getCategory("${category.cname}","/goods/GoodsServlet?method=findByCategory&cid=${category.cid}","body");
		</c:forEach>
		$("#categoryHTML").html(categorybar.toString());
	});

</script>
</head>
  
<body>
<ul class="layui-nav" style="background-color: #f6a828;text-align: left">
	<div id="categoryHTML">这里是分类</div>

</ul>
</body>
</html>
