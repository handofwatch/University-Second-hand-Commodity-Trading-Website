<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>我的商品列表</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/goods/list.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jsps/js/goods/list.js'/>"></script>
</head>

<body>

<ul>
    <c:forEach items="${goodsList.beanList }" var="goods">
        <li>
            <div class="inner">
                <a class="pic" href="<c:url value='/GoodsServlet?method=load&gid=${goods.gid }'/>"><img src="<c:url value='/${goods.image_b }'/>" border="0"/></a>
                <p class="price">
                    <span class="price_n">&yen;${goods.price }</span>
                </p>
                <p><a id="goodsname" title="${goods.gname }" href="<c:url value='/GoodsServlet?method=load&gid=${goods.gid }'/>">${goods.gname }</a></p>
                <p style="margin-top: 5px">
                    <c:choose>
                        <c:when test="${goods.gstatus eq 1 }">等待买家付款</c:when>
                        <c:when test="${goods.gstatus eq 2 }">请您发货</c:when>
                        <c:when test="${goods.gstatus eq 3 }">正在等待买家确认收货</c:when>
                        <c:when test="${goods.gstatus eq 4 }">交易已成功</c:when>
                        <c:when test="${goods.gstatus eq 5 }">正在出售</c:when>
                    </c:choose>
                <p>
                    <c:if test="${goods.gstatus eq 5 }">
                 <a id="edit" href ="<c:url value='/GoodsServlet?method=editpre&gid=${goods.gid }'/>">编辑商品</a>
                    </c:if>
                </p>
                <p>
                    <c:if test="${goods.gstatus eq 2 }">
                        <a href="<c:url value='/OrderServlet?method=sendout&gid=${goods.gid }'/>"> 如果您已发货，请点击这里 </a><br/>
                    </c:if>
                </p>
                <p>
                    <c:if test="${(goods.gstatus eq 4) || (goods.gstatus eq 5) }">
                    <a id="delete" href ="<c:url value='/GoodsServlet?method=delete&gid=${goods.gid }'/>">立即删除</a>
                    </c:if>
                </p>
            </div>
        </li>

    </c:forEach>












</ul>

<div style="float:left; width: 100%; text-align: center;">
    <hr/>
    <br/>
    <%@include file="/jsps/pager/pager.jsp" %>
</div>

</body>

</html>
