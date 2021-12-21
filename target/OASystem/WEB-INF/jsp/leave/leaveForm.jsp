<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<style>
body{ background:#E5E5E5; }
[type='text']{
	font-size:18px; background:#F3F3F3;
	margin-top:10px; border:1px solid #666;
	height:35px;
}
[type='button']{
	font-size:18px; margin-top:10px; 
	height:38px; width:100px;
}
[type='button']:hover{
	background:#D5D5D5; cursor:pointer;
	border:1px solid #666;
}
select {
	font-size:16px; margin-top:10px; 
	height:32px; width:180px;
	background:#E3E3E3;
}
textarea{
	background:#F3F3F3; margin-top:10px;
	font-size:17px;
}
</style>
<script src="${ctxPath}/static/js/jquery-1.11.1.min.js"></script>
<script>
function submitLeave(){
	$.ajax(
		{
			url:'${ctxPath}/Leave/submitLeave',
			type:'post',
			data:{
				initiator:$('#initiator').val(),
				startDate:$('#startDate').val(),
				endDate:$('#endDate').val(),
				leaveType:$('#leaveType').val(),
				reason:$('#reason').val()
			},
			dataType:"json",
			success:function( json ){
			    var ret = json['result'];
			    var cause = json['cause'];
			    if (ret='success'){
			        alert('申请请假成功');
			        //{ps}查看我发起的任务
					window.location = '${ctxPath}/Leave/myInitiate';
				} else {
			        alert('申请请假失败，原因：'+cause);
				}

			}
		}		
	);
}
</script>

<form id="frmLeave">	
	<!--  发起人ID:initiator -->
	<input id="initiator" type="hidden" value="${user.id}"/><br/>
	
	<label>开始时间:</label>
	<input id="startDate" type="text" /><br/>
	
	<label>结束时间:</label>
	<input id="endDate" type="text" /><br/>
	
	<label>请假类型:</label> 
	<select id="leaveType">
		<option value="事假">事假</option>
		<option value="年假">年假</option>
		<option value="病假">病假</option>
		<option value="婚假">婚假</option>
		<option value="产假">产假</option>
		<option value="陪产假">陪产假</option>
	</select><br/>
	
	<label>请假原因:</label> 
	<textarea id="reason" cols="35" rows="5">
	</textarea><br/>
	
	<input type="button" value="提交请假" 
		onclick="submitLeave();"/><br/><br/>
	
</form>