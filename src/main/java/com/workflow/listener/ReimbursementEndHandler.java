package com.workflow.listener;

import com.gec.service.LeaveService;
import com.gec.service.ReimburseService;
import com.gec.utils.MyWebUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @Author : JingJie
 * @Date : 2021/12/8 14:15
 * @Description :       [ok]
 * @Version : 1.0
 */
public class ReimbursementEndHandler
    implements ExecutionListener {
    private ReimburseService reimburseService;
    public ReimbursementEndHandler(){
        //{ps}将service的bean装配上来
        reimburseService = MyWebUtils.getBean(ReimburseService.class);
    }
    @Override
    public void notify(DelegateExecution exec)
            throws Exception {
        System.out.println("+-----------{ExecutionListener}:到达-----------+");
        //{1}获取流程变量
        String isCancel = (String)exec.getVariable("isCancel");
        System.out.println(isCancel);
        String _status = "已办结"; //默认值
        //{2}根据 cancel 变量设置状态
        if ("true".equals(isCancel)){
            _status = "已取消";
        }
        //{3}获取到流程实例ID
        String insID = exec.getProcessInstanceId();

        //{4}更新流程的状态
        reimburseService.updateStatusByInstance(
                insID, _status );
        //{5}更新办理人与任务ID为---> null
        reimburseService.updateAssigneeAndTask(
                insID, null, null);
        System.out.println("+-----------{ExecutionListener}:运行完成-----------+");
    }
}
