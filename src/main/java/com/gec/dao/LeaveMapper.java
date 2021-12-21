package com.gec.dao;

import com.gec.domain.Leave;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LeaveMapper {

	/*-------------------------[查询相关流程]-------------------------------*/
	List<Leave> queryWaitMyApprove(
            @Param("assigneeId") String assigneeId);

	//{2}查询我发起的任务..
	List<Leave> queryMyInitiate(
            @Param("initiator") String initiator);

	List<Leave> queryMyApproved(
            @Param("assignee") String assignee);

	Leave queryMyTaskByTaskId(
            @Param("taskId") String taskId);

	/*-------------------------[更新相关流程]-------------------------------*/
	int addLeave(Leave leave);

	int updateStatus(
            @Param("insId") String insId,
            @Param("status") String status);

	int updateAssigneeByTask(
            @Param("taskId") String taskId,
            @Param("assignee") String assignee);

//	int updateStatusByTask(
//            @Param("taskId") String taskId,
//            @Param("status") String status);

	int updateAssigneeAndTask(
            @Param("insId") String insId,
            @Param("taskId") String taskId,
            @Param("assignee") String assignee);



	List<String> findAllProcDefId(@Param("procDefId")String procDefId);
	void delById(@Param("id") String id);
}










