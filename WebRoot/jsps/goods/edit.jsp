<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>add Goods</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/goods/add.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/jquery/jquery.datepick.css'/>">
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jquery/jquery.datepick.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/jquery/jquery.datepick-zh-CN.js'/>"></script>
    <script type="text/javascript">
        $(function () {

            $("#btn").addClass("btn1");
            $("#btn").hover(
                function() {
                    $("#btn").removeClass("btn1");
                    $("#btn").addClass("btn2");
                },
                function() {
                    $("#btn").removeClass("btn2");
                    $("#btn").addClass("btn1");
                }
            );

            $("#btn").click(function() {
                var gname = $("#gname").val();
                var price = $("#price").val();
                var cid = $("#cid").val();
                var gstatus = $("#gstatus").val();

                if(!gname || !price || !cid || !gstatus) {
                    alert("商品名、定价、分类、商品状态都不能为空！");
                    return false;
                }

                if( isNaN(price)) {
                    alert("定价必须是合法小数！");
                    return false;
                }
                $("#form").submit();
            });
        });


    </script>
</head>

<body>
<div>
    <p style="font-weight: 900; color: red;">${msg }</p>
    <form action="<c:url value='/EditGoodsServlet'/>" enctype="multipart/form-data" method="POST" id="form">
        <input id="gid" type="hidden" name="gid" value=${goods.gid} />
        <div>
            <ul>
                <li>商品名：　<input id="gname" type="text" name="gname" value=${goods.gname} style="width:500px;"/></li>
                <li>大图1：　<input id="image_w" type="file" name="image_w"/>不上传即维持原图</li>
                <li>大图2：　<input id="image_w2" type="file" name="image_w2"/></li>
                <li>定价：　<input id="price" type="text" name="price" value=${goods.price} style="width:50px;"/>
            </ul>
            <hr style="margin-left: 50px; height: 1px; color: #dcdcdc"/>
            <table>
                <tr>
                    <td colspan="3">描述：<input type="text" id="gdesc" name="gdesc" value=${goods.gdesc} style="width:100px;"/></td>
                </tr>
                <tr>
                    <td>
                        一级分类：<select name="cid" id="cid">
                        <option value="">====请选择分类====</option>
                        <c:forEach items="${category }" var="category">
                            <option value="${category.cid }">${category.cname }</option>
                        </c:forEach>

                    </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        商品状态：<select name="gstatus" id="gstatus">

                        <option value="">====请选择状态====</option>
                        <option value="4">已售出 </option>
                        <option value="5">未售出</option>

                    </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="button" id="btn" class="btn" value="更新商品">
                    </td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </div>
    </form>
</div>

</body>
</html>
