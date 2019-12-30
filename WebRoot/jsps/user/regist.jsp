<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
      <meta content="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/regist.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/user/regist.js'/>"></script>
    <script src="/layui/src/layui.js"></script>
  </head>
  
  <body style="background: #fabd00">
  <div style="position: absolute;width: 400px;height: 800px;z-index: -1;top: 0px">
      <img src="<c:url value='/images/huang_2_bgi.jpg'/>" height="100%" width="100%"/>
  </div>
<div class="layui-main">
  <div class="layui-container" style="background: #F0F0F0; padding-bottom: 20px;border: black solid thin;width: 550px;margin-left: 15%;margin-top: 15%">
    <div  style="padding: 6%">
      <span class="spanTitle">新用户注册</span>
    </div>
<form action="<c:url value='/UserServlet'/>" method="post" id="registForm">
	<input type="hidden" name="method" value="regist"/>

  <div class="layui-form layui-col-space5">
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">用户名:</label>
          <div class="layui-input-block">
            <input value="${form.loginname }" type="text" id = "loginname" name="loginname" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
<%--            <label id="loginnameError" class="errorClass">${errors.loginname }</label>--%>
          </div>
        </div>
        <label id="loginnameError" class="errorClass">${errors.loginname }</label>
      </div>

<%--      <tr>--%>
<%--        <td class="tdText">用户名：</td>--%>
<%--        <td class="tdInput">--%>
<%--          <input class="inputClass" type="text" name="loginname" id="loginname" value="${form.loginname }"/>--%>
<%--        </td>--%>
<%--        <td class="tdError">--%>
<%--          <label class="errorClass" id="loginnameError">${errors.loginname }</label>--%>
<%--        </td>--%>
<%--      </tr>--%>
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">登陆密码:</label>
          <div class="layui-input-block">
            <input value="${form.loginpass }" type="password" id = "loginpass" name="loginpass" required  lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
<%--            <label id="loginpassError" class="errorClass">${errors.loginpass }</label>--%>
          </div>
        </div>
        <label id="loginpassError" class="errorClass">${errors.loginpass }</label>
      </div>
<%--      <tr>--%>
<%--        <td class="tdText">登录密码：</td>--%>
<%--        <td>--%>
<%--          <input class="inputClass" type="password" name="loginpass" id="loginpass" value="${form.loginpass }"/>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--          <label class="errorClass" id="loginpassError">${errors.loginpass }</label>--%>
<%--        </td>--%>
<%--      </tr>--%>
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">确认密码:</label>
          <div class="layui-input-block">
            <input value="${form.reloginpass }" type="password" id = "reloginpass" name="reloginpass" required  lay-verify="required" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
<%--            <label id="reloginpassError" class="errorClass">${errors.reloginpass }</label>--%>
          </div>
        </div>
        <label id="reloginpassError" class="errorClass">${errors.reloginpass }</label>
      </div>
<%--      <tr>--%>
<%--        <td class="tdText">确认密码：</td>--%>
<%--        <td>--%>
<%--          <input class="inputClass" type="password" name="reloginpass" id="reloginpass" value="${form.reloginpass }"/>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--          <label class="errorClass" id="reloginpassError">${errors.reloginpass}</label>--%>
<%--        </td>--%>
<%--      </tr>--%>
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">Email:</label>
          <div class="layui-input-block">
            <input value="${form.email}" type="email" id = "email" name="email" required  lay-verify="required" placeholder="请输入邮箱" autocomplete="off" class="layui-input">
<%--            <label id="emailError" class="errorClass">${errors.email }</label>--%>
          </div>
        </div>
        <label id="emailError" class="errorClass">${errors.email }</label>
      </div>
<%--      <tr>--%>
<%--        <td class="tdText">Email：</td>--%>
<%--        <td>--%>
<%--          <input class="inputClass" type="text" name="email" id="email" value="${form.email }"/>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--          <label class="errorClass" id="emailError">${errors.email}</label>--%>
<%--        </td>--%>
<%--      </tr>--%>
      <div class="layui-form-item">
        <div class="layui-inline">
          <label class="layui-form-label">验证码:</label>
          <div class="layui-input-block">
            <input value="${form.verifyCode}" type="text" id = "verifyCode" name="verifyCode" required  lay-verify="required" placeholder="请输入图片中的验证码" autocomplete="off" class="layui-input">
<%--            <label id="verifyCodeError" class="errorClass">${errors.verifyCode }</label>--%>
          </div>
        </div>
        <label id="verifyCodeError" class="errorClass">${errors.verifyCode }</label>
      </div>
<%--      <tr>--%>
<%--        <td class="tdText">验证码：</td>--%>
<%--        <td>--%>
<%--          <input class="inputClass" type="text" name="verifyCode" id="verifyCode" value="${form.verifyCode }"/>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--          <label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label>--%>
<%--        </td>--%>
<%--      </tr>--%>
      <div class="layui-form-item" style="padding-left: 112px">
        <div class="layui-inline">
          <div id="divVerifyCode" style="width: 100px"><img style="width: 100px;height: 40px" id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/></div>
          <label><a href="javascript:_hyz()">看不清，换一张
              <i class="layui-icon layui-icon-refresh-3" style="font-size: 14px; color: black;"></i>
          </a></label>
        </div>
      </div>
  <div class="layui-row" style="margin-left: 15px">
    <div class="layui-col-xs9">
        <a href="<c:url value='/jsps/user/login.jsp'/> " class="layui-btn layui-btn-danger">
            <i class="layui-icon layui-icon-prev" style="font-size: 16px; color: white;"></i>
            返回登陆
        </a>
<%--      <input type="submit" class="layui-btn" value="注册账号">--%>
    </div>
    <div class="layui-col-xs">
        <input type="submit" class="layui-btn" value="注册账号">
<%--      <a href="<c:url value='/jsps/user/login.jsp'/> " class="layui-btn layui-btn-danger">返回登陆</a>--%>
    </div>
    </div>
  </div>
<%--      <tr>--%>
<%--        <td></td>--%>
<%--        <td>--%>
<%--          <div id="divVerifyCode"><img id="imgVerifyCode" src="<c:url value='/VerifyCodeServlet'/>"/></div>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--          <label><a href="javascript:_hyz()">换一张</a></label>--%>
<%--        </td>--%>
<%--      </tr>--%>
<%--      <tr>--%>
<%--        <td></td>--%>
<%--        <td>--%>
<%--          <input type="image" src="<c:url value='/images/regist1.jpg'/>" id="submitBtn"/>--%>
<%--        </td>--%>
<%--        <td>--%>
<%--          <label></label>--%>
<%--        </td>--%>
<%--      </tr>--%>
</form>
  </div>
</div>
  </body>
</html>
