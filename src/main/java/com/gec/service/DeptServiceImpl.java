package com.gec.service;

import com.gec.dao.DeptMapper;
import com.gec.domain.Dept;
import com.gec.domain.Option;
import com.gec.exception.EntityException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/16 0:17
 * @Description : com.gec.service
 * @Version : 1.0
 */
@Service
public class DeptServiceImpl extends BaseService
        implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public Page searchDeptList(String page, String count, Map<String, String> value) {
        print("+--------------[开始搜索数据]-----------------+");
        //{1}开启分页
        Page p = PageHelper.startPage(
                Integer.valueOf(page),
                Integer.valueOf(count),
                true );
        //{2}查询数据
        List<Dept> list = deptMapper.searchDept(value);
        for (Dept dept : list) {
            print(dept);
        }
        print("+--------------[搜索数据结束]-----------------+");
        //{3}返回给控制层
        return p;
    }

    @Override
    public void delDeptById(String id) throws EntityException {
        print("+--------------[开始删除数据]-----------------+");
        //{1}先查询是否有子级部门再删除
        List<Dept> chirdDeptById = deptMapper.findChirdDeptById(id);
        //{2}不为空则抛出异常
        if (!chirdDeptById.isEmpty()){
            throw new EntityException("该类存在子级部门，请先删除子级部门");
        }
        deptMapper.delDeptById(id);
        print("+--------------[删除数据结束]-----------------+");
    }

    @Override
    public void saveOneDept(Dept dept) {
        //{1}先判断id是否为空
        System.out.println(dept.getId());
        if (dept.getId() == null){ //进行新增
            print("+--------------[开始增加数据]-----------------+");
            System.out.println(dept);
            //{1}设置id
            String uuid = createUUID(3);
            dept.setId("d0"+uuid);
            if (dept.getParentId() == ""){
                dept.setParentId("top");
                dept.setPids("top");
                dept.setHasSub("1");
                dept.setLevel("LV1");
            }else {
                Dept d = deptMapper.getDeptById(dept.getParentId());
                dept.setPids(d.getId()+","+d.getId());
                dept.setHasSub("0");
                dept.setLevel("LV2");
            }
            deptMapper.saveOneDept(dept);
            print("增加的数据为："+ dept );
            print("+--------------[增加数据结束]-----------------+");
        }else if (dept.getId() != null){ //进行修改
            print("+--------------[开始修改数据]-----------------+");
            System.out.println(dept);
            //{1}先判断部门级别是否改变？
            if (dept.getParentId() == ""){
                dept.setParentId("top");
                dept.setLevel("Lv1");
                dept.setHasSub("1");
                dept.setPids("top");
            }else {
                Dept d = deptMapper.getDeptById(dept.getParentId());
                dept.setPids(d.getId()+","+d.getId());
                dept.setHasSub("0");
                dept.setLevel("LV2");
            }
            deptMapper.updateOneDept(dept);
            print("+--------------[修改数据结束]-----------------+");
        }
    }

    @Override
    public Dept getDeptById(String id) throws EntityException {
        print("+--------------[开始获取一个部门信息]-----------------+");
        Dept dept = deptMapper.getDeptById(id);
        if (dept == null){
            throw new EntityException("该部门不存在");
        }
        print(dept);
        print("+--------------[开始获取一个部门信息结束]-----------------+");
        return dept;
    }

    @Override
    public Map<String, Object> getSelectData() {
        Map<String,Object> map = new HashMap();
        print("+--------------[开始获取下拉列表数据]-----------------+");
        List<Option> allDept = deptMapper.getAllDept();
        map.put("allDept", allDept);
        for (Option option: allDept) {
            print(option);
        }
        print("+--------------[获取下拉列表数据结束]-----------------+");
        return map;
    }
}
