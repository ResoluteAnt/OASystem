<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>

<script src="${ctxPath}/static/js/jquery-1.11.1.min.js"></script>


<form id="frmLeave">	
	<!--  {ps}发起人: initiator -->
	<input id="initiatorName" type="text" value="${reimburse.initiatorName}" readonly/><br/>

	<label>报销金额:</label>
	<input id="money" type="text" value="${reimburse.money}" readonly/><br/>
	
	<label>报销类型:</label>
	<input id="type" type="text" value="${reimburse.type}" readonly/>
	<br/>
	
	<label>报销原因:</label>
	<textarea id="reason" readonly>${reimburse.reason}</textarea><br/>
	
	<input type="button" value="同意申请" 
		onclick="apprLeave('true');"/>
		
	<input type="button" value="拒绝申请" 
		onclick="apprLeave('false');"/><br/><br/>
		
	<label>拒绝原因:</label> 
	<textarea id="refuseReason"></textarea><br/><br/>
	
	<label>任务 ID:</label> 
	<input id="taskId" type="text" value="${reimburse.taskId}" readonly/>
	<br/><br/>

</form>

<script>
function apprLeave( _flag ){
	var _taskId = $("#taskId").val();
	//{1} 当前用户的部门Id
	var _deptId = '${user.deptId}';
	//{2} 拒绝原因
	var _reason   = $("#refuseReason").val();
	//{3} 提交地址
	$.ajax(
		{
			url:'${ctxPath}/Reimburse/doApprove',
			type:'post',
			data:{
                deptId:_deptId,
				appFlag:_flag,
				refuseReason:_reason,
				taskId:_taskId
			},
			dataType:"json",
			success:function( json ){
				console.log( json );
				var ret = json['result'];
				if (ret=='success'){
				    alert('提交审批成功');
				    window.location = '${ctxPath}/Reimburse/waitMyApprove'
				} else {
				    var cause = json['cause'];
                    alert('提交审批失败，原因：'+ cause);
				}
			}
		}		
	);
}
</script>




