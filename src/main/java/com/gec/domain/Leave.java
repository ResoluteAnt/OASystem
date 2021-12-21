package com.gec.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Leave {
	private String procKey;
	private String procDefId;

	private String procInstId;
	private String taskId;       //{ps} 非表中字段
	private String taskName;     //{ps} 非表中字段

	private String initiator;      //{ps} 发起人ID
	private String initiatorName;  //{ps} 发起人名字

	@JSONField(format="yyyy-MM-dd")
	private Date startDate;

	@JSONField(format="yyyy-MM-dd")
	private Date endDate;

	private String reason;
	private Integer days;
	private String leaveType;

	private String result;

	private String assignee;       //{ps} 办理人ID
	private String assigneeName;   //{ps} 办理人名称
	private String apprTime;       //{ps} 我的办理时间

	private String createTime;  //{ps} 流程创建时间
	private String finishTime;  //{ps} 流程完成时间
	private String status;      //{ps} 流程状态

	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProcKey() {
		return procKey;
	}
	public void setProcKey(String procKey) {
		this.procKey = procKey;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getInitiatorName() {
		return initiatorName;
	}
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getApprTime() {
		return apprTime;
	}
	public void setApprTime(String apprTime) {
		this.apprTime = apprTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}


	@Override
	public String toString() {
		return "Leave{" +
				"procKey='" + procKey + '\'' +
				", procDefId='" + procDefId + '\'' +
				", procInstId='" + procInstId + '\'' +
				", taskId='" + taskId + '\'' +
				", taskName='" + taskName + '\'' +
				", initiator='" + initiator + '\'' +
				", initiatorName='" + initiatorName + '\'' +
				", startDate=" + startDate +
				", endDate=" + endDate +
				", reason='" + reason + '\'' +
				", days=" + days +
				", leaveType='" + leaveType + '\'' +
				", result='" + result + '\'' +
				", assignee='" + assignee + '\'' +
				", assigneeName='" + assigneeName + '\'' +
				", apprTime='" + apprTime + '\'' +
				", createTime='" + createTime + '\'' +
				", finishTime='" + finishTime + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
