package com.gec.service;

import com.gec.dao.DeptMapper;
import com.gec.dao.LeaveMapper;
import com.gec.dao.ProcessConfigMapper;
import com.gec.dao.ReimburseMapper;
import com.gec.domain.Leave;
import com.gec.domain.PageBean;
import com.gec.domain.ProcessConfig;
import com.gec.domain.Reimburse;
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
 * @Date : 2021/12/16 14:50
 * @Description : 报销流程的业务层
 * @Version : 1.0
 */
@Service
public class ReimburseServiceImpl extends BaseService
        implements ReimburseService {
    //{1}自动装配(两个Mapper)
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private ReimburseMapper reimburseMapper;
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


    /*+------------------------------[获取领导部分]-------------------------------------+*/
    @Override
    public String getDeptLeader(String deptId) {
        System.out.println("+-------------------[开始获得部门领导]----------------------+");
        String deptLeaderId = deptMapper.findDeptLeaderId(deptId);
        System.out.println(deptLeaderId);
        System.out.println("+-------------------[结束获得部门领导]----------------------+");
        return deptLeaderId;
    }

    @Override
    public String getManager(String deptId) {   //[OK]
        System.out.println("+-------------------[开始获取部门经理]----------------------+");
        //{1}获取上级部门的id
        String deptManager = deptMapper.findDeptManager(deptId);
        System.out.println(deptManager);
        System.out.println("+-------------------[结束获取部门经理]----------------------+");
        //{2}根据此id，得到这个部门的领导.
        return deptManager;
    }

    @Override
    public String getFinanceleader() {          //[OK]
        System.out.println("+-------------------[开始获取财务部主管]----------------------+");
        String deptFinance = deptMapper.findDeptLeaderId("d02");
        System.out.println(deptFinance);
        System.out.println("+-------------------[结束获取财务部主管]----------------------+");
        return deptFinance;
    }
    /*+------------------------------[包装流程变量]-------------------------------+*/
    //{a.1}包装流程变量                             //[OK]
    private Map<String,Object> setVariables( Reimburse reimburse ){
        Map<String,Object> vars = new HashMap();
        vars.put("inputUser", reimburse.getInitiator()); //发起人
        vars.put("reason", reimburse.getReason());  //原因
        vars.put("money", reimburse.getMoney());    //金额
        vars.put("type", reimburse.getType());      //报销类型
        vars.put("isCancel", "false");                //取消任务
        return vars;
    }
    //{a.2}提交申请                                //[OK]
    private void commitReimburse(
            String initiator,String insID){
        //{1}查询的得到任务ID
        Task task = taskService.createTaskQuery()
                .taskAssignee(initiator)
                .processInstanceId(insID)
                .singleResult();
        //{2}提交任务
        taskService.complete(task.getId());
    }
    /*+------------------------------[开启与提交流程部分]-------------------------------+*/
    @Override
    public void startProccess(Reimburse reimburse) {
        //{1}包装流程变量
        Map<String, Object> procVars = setVariables(reimburse);
        //{2}查询系统的流程字典表 - 获得流程定义ID
        ProcessConfig config = processConfigMapper
                .getProcConfigByCategory("报销流程");
        String procDefId = config.getProcDefId();
        //{3}开启流程实例[存入流程变量]
        ProcessInstance instance = rtService.startProcessInstanceById(
                procDefId, procVars);
        //{4}补全 leave 信息
        //如：流程定义ID，流程实例ID
        reimburse.setProcDefId( procDefId );
        reimburse.setProcInstId( instance.getId() );
        reimburse.setCreateTime( dateToStr(new Date()));
        reimburse.setStatus("办理中");
        //{6}保存到t_leave表
        reimburseMapper.addReimburse(reimburse);
        //{7}提交任务[发起人，流程实例ID]
        commitReimburse(reimburse.getInitiator(),
                instance.getId());
    }
    @Override
    public void completeMyApprove(Map data) {
        //{1}提交任务
        String deptId = (String) data.get("deptId");
        String taskId = (String) data.get("taskId");
        String appFlag = (String) data.get("appFlag");
        //{1}我是谁：主管/经理
        //{2}拿到父级部门id
        //主管
        String dId = deptMapper.findDeptLeaderId(deptId);
        //经理
        String deptManager = deptMapper.findDeptManager(deptId);
        //财务部主管
        String deptFinance = deptMapper.findDeptLeaderId("d02");
        if (dId != null){  //主管
            data.put("isApp", appFlag);
        }else if (deptManager != null){     //我是主管
            data.put("isApp", appFlag);
        }else if (deptFinance != null){
            data.put("isApp", appFlag);
        }
        //{3}把办理人设置为空
        reimburseMapper.updateAssigneeByTask(taskId, null);
        //{4}提交任务 把data提交上去了
        taskService.complete(taskId, data);
    }
    /*+------------------------------[更新流程部分]------------------------------------+*/
    @Override
    public void updateAssigneeAndTask
            (String insId, String taskId, String assignee) {
        reimburseMapper.updateAssigneeAndTask(insId, taskId, assignee);
        //{ps}更新失败这里暂时不处理
    }
    @Override
    public void updateStatusByInstance(String insId, String status) {
        int count = reimburseMapper.updateStatus(insId, status);
        //----更新失败暂时不处理------
    }
    /*+------------------------------[查询流程部分]-------------------------------------+*/
    @Override
    public PageBean<Reimburse> queryMyInitiate(String initiator) {
        //{1}省略分页功能
        //{2}获取到列表
        List<Reimburse> list = reimburseMapper.queryMyInitiate(initiator);
        PageBean pageBean = new PageBean();
        //{3}临时设置。。(到时要设置总记录数据)
        pageBean.setCount(list.size());
        pageBean.setList( list );
        //{4}返回到上层
        return pageBean;
    }

    @Override
    public PageBean<Reimburse> queryWaitMyApprove
            (String assigneeId, int page, int limit) {
        //{1}这里暂时不设置分页
        //{2}查询审批的任务
        List<Reimburse> list = null;
        PageBean pBean = new PageBean();
        list = reimburseMapper.queryWaitMyApprove(assigneeId);
        //{3}设置分页信息
        pBean.setCount(list.size());//暂时设置list大小
        pBean.setList( list );
        return pBean;
    }

    @Override
    public Reimburse queryMyTaskByTaskId(String taskId) {
        return reimburseMapper.queryMyTaskByTaskId(taskId);
    }
    /*+-----------------------------------------------------------------------------+*/
}
