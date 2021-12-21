package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.*;
import com.gec.service.MenuService;
import com.gec.service.UserService;
import com.github.pagehelper.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/11 9:15
 * @Description : 实现用户的登录注册功能
 * @Version : 1.0
 */
@Controller
@RequestMapping("/User")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    /*+-----------------[用户登录模块]----------------------+*/
   //{1}跳转到首页
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    //{2}内部转发到登录页
    @RequestMapping("/showLogin")
    public String showLogin(){
        return "forward:/login.jsp";
    }
    //{3}执行退出功能 --> 销毁会话 -->重定向会首页
    @RequestMapping("/logout")
    public String logout(
            HttpSession session){

        //{1}销毁会话
        session.invalidate();
        //{2}重定向会登录页
        return "forward:/login.jsp";
    }
    //{3}执行登录方法
    @RequestMapping("/login")
    public String login(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            HttpSession websession){
        Md5Hash md5Hash = new Md5Hash(password);
        //{1}创建令牌,载入用户名+密码
        UsernamePasswordToken token = 
                new UsernamePasswordToken(username, String.valueOf(md5Hash));
        //{2}获取主体对象
        Subject subject = SecurityUtils.getSubject();
        String errcode = "0";
        try{
            //{3}执行登录
            subject.login(token);
            //{4}尝试使用shiro提供的session
            Session session = subject.getSession(true);
            //{5}获取从数据库中得到的用户的身份信息
            User user = (User)subject.getPrincipal();
            //{6}查询该角色的菜单信息
            Map<String,List<Menu>> menuInfo =
                    menuService.setInitMenu(user.getRole().getId());
            //{7}设置到会话域
            session.setAttribute("user", user);
            session.setAttribute("menuInfo", menuInfo);
            //{8}重定向到主页
            return "redirect:/User/index";
        }catch (UnknownAccountException e){
            errcode = "1";
        }catch (IncorrectCredentialsException e){
            errcode = "2";
        }
        return "redirect:/User/showLogin?errcode="+errcode;
    }


    /*+-----------------[用户列表模块]----------------------+*/
    //{ps}跳转到用户列表界面的
    @RequestMapping("/viewList")
    public String viewList(){
        return "/user/list";
    }

    /**
     * 映射地址：/User/viewList
     * 功能说明：搜索用户的数据
     */
    @RequestMapping(
            value = "/jsonList",
            produces= "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String jsonList(
            @RequestParam( value = "page", defaultValue = "1") String page,
            @RequestParam( value = "count", defaultValue = "8") String count,
            @RequestParam Map<String,String> searchValue)
    {
        Page p = null;
        try{
            p = userService.searchUserList(page, count, searchValue);
            return packJSON( p );
        }catch (Exception e){
            e.printStackTrace();
            return setErrorMsg(e);
        }
    }
    /*+-----------------[保存用户模块]----------------------+*/
    //{ps}跳转到添加用户的界面  /Dept/viewAdd
    @RequestMapping("/viewAdd")
    public String viewAdd(){
        return "/user/addUser";
    }

    /*+-----------------[修改用户模块]----------------------+*/
    //{ps}根据用户id获得一条记录    /User/jsonInfo
    @RequestMapping("/jsonInfo")
    @ResponseBody
    public String jsonInfo(
            @RequestParam("id") String id )
    {
        JSONObject jsObj = new JSONObject();
        try {
            Map<String, Object> data = userService.getOneUserById(id);
            User user = (User) data.get("user");
            jsObj.put("result", "success");
            jsObj.put("user", user);
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    /*+-----------------[删除用户模块]----------------------+*/
    //{ps}根据用户id删除用户        /User/delete
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam("id") String id )
    {
        JSONObject jsObj = new JSONObject();
        try {
            userService.delOneUserById(id);
            jsObj.put("result", "success");
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    /*+-----------------[保存用户模块]----------------------+*/
    @RequestMapping("/save")
    @ResponseBody
    public String save(
            @RequestParam User user )
    {
        JSONObject jsObj = new JSONObject();
        try {
            userService.saveOneUser(user);
            jsObj.put("result", "success");
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    /*+-----------------[数据模块]----------------------+*/
    @RequestMapping(
            value = "/getSelectData",
            produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String getSelectData()
    {
        JSONObject jsObj = new JSONObject();
        try {
            Map<String, Object> data = userService.getSelectData();
            List<Option> allRole = (List<Option>)data.get("allRole");
            List<Option> allDept = (List<Option>)data.get("allDept");
            jsObj.put("result", "success");
            jsObj.put("roleData", allRole);
            jsObj.put("deptData", allDept);
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    /*+-----------------[其它模块]----------------------+*/
    //------[待补充]------

}
