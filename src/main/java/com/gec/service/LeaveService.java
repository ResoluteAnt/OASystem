package com.gec.service;

import com.gec.domain.Leave;
import com.gec.domain.PageBean;

import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/7 16:44
 * @Description : com.gec.service
 * @Version : 1.0
 */
public interface LeaveService {
    //{1}获取部门领导
    String getDeptLeader(
            String deptId);
    //{2}获取上一级部门领导
    String getHigherDeptLeader(
        String deptId
    );
    //{3}更新办理人与任务ID
    void updateAssigneeAndTask(
       String insId,String taskId,
       String assignee
    );
    //{4}开启流程与提交申请
    void startProccess(Leave leave);

    //{5}查看我发起的任务
    PageBean<Leave> queryMyInitiate(
            String initiator
    );

    //{6}查看我待审批的任务
    PageBean<Leave> queryWaitMyApprove(
            String assigneeId,int page,int limit
    );
    //{7}查询待我审批的一个任务详情
    Leave queryMyTaskByTaskId(String taskId);
    //{8}提交我的审批
    void completeMyApprove(Map data);
    //{9}根据流程实例Id更新状态
    void updateStatusByInstance(String insId,String status);
}
