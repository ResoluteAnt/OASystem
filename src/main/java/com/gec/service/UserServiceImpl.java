package com.gec.service;

import com.gec.dao.DeptMapper;
import com.gec.dao.RoleMapper;
import com.gec.dao.UserMapper;
import com.gec.domain.Dept;
import com.gec.domain.Option;
import com.gec.domain.Role;
import com.gec.domain.User;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author : JingJie
 * @Date : 2021/12/13 14:41
 * @Description : com.gec.service
 * @Version : 1.0
 */
@Service
public class UserServiceImpl extends BaseService
        implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Page searchUserList
            (String page, String count, Map<String, String> value) {
        print("+--------------[开始搜索数据]-----------------+");
        //{1}开启分页
        Page p = PageHelper.startPage(
                    Integer.valueOf(page),
                    Integer.valueOf(count),
                true );
        //{2}查询数据
        List<User> list = userMapper.searchUserList(value);
        for (User user : list) {
            //{3}通过部门id查找部门，并为list设置部门
            Map<String,String> map = new HashMap();
            map.put("id", user.getDeptId());
            List<Dept> depts= deptMapper.searchDept(map);
            //{4}设置部门(当部门没有时则跳过，不设置)
            if (depts.size() == 0) continue;
            user.setDept(depts.get(0));
        }
        print(list);
        print("+--------------[搜索数据结束]-----------------+");
        //{3}返回给控制层
        return p;
    }

    @Override
    public  Map<String,Object> getOneUserById(String id) {
        Map<String,Object> map = new HashMap();
        print("+--------------[开始查询数据]-----------------+");
        User user = userMapper.getOneUserById(id);
        map.put("user", user);
        print("+--------------[查询数据结束]-----------------+");
        return map;
    }

    @Override
    public Map<String, Object> getSelectData() {
        Map<String,Object> map = new HashMap();
        print("+--------------[开始获取下拉列表数据]-----------------+");
        List<Option> allDept = deptMapper.getAllDept();
        List<Option> allRole = roleMapper.getAllRole();
        map.put("allDept", allDept);
        map.put("allRole", allRole);
        print("+--------------[获取下拉列表数据结束]-----------------+");
        return map;
    }

    @Override
    public void delOneUserById(String id) {
        print("+--------------[开始删除数据]-----------------+");
        //{ps}总共要删除两张表的记录
        //{1}删除user表(数据库表设置了外键会同时删除两张表的数据)
        userMapper.delOneUserById(id);
        print("+--------------[删除数据结束]-----------------+");
    }

    @Override
    public void saveOneUser(User user){
        Md5Hash md5Hash = new Md5Hash(user.getPassword());
        user.setPassword(String.valueOf(md5Hash));
        //{1}判断是否有id
        if (user.getId() != null){
            print("+--------------[开始修改数据]-----------------+");
            //{ps}进行修改用户
            System.out.println(user);
            userMapper.updateOneUserInfo(user);
            if (user.getRole_id()!=null){
                roleMapper.updateOneRoleInfo(user);
            }
            print("+--------------[修改数据结束]-----------------+");
        }else {
            print("+--------------[开始增加数据]-----------------+");
            print(user);
            //{2}自定义用户id
            String userId = createUUID(5);
            user.setId("u"+userId);
            user.setNo("[emp]"+userId);
            //comment由系统自动生成    格式：andy [employee] [d01:开发部]
            String comment = user.getUsername()+
                    "["+ getRole(user.getRole_id())+"]"+
                    "["+ user.getDeptId()+":"+user.getDeptName()+"]";
            //{ps}进行新增用户
            //{1}先保存用户表
            userMapper.saveOneUser(user);
            print(user.getRole_id());
            //{2}再保存用户关系表
            userMapper.saveOneUserRole(user.getId(),
                    user.getRole_id() ,comment);
            print("+--------------[增加数据结束]-----------------+");
        }
    }




    /*+----------------------------[临时角色关系对应表]-------------------------------------+*/
    private Map<String,String> map = new HashMap();
    private String getRole(String roleId){
        map.put("r0*", "admin");
        map.put("r01", "leader");
        map.put("r02", "leader");
        map.put("r03", "manager");
        map.put("r04", "teamLeader");
        map.put("r05", "employee");
        return map.get(roleId);
    }


    /*+----------------------------[输出测试方法]-------------------------------------+*/
    private void print(List<User> list){
        for (User user : list ) {
            String roleDesc = null;
            Role role = user.getRole();
            if (role != null){
                roleDesc = role.getRoleDesc();
            }
            System.out.printf("{%s},{%s},{%s},{%s},{%s},{%s}\n",
                    user.getId(),user.getUsername(),
                    user.getNickName(),user.getDeptId(),
                    roleDesc,user.getCreateDate());
        }
    }

}
