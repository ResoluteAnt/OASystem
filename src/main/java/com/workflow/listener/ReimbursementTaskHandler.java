package com.workflow.listener;

import com.gec.domain.Role;
import com.gec.domain.User;
import com.gec.service.LeaveService;
import com.gec.service.ReimburseService;
import com.gec.utils.MyWebUtils;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.Map;
import java.util.Set;

/**
 * @Author : JingJie
 * @Date : 2021/12/7 16:08
 * @Description : com.workflow.listener  [ok]
 * @Version : 1.0
 */
public class ReimbursementTaskHandler
        implements TaskListener {
    //{ps}这个侦听器是由Activiti自动创建
    //   也就是spring在这里无法注入对象
    //解决办法：从IOC容器中获取该对象
    //{1}装配一个service类
    private ReimburseService reimburseService;
    public ReimbursementTaskHandler(){
        //{2}我可以通过spring的上下文对象获取Bean对象
        reimburseService = MyWebUtils.getBean(
                ReimburseService.class );
    }
    @Override
    public void notify(DelegateTask task) {
        //{1}取出当前登录系统的操作用户
        //   可能的人: andy{职员}, candy{主管}
        //   从会话域中取出用户
        User curUser = MyWebUtils.getCurrentUser();

        //{2.1}获取当前用户的角色(雇员 or 经理)
        //{2.2}一个员工有多个角色, 这里暂时当他有一个
        //   以后真有多个，修改以下代码。
        Set<Role> roles = curUser.getRoles();
        Role role = roles.iterator().next();
        //{3}获取任务 ID, 流程实例 ID
        String taskID = task.getId();
        String procInsID = task.getProcessInstanceId();
        //{4}获得部门ID
        String deptId = curUser.getDeptId();
        String leaderId = null;
        //{1}取出流程变量 flag null 1 2
        String name = task.getName();
        if (name.equals("部门主管审批")){
            //{1.1}部门主管审批
            //{1}去寻找本部门的总经理审批
            leaderId = reimburseService.getManager(deptId);
            printMark("部门主管："+leaderId);
        }else if (name.equals("账务部主管审批")){
            //{1.2}寻找本部门的财务主管审批
            leaderId = reimburseService.getFinanceleader();
            printMark("财务主管："+leaderId);
        }else {     //职员
            leaderId = reimburseService.getDeptLeader(deptId);
            printMark("部门主管："+leaderId);
        }
        //{6.1}设置新建节点的办理人
        task.setAssignee( leaderId );
        //{6.2}更新t_leave 表(办理人+任务ID)
        reimburseService.updateAssigneeAndTask(
                procInsID, taskID, leaderId);
    }
    private void printMark(Object obj) {
        System.out.println("+--------------------------------------+");
        System.out.println( obj );
        System.out.println("+--------------------------------------+");
    }
}
