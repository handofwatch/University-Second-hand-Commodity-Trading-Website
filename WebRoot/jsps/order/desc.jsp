<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/order/desc.css'/>">
  </head>
  
<body>
	<div class="divOrder">
		<span>订单号：${order.oid }
		　　　下单时间：${order.ordertime }</span>
	</div>
	<div class="divContent">
		<div class="div2">
			<dl>
				<dt>收货人信息</dt>
				<dd>${order.address }</dd>
				<dt>收货人</dt>
				<dd>${order.buyername }</dd>
				<dt>手机号</dt>
				<dd>${order.phone }</dd>
			</dl>
		</div>
		<div class="div2">
			<dl>
				<dt>商品清单</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">商品名称</th>
							<th class="tt" align="left">单价</th>
							<th class="tt" align="left">状态</th>
						</tr>


<c:forEach items="${order.orderItemList }" var="item">
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/${item.goods.image_b }'/>"/>
								  <a href="<c:url value='/GoodsServlet?method=load&gid=${item.goods.gid }'/>">${item.goods.gname }</a>
								</div>
							</td>
							<td class="td">
								<span>&yen;${item.price }</span>
							</td>
							<td class="td">
								<span>


								<c:choose>
									<c:when test="${item.orderstatus eq 1 }">(等待付款)</c:when>
									<c:when test="${item.orderstatus eq 2 }">(准备发货)</c:when>
									<c:when test="${item.orderstatus eq 3 }">(等待确认)</c:when>
									<c:when test="${item.orderstatus eq 4 }">(交易成功)</c:when>
									<c:when test="${item.orderstatus eq 5 }">(已取消)</c:when>
								</c:choose>


								</span>
							</td>
						</tr>
<%--	不提供支付，要支付请在订单里支付，这是订单项--%>
	<c:if test="${item.orderstatus eq 1 and btn eq 'cancel'}">
		<a id="cancel" href="<c:url value='/OrderServlet?method=cancel&orderItemId=${item.orderItemId  }'/>">取消订单项</a><br/>
	</c:if>
	<c:if test="${item.orderstatus eq 3 and btn eq 'confirm'}">
		<a id="confirm" href="<c:url value='/OrderServlet?method=confirm&orderItemId=${item.orderItemId }'/>">确认收货</a><br/>
	</c:if>
</c:forEach>


					</table>
				</dd>
			</dl>
		</div>
		<div style="margin: 10px 10px 10px 550px;">
			<span style="font-weight: 900; font-size: 15px;">合计金额：</span>
			<span class="price_t">&yen;${order.total }</span><br/>
		</div>
	</div>
</body>
</html>

