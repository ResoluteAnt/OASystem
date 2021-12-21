package com.gec.service;

import com.gec.domain.Leave;
import com.gec.domain.PageBean;
import com.gec.domain.Reimburse;

import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/16 14:50
 * @Description : com.gec.service
 * @Version : 1.0
 */
public interface ReimburseService {
    //{1}获取部门主管
    String getDeptLeader(
            String deptId);
    //{2}获取部门经理
    String  getManager(String deptId);

    //{3}获取财务部门主管
    String getFinanceleader();
    //{4}更新办理人与任务ID
    void updateAssigneeAndTask(
            String insId,String taskId,
            String assignee
    );
    //{5}开启流程与提交申请
    void startProccess(Reimburse reimburse);

    //{6}查看我发起的任务
    PageBean<Reimburse> queryMyInitiate(
            String initiator
    );

    //{7}查看我待审批的任务
    PageBean<Reimburse> queryWaitMyApprove(
            String assigneeId,int page,int limit
    );
    //{8}查询待我审批的一个任务详情
    Reimburse queryMyTaskByTaskId(String taskId);

    //{9}提交我的审批
    void completeMyApprove(Map data);

    //{10}根据流程实例Id更新状态
    void updateStatusByInstance(String insId,String status);


}
