package com.gec.service;
import com.gec.domain.Menu;

import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/12 18:04
 * @Description : com.gec.service
 * @Version : 1.0
 */
public interface MenuService {
    //{1}初始化菜单信息"父菜单名称""子菜单名称"
    Map<String,List<Menu>> setInitMenu(String roleId);
}
