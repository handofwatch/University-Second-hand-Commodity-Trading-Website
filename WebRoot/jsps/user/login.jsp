<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
      <link rel="stylesheet" type="text/css" href="<c:url value='/layui/src/css/layui.css'/>" media="all">
      <link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/user/login.css'/>">
      <script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
      <script type="text/javascript" src="<c:url value='/jsps/js/user/login.js'/>"></script>
      <script src="<c:url value='/js/common.js'/>"></script>
      <script src="/layui/src/layui.js"></script>
      <script type="text/javascript">
	$(function() {/*Map<String(Cookie名称),Cookie(Cookie本身)>*/
		// 获取cookie中的用户名
		var loginname = window.decodeURI("${cookie.loginname.value}");
		if("${requestScope.user.loginname}") {
			loginname = "${requestScope.user.loginname}";
		}
		$("#loginname").val(loginname);
	});
</script>
  </head>
  
  <body     style="background-color: #fabd00;">
  <div style="position:absolute; width:1310px; height:888px; z-index:-1 ;top:0px;left: 500px">
      <img src="<c:url value='/images/huang_1_bgi.jpg'/>" height="100%" width="100%"/>
  </div>
  <div  class="layui-main">
<%--	  <div><img src="<c:url value='/images/huang_1_bgi.jpg'/>" /></div>--%>
<%--	    <div class="imageDiv"><img class="img" src="<c:url value='/images/zj.png'/>"/></div>--%>
<%--        <div class="login1">--%>
<%--	      <div class="login2">--%>
    <div class="layui-container" style="background: #F0F0F0; padding-bottom:20px; border:black solid thin;width: 550px;margin-left: 15%;margin-top: 15%" >
<%--        <div style="margin-top: 10%"></div>    --%>
        <div class="loginTopDiv">
              <span class="loginTop">用户登陆</span>
            </div>
              <form target="_top" action="<c:url value='/UserServlet'/>" method="post" id="loginForm">
                <input type="hidden" name="method" value="login" />
                  <div>
                      <div width="50"></div>
                      <div><label class="error" id="msg">${msg }</label></div>
                    </div>
<%--                    <tr>--%>
<%--                      <td width="50">用户名</td>--%>
<%--                      <td><input class="input" type="text" name="loginname" id="loginname"/></td>--%>
<%--                    </tr>--%>
                      <div class="layui-form-item">
                          <div class="layui-inline">
                            <label class="layui-form-label">用户名</label>
                            <div class="layui-input-block">
                                <input type="text" id = "loginname" name="loginname" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <label id="loginnameError" class="error"></label>
                      </div>
<%--                      <div>--%>
<%--                          <td height="20">&nbsp;</td>--%>
<%--                          <td><label id="loginnameError" class="error"></label></td>--%>
<%--                      </div>--%>

                      <div class="layui-form-item">
                          <div class="layui-inline">
                            <label class="layui-form-label">密 码</label>
                            <div class="layui-input-block">
                                <input type="password" id = "loginpass" name="loginpass" required  lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                            </div>
                          </div>
                          <label id="loginpassError" class="error"></label>
                      </div>
<%--                    <tr>--%>
<%--                      <td>密　码</td>--%>
<%--                      <td><input class="input" type="password" name="loginpass" id="loginpass" value="${user.loginpass }"/></td>--%>
<%--                    </tr>--%>
<%--                      <div>--%>
<%--                          <div height="20">&nbsp;</div>--%>
<%--                          <div><label id="loginpassError" class="error"></label></div>--%>
<%--                      </div>--%>

                      <div class="layui-form-item">
                          <div class="layui-inline">
                            <label class="layui-form-label">验证码</label>
                            <div class="layui-input-block">
                                <input type="text" name="verifyCode" id = "verifyCode" required  value="${user.verifyCode }" lay-verify="required" placeholder="请输入验证码" class="layui-input">
                            </div>

<%--                              <div>--%>
<%--                                  <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>"/>--%>
<%--                                  <a href="javascript:_change()" class="layui-word-aux">换张图</a>--%>
<%--                              </div>--%>
                          </div>
                          <label id="verifyCodeError" class="error"></label>
<%--                          <div>验证码</div>--%>
<%--                          <div>--%>
<%--                              <input class="input yzm" type="text" name="verifyCode" value="${user.verifyCode }"/>--%>
<%--                              <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>"/>--%>
<%--                              <a href="javascript:_change()" class="layui-form-mid layui-word-aux">换张图</a>--%>
<%--                          </div>--%>
                      </div>
                  <div class="layui-form-item">
                      <div class="layui-inline">
                    <div style="margin-top: 5px;margin-left: 110px">
                      <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" style="height: 40px;width: 100px"/>
                      <a href="javascript:_change()">看不清，换一张
                          <i class="layui-icon layui-icon-refresh-3" style="font-size: 14px; color: black;"></i>
                      </a>
                    </div>
                      </div>
                  </div>

                      <div class="layui-row">
                          <div class="layui-col-xs9" style="margin-left: 15px">
                              <a href="<c:url value='/jsps/user/regist.jsp'/>" class="layui-btn layui-btn-danger">
                                  立即注册
                                  <i class="layui-icon layui-icon-next" style="font-size: 16px; color: white;"></i>
                              </a>

<%--                              <input type="submit" class="layui-btn" value = "登 陆">--%>
                          </div>
<%--                          <input type="image" id="submit" src="<c:url value='/images/login1.jpg'/>" class="loginBtn"/>--%>
                          <div class="layui-col-xs">
                              <input type="submit" class="layui-btn" value = "登 陆">

<%--                              <a href="<c:url value='/jsps/user/regist.jsp'/>" class="layui-btn layui-btn-danger">立即注册</a>--%>
                          </div>
                      </div>
                  </div>
            </div>
  </body>
</html>
	