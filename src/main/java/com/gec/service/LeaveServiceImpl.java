package com.gec.service;

import com.gec.dao.DeptMapper;
import com.gec.dao.LeaveMapper;
import com.gec.dao.ProcessConfigMapper;
import com.gec.domain.Leave;
import com.gec.domain.PageBean;
import com.gec.domain.ProcessConfig;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/7 16:54
 * @Description : com.gec.service
 * @Version : 1.0
 */
@Service
public class LeaveServiceImpl
        extends BaseService implements LeaveService{
    //{1}自动装配(两个Mapper)
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private ProcessConfigMapper processConfigMapper;

    //{2}自动装配(工作流相关的Service)
    @Autowired
    private RepositoryService repoService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService rtService;

    @Override
    public String getDeptLeader(String deptId) {
        return deptMapper.findDeptLeaderId(deptId);
    }

    @Override
    public String getHigherDeptLeader(String deptId) {
        //{1}获取上级部门的id
        String higherDeptId = deptMapper.findHigherLevelDept(
                                        deptId);
        System.out.println(higherDeptId);
        //{2}根据此id，得到这个部门的领导.
        return deptMapper.findDeptLeaderId(higherDeptId);
    }

    @Override
    public void updateAssigneeAndTask(String insId, String taskId, String assignee) {
        leaveMapper.updateAssigneeAndTask(
                insId, taskId, assignee);
        //{ps}更新失败这里暂时不处理
    }

    //+------------------[A.发起申请的业务]---------------------+
    //{a.1}包装流程变量..
    private Map<String,Object> setVariables( Leave leave ){
        Map<String,Object> vars = new HashMap();
        vars.put("inputUser", leave.getInitiator()); //发起人
        vars.put("startDate", dateToStr(leave.getStartDate())); //开始日期
        vars.put("endDate", dateToStr(leave.getEndDate())); //结束日期
        vars.put("reason", leave.getReason()); //请假原因
        vars.put("days", leave.getDays());    //请假天数
        vars.put("leaveType", leave.getLeaveType()); //请假类型
        vars.put("cancel", "false");  //取消任务
        return vars;
    }
    //{a.2}提交申请
    private void commitLeave(
            String initiator,String insID){
        //{1}查询的得到任务ID
        Task task = taskService.createTaskQuery()
                .taskAssignee(initiator)
                .processInstanceId(insID)
                .singleResult();
        //{2}提交任务
        taskService.complete(task.getId());
    }
    //{a.3}开启流程实例
    @Override
    public void startProccess(Leave leave) {
        //{1}计算天数
        int days = calDays(leave.getStartDate(),
                leave.getEndDate());
        leave.setDays(days);//设置天数
        //{2}包装流程变量
        Map<String, Object> procVars = setVariables(leave);
        //{3}查询系统的流程字典表 - 获得流程定义ID
        ProcessConfig config = processConfigMapper
                .getProcConfigByCategory("请假流程");
        String procDefId = config.getProcDefId();
        //{4}开启流程实例[存入流程变量]
        ProcessInstance instance = rtService.startProcessInstanceById(
                                    procDefId, procVars);
        //{5}补全 leave 信息
        //如：流程定义ID，流程实例ID
        leave.setProcDefId( procDefId );
        leave.setProcInstId( instance.getId() );
        leave.setCreateTime( dateToStr(new Date()));
        leave.setStatus("办理中");
        //{6}保存到t_leave表
        leaveMapper.addLeave( leave );
        //{7}提交任务[发起人，流程实例ID]
        commitLeave(leave.getInitiator(),
                instance.getId());
    }


    //+------------------[B.查询我发起的业务(现在的)]--------------------+
    @Override
    public PageBean<Leave> queryMyInitiate(String initiator) {
        //{1}省略分页功能
        //{2}获取到列表
        List<Leave> list = leaveMapper.queryMyInitiate(initiator);
        PageBean pageBean = new PageBean();
        //{3}临时设置。。(到时要设置总记录数据)
        pageBean.setCount(list.size());
        pageBean.setList( list );
        //{4}返回到上层
        return pageBean;
    }


    //+------------------[C.查询待我审批的业务]------------------+
    @Override
    public PageBean<Leave> queryWaitMyApprove(String assigneeId, int page, int limit) {
        //{1}这里暂时不设置分页
        //{2}查询审批的任务
        List<Leave> list = null;
        PageBean pBean = new PageBean();
        list = leaveMapper.queryWaitMyApprove(assigneeId);
        //{3}设置分页信息
        pBean.setCount(list.size());//暂时设置list大小
        pBean.setList( list );
        return pBean;
    }

    @Override
    public Leave queryMyTaskByTaskId(String taskId) {
        return leaveMapper.queryMyTaskByTaskId(taskId);
    }

    /*+----------------------[D.查询我已审批的业务]---------------------+*/
    /*+----------------------[E:审批我的业务]--------------------------+*/

    /*
        前端有4个数据会过来
             {a}deptId:_deptId,
		     {b}appFlag:_flag,   true(同意)/false(不同意)
		     {c}refuseReason:_reason, 拒绝原因
		     {d}taskId:_taskId  任务ID
     */
    @Override
    public void completeMyApprove(Map data) {
        System.out.println(data);
        String deptId = (String) data.get("deptId");
        String taskId = (String) data.get("taskId");
        String appFlag = (String) data.get("appFlag");
        //{1}我是谁：老板/主管
        //{2}拿到父级部门id
        System.out.println(deptId);
        String pDeptId = deptMapper.findHigherLevelDept(deptId);
        if ("top".equals(pDeptId)){  //老板
            data.put("bossApp", appFlag);
        }else {     //我是主管
            data.put("deptApp", appFlag);
        }
        //{3}把办理人设置为空
        leaveMapper.updateAssigneeByTask(taskId, null);
        //{4}提交任务 把data提交上去了
        taskService.complete(taskId, data);
    }

    @Override
    public void updateStatusByInstance(String insId,
                                       String status) {
        int count = leaveMapper.updateStatus(insId, status);
        //----更新失败暂时不处理------

    }
}
