package com.gec.dao;

import com.gec.domain.Dept;
import com.gec.domain.Option;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeptMapper {

    //{A}功能:获取部门领导ID
    String findDeptLeaderId(
            @Param("deptId") String deptId);

    //{B}功能:获取上级部门的 ID
    String findHigherLevelDept(
            @Param("deptId") String deptId);

    //{C}功能:根据前端传来的map进行数据搜索
    List<Dept> searchDept(
            Map<String,String> map);

    //{D}功能：查询所有部门
    List<Option> getAllDept();

    //{E}功能：新增一个部门
    void saveOneDept(
            Dept dept);

    //{F}更新一个部门数据
    void updateOneDept(
            Dept dept);

    //{G}根据id搜索他下面是否还有部门 有就不允许删除
    List<Dept> findChirdDeptById(
            @Param("id") String id);

    //{H}根据部门id删除部门
    void delDeptById(
            @Param("id") String id);
    //{F}根据id查询一个部门
    Dept getDeptById(
            @Param("id") String id );

    //{H}获取部门的经理
    String findDeptManager(String deptId);

}

