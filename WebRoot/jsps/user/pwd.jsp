<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改密码</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/> "media="all">
	  <link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>">
	  <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/pwd.css'/>">
	  <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	  <script type="text/javascript" src="<c:url value='/jsps/js/user/pwd.js'/>"></script>
	  <script src="/layui/src/layui.js"></script>
	  <script src="<c:url value='/js/common.js'/>"></script>
  </head>
  
  <body>
  <div class="layui-container" style="border: black solid thin;width: 600px;">
    <div style="padding: 30px">
    	<span style="color:#696969;font-size: 20px;font-weight: 900">修改密码</span>
    </div>
		<form action="<c:url value='/UserServlet'/>" method="post" target="_top">
			<input type="hidden" name="method" value="updatePassword"/>
			<tr>
				<td><label class="error">${msg }</label></td>
				<td colspan="2">&nbsp;</td>
			</tr>
			<div class="layui-form">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">原密码：</label>
						<div class="layui-input-block">
							<input value="${user.loginpass }" type="password" id = "loginpass" name="loginpass" required  lay-verify="required" placeholder="请输入原始密码" autocomplete="off" class="layui-input">
						</div>
					</div>
					<label id="loginpassError" class="error"></label>
				</div>
<%--			<tr>--%>
<%--				<td align="right">原密码:</td>--%>
<%--				<td><input class="input" type="password" name="loginpass" id="loginpass" value="${user.loginpass }"/></td>--%>
<%--				<td><label id="loginpassError" class="error"></label></td>--%>
<%--			</tr>--%>
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">新密码：</label>
							<div class="layui-input-block">
								<input value="${user.newpass }" type="password" id = "newpass" name="newpass" required  lay-verify="required" placeholder="请输入新的密码" autocomplete="off" class="layui-input">
							</div>
						</div>
						<label id="newpassError" class="error"></label>
					</div>
<%--			<tr>--%>
<%--				<td align="right">新密码:</td>--%>
<%--				<td><input class="input" type="password" name="newpass" id="newpass" value="${user.newpass }"/></td>--%>
<%--				<td><label id="newpassError" class="error"></label></td>--%>
<%--			</tr>--%>
						<div class="layui-form-item">
							<div class="layui-inline">
								<label class="layui-form-label">确认密码：</label>
								<div class="layui-input-block">
									<input value="${user.reloginpass }" type="password" id = "reloginpass" name="reloginpass" required  lay-verify="required" placeholder="请再次输入新的密码" autocomplete="off" class="layui-input">
								</div>
							</div>
							<label id="reloginpassError" class="error"></label>
						</div>
<%--			<tr>--%>
<%--				<td align="right">确认密码:</td>--%>
<%--				<td><input class="input" type="password" name="reloginpass" id="reloginpass" value="${user.reloginpass }"/></td>--%>
<%--				<td><label id="reloginpassError" class="error"></label></td>--%>
<%--			</tr>--%>
							<div class="layui-form-item">
								<div class="layui-inline">
									<label class="layui-form-label">验证码：</label>
									<div class="layui-input-block">
										<input type="text" id = "verifyCode" name="verifyCode" required  lay-verify="required" placeholder="请输入验证码" autocomplete="on" class="layui-input">
									</div>
								</div>
								<img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" style="border: black thin solid"/>
								<a href="javascript:_change();">看不清，换一张</a>
<%--								<label id="verifyCodeError" class="error"></label>--%>
								<div><label id="verifyCodeError" class="error" style="padding-left: 110px"></label></div>
							</div>

<%--							<div class="layui-form" style="padding-left: 50px">--%>
<%--								<div class="layui-form-item">--%>
<%--									<div class="layui-inline">--%>
<%--										<img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/>--%>
<%--										<a href="javascript:_change();">看不清，换一张</a>--%>
<%--									</div>--%>
<%--								</div>--%>


<%--			<tr>--%>
<%--				<td align="right"></td>--%>
<%--				<td>--%>
<%--				  <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="1"/>--%>
<%--		    	  <a href="javascript:_change();">看不清，换一张</a>--%>
<%--				</td>--%>
<%--			</tr>--%>

<%--			<tr>--%>
<%--				<td align="right">验证码:</td>--%>
<%--				<td>--%>
<%--				  <input class="input" type="text" name="verifyCode" id="verifyCode" value="${user.verifyCode }"/>--%>
<%--				</td>--%>
<%--				<td><label id="verifyCodeError" class="error"></label></td>--%>
<%--			</tr>--%>
								<div class="layui-form-item" style="padding-left: 430px">
									<input id="submit" type="submit" value="确认修改" class="layui-btn layui-btn-danger"/>
								</div>
			</div>
<%--			<tr>--%>
<%--				<td align="right"></td>--%>
<%--				<td><input id="submit" type="submit" value="修改密码"/></td>--%>
<%--			</tr>--%>
		</form>
  	</div>
  </body>
</html>
