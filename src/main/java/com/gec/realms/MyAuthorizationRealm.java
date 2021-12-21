package com.gec.realms;

import com.gec.dao.RoleMapper;
import com.gec.dao.UserMapper;
import com.gec.domain.User;
import com.gec.service.MenuService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author : JingJie
 * @Date : 2021/12/10 22:26
 * @Description : 实现登录认证以及授权的域 Realm
 * @Version : 1.0
 */
public class MyAuthorizationRealm
        extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /*+--------------------[初始化用户数据]---------------------+*/
    public MyAuthorizationRealm(){
        //----[待定]----
    }
    /*+--------------------[初始化权限相关数据]---------------------+*/
    Map<String,Set<String>> rolePermMap = new HashMap();
    public void initRealm(){
        //{1}暂时写死，后面再写活
        Set<String> r0 =  roleMapper.getRolePermission("r0*");  //管理员
        Set<String> r01 =  roleMapper.getRolePermission("r01"); //boss
        Set<String> r02 =  roleMapper.getRolePermission("r02"); //leader
        Set<String> r03 =  roleMapper.getRolePermission("r03"); //manager
        Set<String> r05 =  roleMapper.getRolePermission("r05"); //Employee

        rolePermMap.put("r0*", r0);
        rolePermMap.put("r01", r01);
        rolePermMap.put("r02", r02);
        rolePermMap.put("r03", r03);
        rolePermMap.put("r05", r05);
    }

    //Zation:是做用户授权 与 权限认证的
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo
            (PrincipalCollection collection) {
        //{1}获取主身份信息(做认证时放入的时User，所以这里拿到的时User)
        User priPrincipal = (User)collection.getPrimaryPrincipal();
        /*+---------------------------------------------------------+*/
        //{2}获取角色 ID
        String roleId = priPrincipal.getRole().getId();
        //{3}通过角色名获取角色权限.{从缓存中获取}
        Set<String> permissions = rolePermMap.get( roleId );
        //{4}把数据传给菜单让他去初始化
        /*+---------------------------------------------------------+*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //{4}添加一个角色进来
        //info.addRole( roleName );     //这里注释掉
        System.out.println("+-----["+priPrincipal.getUsername()+"权限如下]-------+");
        for (String per: permissions) {
            System.out.println( per );
        }
        //{5}设置权限
        info.setStringPermissions( permissions );
        //{6}最后将这个信息对象返回给授权认证器处理
        return info;
    }
    //Cation：是做登录认证的
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo
            (AuthenticationToken token)
            throws AuthenticationException {
        //{1}得到用户名
        String principal = (String) token.getPrincipal();
        //{2}得到用户密码
        char[] password = (char[]) token.getCredentials();
        String _password = new String(password);

        //{3}从数据库中获得用户信息(数据库中的用户)
        User dbuser = userMapper.getUserByName(principal);
        if (dbuser==null){
            return null; //认证器收到null，就会抛出未知用户异常
        }
        System.out.println();

        //{ps}加密这个信息类稍后再用
//        new SimpleAuthenticationInfo(
//                principal,hashCredentials,
//                credentialsSalt,realmName);
        //{4}创建认证信息类对象(不加密的)[认证器会自动判断密码]
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                dbuser, dbuser.getPassword(), getName()
              //数据用户      数据密码           realm名称
        );
        return info;
    }
}
