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
			url:'${ctxPath}/Reimburse/submitReimburse',
			type:'post',
			data:{
				initiator:$('#initiator').val(),
                money:$('#money').val(),
                type:$('#type').val(),
				reason:$('#reason').val()
			},
			dataType:"json",
			success:function( json ){
			    var ret = json['result'];
			    var cause = json['cause'];
			    if (ret='success'){
			        alert('申请成功');
			        //{ps}查看我发起的任务
					window.location = '${ctxPath}/Reimburse/myInitiate';
				} else {
			        alert('申请失败，原因：'+cause);
				}
			}
		}		
	);
}
</script>

<form id="frmLeave">	
	<!--  发起人ID:initiator -->
	<input id="initiator" type="hidden" value="${user.id}"/><br/>
	
	<label>报销金额:</label>
	<input id="money" type="text" /><br/>
	
	<label>报销类型:</label>
	<select id="type">
		<option value="招待费">招待费</option>
		<option value="办公费">办公费</option>
		<option value="差旅费">差旅费</option>
		<option value="维修费">维修费</option>
		<option value="咨询费">咨询费</option>
		<option value="审计费">审计费</option>
	</select><br/>
	
	<label>报销原因:</label>
	<textarea id="reason" cols="35" rows="5">
	</textarea><br/>
	
	<input type="button" value="提交审批"
		onclick="submitLeave();"/><br/><br/>
	
</form>