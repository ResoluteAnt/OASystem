package com.gec.dao;

import com.gec.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    //{1}根据角色名查询一个角色
    User getUserByName(
            @Param("username") String username);

    //{2}UserMapper：查询一个List列表到前端
    List<User> searchUserList(
            Map<String,String> map);

    //{3}根据用户id查询出一个用户的数据
    User getOneUserById(
            @Param("id") String id);

    //{4}根据用户id删除一个用户
    int delOneUserById(
            @Param("id") String id);

    //{5}根据User对象保存一个用户
    int saveOneUser( User user );
    //{6}根据user_id 和 role_id 保存一个关系表记录
    int saveOneUserRole(
            @Param("user_id") String user_id,
            @Param("role_id") String role_id,
            @Param("comment") String comment);

    //{7}根据User对象修改一个用户的信息
    int updateOneUserInfo(User user);
}
