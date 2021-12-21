package com.gec.utils;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/2 16:00
 * @Description : 流程工具类
 * @Version : 1.0
 */
public class ProcessUtil {
    static ProcessEngine engine;
    static {
        engine = ProcessEngines.getDefaultProcessEngine();
    }
    public static ProcessEngine getEngine(){
        return engine;
    }
    private static void prtMark(Object msg){
        System.out.println("+--------------------------------------------+");
        System.out.println("{DEBUG}"+msg);
        System.out.println("+--------------------------------------------+");
    }

    //{2}删除流程的方法
    public static void deleteProcessByDeployID(
            String deployID , boolean isCascade ){
        ProcessEngine E = getEngine();
        RepositoryService respSV = E.getRepositoryService();
        respSV.deleteDeployment(deployID, isCascade);
        prtMark("删除流程成功："+deployID);
    }


}
