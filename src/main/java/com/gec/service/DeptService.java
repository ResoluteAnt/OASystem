package com.gec.service;

import com.gec.domain.Dept;
import com.gec.exception.EntityException;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/16 0:17
 * @Description : com.gec.service
 * @Version : 1.0
 */
public interface DeptService {
    //{1}进行模糊搜索
    Page searchDeptList(
            String page,       //页数
            String count,       //每页数量
            Map<String,String> value //搜索值
    );
    //{2}删除功能
    void delDeptById(String id) throws EntityException;
    //{3}保存或者修改部门
    void saveOneDept(Dept dept);
    //{4}根据id查找一个部门的数据
    Dept getDeptById(String id) throws EntityException;
    //{5}获取下拉菜单数据
    Map<String, Object> getSelectData();
}
