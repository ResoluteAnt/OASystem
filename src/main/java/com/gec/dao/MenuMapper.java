package com.gec.dao;

import com.gec.domain.Menu;
import com.gec.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface MenuMapper {
    //{1}获取系统菜单
    Set<Permission> getSysPermission();
    //{2}根据主菜单的id获得主菜单下的所有子菜单的名称
    List<Menu> getMenuDataAll(
            @Param("supMenuId") String supMenuId );
    //{3}根据权限一个相应的菜单(用来查询主菜单)
    Menu getMenuDataOne(
            @Param("permission") String permission);
    //{4}根据角色Id去获得权限
    List<String> getMenuDateByRoleId(
            @Param("RoleId") String RoleId);
}
