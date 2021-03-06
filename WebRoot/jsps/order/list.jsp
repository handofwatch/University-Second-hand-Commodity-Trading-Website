<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/list.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
	  <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">

  <%--    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>--%>
  </head>
  
  <body>
<div class="divMain">
	<div class="divTitle">
		<span style="margin-left: 150px;margin-right: 280px;">订单信息</span>
	</div>

	<div class="layui-container" style="padding: 0;width: 100%;">

<c:forEach items="${pb.beanList }" var="order">

			<div class="layui=row" style="font-size: 15px;margin: 2px">
				<div class="layui-col-md6">订单号：<a  href="<c:url value='/OrderServlet?method=load&oid=${order.oid }'/>">${order.oid }</a></div>
				<div class="layui-col-md6">下单时间：${order.ordertime }</div>
			</div>


  			<c:forEach items="${order.orderItemList }" var="orderItem">
			<div class="layui-row">
				<a class="link2" href="<c:url value='/GoodsServlet?method=load&gid=${orderItem.goods.gid }'/>">
	    			<img border="0" width="70" src="<c:url value='/${orderItem.goods.image_b }'/>"/>
				</a>
	  			<c:choose>
		  			<c:when test="${orderItem.orderstatus eq 1 }">(等待付款)</c:when>
		  			<c:when test="${orderItem.orderstatus eq 2 }">(等待卖家发货)</c:when>
		  			<c:when test="${orderItem.orderstatus eq 3 }">(等待您确认收货)</c:when>
		  			<c:when test="${orderItem.orderstatus eq 4 }">(交易成功)</c:when>
		  			<c:when test="${orderItem.orderstatus eq 5 }">(已取消)</c:when>
	  			</c:choose>

	  			<c:if test="${orderItem.orderstatus eq 3 }">
		  			<a href="<c:url value='/OrderServlet?method=confirm&orderItemId=${orderItem.orderItemId }'/>">确认收货</a><br/>
	  			</c:if>
			</div>
  			</c:forEach>
			<div >
				<span class="price_t" style="font-size: 15px">总金额：&yen;${order.total }</span>&nbsp;&nbsp;
			</div>
			<div class="layui-row" style="border-bottom: #9F9F9F solid;text-align: right;font-size: 15px">
					<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }'/>">
						查看详情<i class="layui-icon layui-icon-list" style="font-size: 17px; color: black;"></i></a> &nbsp;&nbsp;| &nbsp;&nbsp;
					<c:if test="${order.orderItemList[0].orderstatus eq 1 }">
						<a href="<c:url value='/OrderServlet?method=payment&oid=${order.oid }'/>">
							前往支付<i class="layui-icon layui-icon-release" style="font-size: 17px; color: black;"></i></a> &nbsp;&nbsp;| &nbsp;&nbsp;
						<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }&btn=cancel'/>">
							取消物品<i class="layui-icon layui-icon-close" style="font-size: 17px; color: black;"></i></a>
					</c:if>
			</div>
</c:forEach>
</div>

	<div style="background-color:gainsboro;border-top: 2px solid #FE7C2C;height: 35px;width: 100%;text-align: center;font-size: 15px">
<%--<%@include file="/jsps/pager/pager.jsp" %>--%>下面已经没有了啦!
	</div>
</div>
  </body>
</html>
