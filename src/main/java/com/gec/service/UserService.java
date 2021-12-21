package com.gec.service;

import com.gec.domain.User;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/13 14:40
 * @Description : com.gec.service
 * @Version : 1.0
 */
public interface UserService {
    //{1}searchUserList : 分页查询(使用分页插件)
    Page searchUserList(
            String page,       //页数
            String count,       //每页数量
            Map<String,String> value //搜索值
    );
    //{2}getOneUserById：根据用户Id从数据库中查询一条记录
    Map<String,Object> getOneUserById(String id);
    //{3}delOneUserById：根据用户id删除用户
    void delOneUserById(String id);

    //{4}saveOneUser：根据User对象保存或者修改用户
    void saveOneUser(User user);

    //{5}获取下拉列表数据
    Map<String, Object> getSelectData();
}
