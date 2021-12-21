package com.workflow.listener;

import com.gec.service.LeaveService;
import com.gec.utils.MyWebUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;

import java.util.Set;

/**
 * @Author : JingJie
 * @Date : 2021/12/8 14:15
 * @Description :
 * @Version : 1.0
 */
public class LeaveEndHandler
    implements ExecutionListener {
    private LeaveService leaveService;
    public LeaveEndHandler(){
        //{ps}将service的bean装配上来
        leaveService = MyWebUtils.getBean(LeaveService.class);
    }
    @Override
    public void notify(DelegateExecution exec)
            throws Exception {
        //{1}获取流程变量
        String isCancel = (String)exec.getVariable("cancel");
        String _status = "已办结"; //默认值
        //{2}根据 cancel 变量设置状态
        if ("true".equals(isCancel)){
            _status = "已取消";
        }
        //{3}获取到流程实例ID
        String insID = exec.getProcessInstanceId();

        //{4}更新流程的状态
        leaveService.updateStatusByInstance(
                insID, _status );
        //{5}更新办理人与任务ID为---> null
        leaveService.updateAssigneeAndTask(
                insID, null, null);
    }
}
