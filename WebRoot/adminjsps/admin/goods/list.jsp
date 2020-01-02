<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>图书分类</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/goods/list.css'/>">
<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
<script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>

<script type="text/javascript" src="<c:url value='/adminjsps/admin/js/goods/list.js'/>"></script>
  </head>
  
  <body>
<div class="divBook">
<ul>


<c:forEach items="${pb.beanList }" var="goods">
 <li>
  <div class="inner">
    <a class="pic" href="<c:url value='/admin/AdminGoodsServlet?method=load&bid=${goods.bid }'/>"><img src="<c:url value='/${goods.image_b }'/>" border="0"/></a>
    <p class="price" >
		<span class="price_n">&yen;${goods.currPrice }</span>
		<span class="price_r">&yen;${goods.price }</span>
		(<span class="price_s">${goods.discount }折</span>)
	</p>
	<c:url value="/admin/AdminGoodsServlet" var="authorUrl">
		<c:param name="method" value="findByAuthor"/>
		<c:param name="author" value="${goods.author }"/>
	</c:url>
	<c:url value="/admin/AdminGoodsServlet" var="pressUrl">
		<c:param name="method" value="findByPress"/>
		<c:param name="press" value="${goods.press }"/>
	</c:url>
	<p><a id="bookname" title="${goods.bname }" href="<c:url value='/admin/AdminGoodsServlet?method=load&bid=${goods.gid }'/>">${goods.gname }</a></p>
	<p><a href="${authorUrl }" name='P_zz' title='${goods.author }'>${goods.author }</a></p>
	<p class="publishing">
		<span>出版社：</span><a href="${pressUrl }">${goods.press }</a>
	</p>
  </div>
 </li>
</c:forEach>



 


</ul>
</div>
<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>
  </body>
 
</html>

