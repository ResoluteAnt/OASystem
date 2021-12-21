package com.gec.service;

import com.gec.domain.ProcessConfig;
import com.github.pagehelper.Page;

import java.io.InputStream;
import java.util.Map;

public interface WorkFlowService {

    //{01}创建布署的方法
    String createDeployment(
            ProcessConfig processConfig,
            InputStream is     //压缩流
    );

    //{02}实现菜单列表
    Page searchWorkFlowList(String page, String count, Map<String, String> searchValue);

    //{03}删除
    void delployment(String id);

    //{02}--待定--
    //{03}--待定--
    //{04}--待定--

}
