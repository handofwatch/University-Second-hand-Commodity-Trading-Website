<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>添加新管理员</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta content="content-type" content="text/html;charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
    <script src="/layui/src/layui.js"></script>
</head>

<body style="background: #fabd00">
<div style="position: absolute;width: 400px;height: 800px;z-index: -1;top: 0px">
    <img src="<c:url value='/images/huang_2_bgi.jpg'/>" height="100%" width="100%"/>
</div>
<div class="layui-main">
    <div class="layui-container" style="background: #F0F0F0; padding-bottom: 20px;border: black solid thin;width: 550px;margin-left: 15%;margin-top: 15%">
        <div  style="padding: 6%">
            <span class="spanTitle">添加新管理员</span>
        </div>
        <form action="<c:url value='/AdminServlet'/>" method="post" id="registForm">
            <input type="hidden" name="method" value="regist"/>

            <div class="layui-form layui-col-space5">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">用户名:</label>
                        <div class="layui-input-block">
                            <input value="" type="text" id = "adminname" name="adminname" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <label id="loginnameError" class="errorClass">${errors.adminname }</label>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">登陆密码:</label>
                        <div class="layui-input-block">
                            <input value="" type="password" id = "adminpwd" name="adminpwd" required  lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <label id="loginpassError" class="errorClass">${errors.loginpass }</label>
                </div>
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">确认密码:</label>
                        <div class="layui-input-block">
                            <input value="${form.reloginpass }" type="password" id = "reloginpass" name="reloginpass" required  lay-verify="required" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <label id="reloginpassError" class="errorClass">${errors.reloginpass }</label>
                </div>
                <div class="layui-form-item" style="padding-left: 112px">
                        <input type="submit" class="layui-btn" value="添加新管理员">
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>