package com.gec.service;

import com.gec.dao.MenuMapper;
import com.gec.domain.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author : JingJie
 * @Date : 2021/12/12 18:06
 * @Description : com.gec.service
 * @Version : 1.0
 */
@Service
public class MenuServiceImpl implements MenuService{
    @Autowired
    private MenuMapper menuMapper;

    private List<Menu> perMap = new LinkedList();
    private Map<String,List<Menu>> menuMap;



    /*+--------------------------[查询对应权限菜单权限]----------------------------+*/
    /**
     * {1}初始化菜单信息
     */
    @Override
    public Map<String,List<Menu>> setInitMenu(String roleId){
        menuMap = new HashMap();
        List<String> sysPerms = menuMapper.getMenuDateByRoleId(roleId);
        String temMenu = "";
        for (int i=0 ; i<sysPerms.size(); i++){
            System.out.println("per : " + sysPerms.get(i));
            String[] split = sysPerms.get(i).split(":");
            //{1}查询所有菜单
            Menu supMenu = menuMapper.getMenuDataOne( split[0]+":" );
            containData(supMenu);//去除(view)
            //{2}处理查询出来的菜单数据
            dealMapDate(perMap,  menuMapper.getMenuDataAll( supMenu.getId() ) );
            if (split.length == 1){         // user:
                //{ps}设置一个临时的list
                //System.out.println(perMap);
                //{3}存入map中 主菜单名，子菜单
                menuMap.put(supMenu.getNote(),
                        dealMapDate(new LinkedList<Menu>(), perMap));
                //print(menuMap);
                //{4}清空
                perMap.clear();

            }else if (split.length == 2){   //document:viewList
                if (!split[0].equals(temMenu)) {
                    //{4}解析数据并添加到menuMap中，用于交换数据
                    prtData(supMenu,sysPerms);
                    //{5}存入临时的字符串中
                    temMenu = split[0];
                }
                //{4}记得清空
                perMap.clear();
            }
        }
        //{7}输出菜单
        print(menuMap);
        return menuMap;
    }
    public void clearMap(){

    }
    private void prtData (Menu supMenu, List<String> sysPerms) {
        List<Menu> temp = new LinkedList();
        for (String per : sysPerms) {
            for (int i = 0 ; i < perMap.size();i++) {
                Menu menu = perMap.get(i);
                //{ps}权限相同的时候添加进来
                if (per.equals(menu.getPermission())){
                    temp.add(menu);
                }
            }
        }
        menuMap.put(supMenu.getNote(), temp);
    }
    //{ps}处理查询出来的菜单数据
    private List<Menu> dealMapDate(
            List<Menu> resData,List<Menu> dbData){
        for (Menu menu: dbData) {
            System.out.println(menu);
            containData(menu);
            resData.add(menu);
        }
        return resData;
    }
    //去除view
    private void containData(Menu menu){
        if (menu.getNote().contains("(view)")){
            String note = menu.getNote()
                    .replaceAll("[(view)]", "");
            menu.setNote(note);
        }
    }
    //{3}输出语句
    private void print(Map<String,List<Menu>> map){
        System.out.println("+----------------[数值如下]----------------+");
        Set<String> keys = map.keySet();
        for (String key: keys) {
            System.out.println(key);
            List<Menu> menus = map.get(key);
            for (Menu menu : menus) {
                System.out.println(menu);
            }
        }
        System.out.println("+----------------------------------------+");
    }
}
