package com.gec.shiro;

import com.gec.dao.MenuMapper;
import com.gec.domain.Permission;

import com.gec.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : JingJie
 * @Date : 2021/12/11 15:15
 * @Description : 过滤器数据源{用它来产生过滤器链的配置数据}
 * @Version : 1.0
 */
public class FilterDataSource {
    @Autowired
    private MenuMapper menuMapper;

    //{1}定义相关属性
    //  过滤器的定义
    Map<String,String> definitions = new LinkedHashMap();
    //  正则表达式(用于去除空白字符的)
    private String regex = "\\s*([^\\s]+)\\s*=\\s*([^\\s]+)\\s*";

    //{方法1}去除空白字符用的
    private String[] trimSpace(String line,
                               String regex){
        Pattern cmp = Pattern.compile(regex);
        //{1}获取匹配器
        Matcher mat = cmp.matcher(line);
        if ( !mat.matches() ){
            throw new RuntimeException("配置格式有限");
        }
        //{3}返回一个字符串数组
        return new String[]{
            mat.group(1),mat.group(2)
        };
    }
    //{方法2}设置匿名访问的资源
    public void setAnonymousFilter(
            String definition
    ){
        String[] lines = definition.split("\n");
        System.out.println("+-----------------[OA系统权限不拦截权限]----------------------+");
        for (String L : lines){
            L = L.trim();  //去除前后的空格
            if (L.length()==0) continue;
            //{1}裁掉里面多余的空白空格
            String[] data = trimSpace(L, regex);
            //{2}设置到map中
            definitions.put(data[0], data[1]);
            //{3}输出
            System.out.printf("[%s]={%s}\n",
                    data[0],data[1]);
        }
    }

    //{5}提供给外部获取过滤器链的Map
    public Map getFilterDefinitions(){
        //{1}调取数据库中的数据【
        Set<Permission> sysPerms = menuMapper.getSysPermission();
        System.out.println("+-----------------[OA系统权限]----------------------+");
        //{2}将数据封装成：perms[user:save]这种格式
        for (Permission p : sysPerms){
            StringBuffer sb = new StringBuffer("perms[");
            sb.append( p.getPermission() );
            sb.append( "]" );
            definitions.put(p.getMapping(), sb.toString());
            //输出所有系统权限
            System.out.printf("%s=%s\n", p.getMapping(),
                    sb.toString());
        }
        //+------------------------------+
        //{4}退出后的功能
        definitions.put("/User/logout", "logout");
        //{3}补多一个过滤器(需要登录的资源)
        definitions.put("/**", "authc");
        //{4}返回map
        return definitions;
    }
}
