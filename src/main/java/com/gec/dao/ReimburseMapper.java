package com.gec.dao;

import com.gec.domain.Reimburse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : JingJie
 * @Date : 2021/12/16 14:53
 * @Description : com.gec.dao
 * @Version : 1.0
 */
public interface ReimburseMapper {
    /*-------------------------[查询相关流程]-------------------------------*/
    //{1}查询待我审批的任务有哪些
    List<Reimburse> queryWaitMyApprove(
            @Param("assigneeId") String assigneeId);

    //{2}查询我发起的任务的任务有哪些
    List<Reimburse> queryMyInitiate(
            @Param("initiator") String initiator);
    //{3}查看我发起的任务       待补充
    List<Reimburse> queryMyApproved(
            @Param("assignee") String assignee);
    //{4}通过任务id查询待我审批的一个任务详情
    Reimburse queryMyTaskByTaskId(
            @Param("taskId") String taskId);

    /*-------------------------[更新相关流程]-------------------------------*/
    //{1}添加一个报销的任务
    int addReimburse(Reimburse Reimburse);

    //{2}更新状态通过实例id
    int updateStatus(
            @Param("insId") String insId,
            @Param("status") String status);

    //{3}更新办理人ID通过任务id
    int updateAssigneeByTask(
            @Param("taskId") String taskId,
            @Param("assignee") String assignee);
    //{4}更新办理人和任务ID
    int updateAssigneeAndTask(
            @Param("insId") String insId,
            @Param("taskId") String taskId,
            @Param("assignee") String assignee);

    List<String> findAllProcDefId(@Param("procDefId") String procDefId);
    void delById(@Param("id") String id);
}
