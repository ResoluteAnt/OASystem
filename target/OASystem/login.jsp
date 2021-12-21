<%@ page language="java" contentType="text/html; charset=UTF-8"
		 import="java.util.*"
		 import="org.apache.shiro.*"
		 import="org.apache.shiro.subject.*"
		 pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<%
	Map<String,String> _msg = new HashMap();
	_msg.put("1", "此用户不存在");
	_msg.put("2", "密码错误");
	_msg.put("3", "当前用户已锁定");
	_msg.put("4", "你没有登录，请先登录");
	//{ps} 设置到请求域 ...
	request.setAttribute( "msg", _msg );
%>
<%
	Subject subject = SecurityUtils.getSubject();
	pageContext.setAttribute("user",subject.getPrincipal());
%>
<html>
<head>
	<title>自动化办公系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${ctxPath}/static/css/style.css">
</head>

<body>
<h1>自动化办公系统</h1>
<div class="container w3">
	<h2>Login Now</h2>
	<form action="${ctxPath}/User/login" method="post">
		<div class="username">
			<span class="username">用户名:</span>
			<input type="text" name="username" class="name" placeholder="" required="">
			<div class="clear"></div>
		</div>
		<div class="password-agileits">
			<span class="username">密码:</span>
			<input type="password" name="password" class="password" placeholder="" required="">
			<div class="clear"></div>
		</div>
		<div class="rem-for-agile">
			<input type="checkbox" name="remember" class="remember">记住密码<br>
		</div>
		<div class="login-w3">
			<input type="submit" class="login" value="Login">
		</div>
		<div class="clear"></div>
	</form>
</div>
<div class="footer-w3l">
	<p> &copy; 2021 | The creator is ChiJingJie </p>
</div>
</body>
</html>


<script>
    <c:if test="${not empty param['errcode']}">
    alert( "${msg[ param['errcode'] ]}" );
    //layer.msg( "${msg[ param['errcode'] ]}" );
    </c:if>
</script>



