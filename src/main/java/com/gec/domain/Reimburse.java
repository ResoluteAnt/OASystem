package com.gec.domain;

/**
 * @Author : JingJie
 * @Date : 2021/12/16 15:43
 * @Description : com.gec.domain
 * @Version : 1.0
 */
public class Reimburse {
    /*+------------[与数据库对应的字段]----------------+*/
    private String procInstId;
    private String procDefId;
    private String taskId;
    private String reason;			//{ps} 报销的原因
    private Double money;			//{ps} 报销的金额
    private String type; 			//{ps} 报销项目
    private String createTime;		//{ps} 流程创建时间
    private String finishTime;		//{ps} 流程完成时间
    private String initiator;		//{ps} 发起人ID
    private String assignee;		//{ps} 办理人ID
    private String status;  		//{ps} 流程状态
    /*+----------[一些查询所需要的字段字段]-------------+*/
    private String initiatorName;   //{ps} 发起人名字
    private String assigneeName;   	//{ps} 办理人名称
    private String apprTime;       	//{ps} 我的办理时间

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
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

    @Override
    public String toString() {
        return "Reimburse{" +
                "procInstId='" + procInstId + '\'' +
                ", procDefId='" + procDefId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", reason='" + reason + '\'' +
                ", money=" + money +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", initiator='" + initiator + '\'' +
                ", assignee='" + assignee + '\'' +
                ", status='" + status + '\'' +
                ", initiatorName='" + initiatorName + '\'' +
                ", assigneeName='" + assigneeName + '\'' +
                ", apprTime='" + apprTime + '\'' +
                '}';
    }
}
