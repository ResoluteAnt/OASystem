package com.gec.dao;

import com.gec.domain.Option;
import com.gec.domain.Role;
import com.gec.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {
    //{1}根据角色id查询角色权限
    Set<String> getRolePermission(
            @Param("roleId") String roleId);
    //{2}查询所有角色
    List<Option> getAllRole();
    //{3}修改角色id
    void updateOneRoleInfo(User user);
}
