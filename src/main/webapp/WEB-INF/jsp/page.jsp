<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*"
    import="org.apache.shiro.*" 
    import="org.apache.shiro.subject.*"
    pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<style>
body{
	background:#F5F5F5;
}
</style>
<h1>欢迎使用 OA 系统</h1>
<%
Subject subject = SecurityUtils.getSubject();
pageContext.setAttribute("user",subject.getPrincipal());
%>
<html lang="en">
<h1>当前用户: ${ user }</h1>
<h1>当前页: ${ page }</h1>

