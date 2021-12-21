package com.gec.dao;

import com.gec.domain.ProcessConfig;
import com.gec.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProcessConfigMapper {
	//{1}获取流程配置
    ProcessConfig getProcConfigByCategory(
            @Param("category") String category);
	//{2}添加布署
	int addProcessConfig(ProcessConfig cfg);
	//{3}搜索列表
	List<ProcessConfig> searchConfigList(
			Map<String,String> map);
	//{3}根据id查询一个数据字典对象
	ProcessConfig getProcConfigById(
			@Param("id") String id);
	//{4}根据id删除一条记录
	void delplo(
			@Param("id") String id);
}
