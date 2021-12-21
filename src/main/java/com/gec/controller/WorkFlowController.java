package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.ProcessConfig;
import com.gec.service.WorkFlowService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * @author: Mr.Dai
 * @create: 2021-12-10 17:09
 * @description: 部署一个新的流程布署控制器
 **/

@Controller
@RequestMapping("/WorkFlow")
public class WorkFlowController extends BaseController {

    //{1}自动装配工作流业务对象
    @Autowired
    private WorkFlowService workFlowService;

    /*
        提交到:   /WorkFlow/deployProc
        提交三项数据:  zipFile  note   category
        自动生成一个: id
        成功之后:  重定向到:  /WorkFlow/viewProList
    */
    @RequestMapping("/deployProc")
    public String deployProc(
        @RequestParam("zipFile") MultipartFile file,
        @RequestParam("note")String note,
        @RequestParam("category") String category
    ){
        String id = makeUUID();
        //{ps}封装一个流程配置Bean
        ProcessConfig config = new ProcessConfig();
        config.setId(id);
        config.setNote(note);
        config.setProcCategory(category);
        String deployId;
        try{
            deployId = workFlowService.createDeployment(
                    config, file.getInputStream() );
            return "redirect:/WorkFlow/viewProcList"+
                    "?deployId="+deployId;
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/WorkFlow/viewUploadProc"+
                    "?errMsg=DeployFailed";
        }
    }


    //新的映射:  /WorkFlow/viewUploadProc
    @RequestMapping("/viewUploadProc")
    public String viewUploadProc(){
        return "/workflow/viewUploadProc";
    }

    // 流程列表
    @RequestMapping("/viewProcList")
    public String viewProcList(){
        return "/workflow/list";
    }

    /**
     * 映射地址：/WorkFlow/jsonList
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
            p = workFlowService.searchWorkFlowList(page, count, searchValue);
            return packJSON( p );
        }catch (Exception e){
            e.printStackTrace();
            return setErrorMsg(e);
        }
    }
    //{3}实现删除流程/WorkFlow/delete
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam("id") String id )
    {
        JSONObject jsObj = new JSONObject();
        try {
            workFlowService.delployment(id);
            jsObj.put("result", "success");
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    //{}暂时写在这里
    private String makeUUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-","");
        return uuid;
    }
}
