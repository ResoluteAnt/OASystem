<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!-- 
	提交到: /WorkFlow/deployProc
	提交三项数据: zipFile, note, category
 -->
<form action="${ctxPath}/WorkFlow/deployProc" 
	method="post" 
	enctype="multipart/form-data" >
	<label>流程压缩文件</label>
	<input type="file" name="zipFile" /> <br/>
	<label>布署名称</label>
	<input type="text" name="note" /> <br/>
	<label>选择流程类型</label>
	<select name="category"> 
		<option value="0">请选择流程</option>
		<option value="请假流程">请假流程</option>
		<option value="报销流程">报销流程</option>
		<option value="加班流程">加班流程</option>
		<option value="出差流程">出差流程</option>
	</select> <br/><br/>
	<input type="submit" name="submit" value="文件上传" />
	<br/>
</form>
