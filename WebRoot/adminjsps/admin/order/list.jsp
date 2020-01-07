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
			<tr>

			<div class="layui=row" style="font-size: 15px;margin: 2px">
				<div class="layui-col-md6">订单号：<a>${order.oid }</a></div>
				<div class="layui-col-md6">下单时间：${order.ordertime }</div>
			</div>


			<c:forEach items="${order.orderItemList }" var="orderItem">
				<table>
				<div class="layui-row">
					<p>
							订单项号:${orderItem.orderItemId}
					</p>
					<p>
								商品名称${orderItem.goods.gname}
					</p>
						<img border="0" width="70" src="<c:url value='/${orderItem.goods.image_b }'/>"/>
							${orderItem.price}元
					<c:choose>
						<c:when test="${orderItem.orderstatus eq 1 }">(等待付款)</c:when>
						<c:when test="${orderItem.orderstatus eq 2 }">(等待卖家发货)</c:when>
						<c:when test="${orderItem.orderstatus eq 3 }">(等待确认收货)</c:when>
						<c:when test="${orderItem.orderstatus eq 4 }">(交易成功)</c:when>
						<c:when test="${orderItem.orderstatus eq 5 }">(已取消)</c:when>
					</c:choose>
					<c:if test="${(orderItem.orderstatus eq 1) || (orderItem.orderstatus eq 2) ||(orderItem.orderstatus eq 3)||(orderItem.orderstatus eq 4)}">
                    <a href="${pageContext.request.contextPath}/admin/AdminOrderServlet?method=updateOrderStatus&orderItemId=${orderItem.orderItemId}">取消该订单项</a>
					</c:if>
				</div>
				</table>
			</c:forEach>
			<div >
				<span class="price_t" style="font-size: 15px">总金额：&yen;${order.total }</span>&nbsp;&nbsp;
			</div>
		</c:forEach>
		</tr>
	</div>

	<div style="background-color:gainsboro;border-top: 2px solid #FE7C2C;height: 35px;width: 100%;text-align: center;font-size: 15px">
		<%--<%@include file="/jsps/pager/pager.jsp" %>--%>下面已经没有了啦!
	</div>
</div>
</body>
</html>
