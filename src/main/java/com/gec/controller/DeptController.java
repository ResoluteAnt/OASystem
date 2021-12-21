package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.Dept;
import com.gec.domain.Option;
import com.gec.service.DeptService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/13 19:07
 * @Description : com.gec.controller
 * @Version : 1.0
 */
@Controller
@RequestMapping("/Dept")
public class DeptController extends BaseController{
    @Autowired
    private DeptService deptService;
    /*-----------------------------------负责展示页面-----------------------------------*/
    //{ps}跳转到展示列表的界面   /Dept/viewList
    @RequestMapping("/viewList")
    public String viewList(){
        return "/dept/list";
    }
    //{ps}跳转到新增部门的界面   /Dept/viewAdd
    @RequestMapping("/viewAdd")
    public String viewAdd(){
        return "/dept/addDept";
    }
    /*--------------------------------负负责传输数据--------------------------------*/
    //部门信息			映射地址：/Dept/jsonInfo		权限：dept:jsonInfo    [ok]
    @RequestMapping("/jsonInfo")
    @ResponseBody
    public String jsonInfo(
            @RequestParam("id") String id )
    {
        JSONObject jsObj = new JSONObject();
        try {
            Dept dept = deptService.getDeptById(id);
            jsObj.put("result", "success");
            jsObj.put("dept", dept);
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    //部门列表(json)	    映射地址：/Dept/jsonList		权限：dept:jsonList    [ok]
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
            p = deptService.searchDeptList(page, count, searchValue);
            return packJSON( p );
        }catch (Exception e){
            e.printStackTrace();
            return setErrorMsg(e);
        }
    }
    //删除部门			映射地址：/Dept/delete		权限：dept:delete
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam("id") String id )
    {
        JSONObject jsObj = new JSONObject();
        try {
            deptService.delDeptById(id);
            jsObj.put("result", "success");
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }
    //保存部门			映射地址：/Dept/save			权限：dept:save
    @RequestMapping("/save")
    @ResponseBody
    public String save( Dept dept) {
        System.out.println("{控制层}："+dept);
        JSONObject jsObj = new JSONObject();
        try {
            deptService.saveOneDept(dept);
            jsObj.put("result", "success");
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }
    //获取子部门		    映射地址：/Dept/getSubDept	权限：dept:getSubDept
    /*-----待补充-----*/


    /*+----------------------------[初始化下拉菜单的数据]----------------------------+*/
    @RequestMapping(
            value = "/getSelectData",
            produces= "text/html;charset=UTF-8")
    @ResponseBody
    public String getSelectData()
    {
        JSONObject jsObj = new JSONObject();
        try {
            Map<String, Object> data = deptService.getSelectData();
            List<Option> allDept = (List<Option>)data.get("allDept");
            jsObj.put("result", "success");
            jsObj.put("deptData", allDept);
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }
}
