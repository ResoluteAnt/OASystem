package com.gec.service;

import com.gec.dao.LeaveMapper;
import com.gec.dao.ProcessConfigMapper;
import com.gec.dao.ReimburseMapper;
import com.gec.domain.Dept;
import com.gec.domain.ProcessConfig;
import com.gec.domain.Role;
import com.gec.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @author: Mr.Dai
 * @create: 2021-12-10 15:58
 * @description: 工作流程布署Service
 **/
@Service
public class WorkFlowServiceImpl extends BaseService
        implements WorkFlowService{

    @Autowired
    private RepositoryService repService;
    @Autowired
    private ProcessConfigMapper configMapper;
    @Autowired
    private LeaveMapper leaveMapper;
    @Autowired
    private ReimburseMapper reimburseMapper;
    @Override
    public String createDeployment(
            ProcessConfig processConfig,
            InputStream is) {
        //{1}创建压缩流
        ZipInputStream zipIS = new ZipInputStream(is);
        //{2}创建流程布署(写入bpmn,png到数据库)
        Deployment deploy = repService.createDeployment()
                .addZipInputStream(zipIS)
                .name(processConfig.getNote())
                .deploy();
        //{}拿到布署ID
        String deployId = deploy.getId();
        //{3}这里要封装config 数据
        //{a}id 在控制层来做
        //{b}表单传来:  procCategory
        //{c}表单传来:  note
        processConfig.setDeplomentId(deployId);  //设置布署ID

        //{ps}查询流程定义
        ProcessDefinition procDef = repService
        .createProcessDefinitionQuery()
        .deploymentId(deployId)    //根据流程布署ID 来查
        .singleResult();           //获取唯一结果

        //{ps}流程定义ID
        processConfig.setProcDefId(procDef.getId());
        processConfig.setProcKey(procDef.getKey());
        processConfig.setVersion(procDef.getVersion());

        configMapper.addProcessConfig( processConfig );
        //返回一个布署ID
        return deployId;
    }

    @Override
    public Page searchWorkFlowList(String page, String count, Map<String, String> searchValue) {
        print("+--------------[开始搜索数据]-----------------+");
        //{1}开启分页
        Page p = PageHelper.startPage(
                Integer.valueOf(page),
                Integer.valueOf(count),
                true );
        //{2}查询数据
        List<ProcessConfig> list = configMapper.searchConfigList(searchValue);
        print(list);
        print("+--------------[搜索数据结束]-----------------+");
        //{3}返回给控制层
        return p;
    }

    @Override
    public void delployment(String id) {
        //{1}查找该id的布署的类
        ProcessConfig pro = configMapper.getProcConfigById(id);
        //{2}删除布署
        ProcessEngine E = ProcessEngines.getDefaultProcessEngine();
        E.getRepositoryService().deleteDeployment(pro.getDeplomentId(), true);
        print("删除流程成功："+pro.getDeplomentId());
        //{2}查找LeaveBill:1:8231所有布署为这个的
        //t_leave  t_reimburse
        List<String> alll = leaveMapper.findAllProcDefId(pro.getProcDefId());
        if (!alll.isEmpty()){
            for (String i : alll){
                //批量删除
                leaveMapper.delById(i);
            }}
        List<String> allr = reimburseMapper.findAllProcDefId(pro.getProcDefId());
        if (!allr.isEmpty()){
            for (String i : allr){
                //批量删除
                reimburseMapper.delById(i);
            }}

        //{3}删除数据字典表
        configMapper.delplo(id);
    }

}
