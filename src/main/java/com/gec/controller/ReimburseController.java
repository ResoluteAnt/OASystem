package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.Leave;
import com.gec.domain.PageBean;
import com.gec.domain.Reimburse;
import com.gec.domain.User;
import com.gec.service.LeaveService;
import com.gec.service.ReimburseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/16 22:11
 * @Description : com.gec.controller
 * @Version : 1.0
 */
@Controller
@RequestMapping("/Reimburse")
public class ReimburseController extends BaseController{
    //自动装配
    @Autowired
    private ReimburseService reimburseService;
    private Map<String,User> userMap = null;
    public ReimburseController(){
        userMap = UserData.initData();
    }
    /*+------------------------------[负责页面展示]---------------------------------------+*/
    //映射地址：/Reimburse/myInitiate    查询我发起的任务
    @RequestMapping("/myInitiate")
    public String myInitiate(){
        //{ps}内部转发到：myInitiate.jsp
        return "reimburse/myInitiate";
    }
    //映射地址：/Reimburse/waitMyApprove 查询待我审批的任务
    @RequestMapping("/waitMyApprove")
    public String waitMyApprove(){
        //{ps}内部转发到：myInitiate.jsp
        return "reimburse/waitMyApprove";
    }
    //映射地址：/Reimburse/showReimburseForm 申请表单页面
    @RequestMapping("/showReimburseForm")
    public String showReimburseForm(){
        //{ps}内部转发到：myInitiate.jsp
        return "reimburse/leaveForm";
    }
    /*+------------------------------[负责数据传输]---------------------------------------+*/
     /*
        {1}提交请假申请
        {a}映射地址：/Reimburse/submitReimburse
               initiator:$('#initiator').val(),
                money:$('#money').val(),
                type:$('#type').val(),
				reason:$('#reason').val()
    */
    @RequestMapping(
            value = "/submitReimburse",
            produces = "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String submitReimburse(Reimburse reimburse){
        String respTxt = "";
        JSONObject jsObj = new JSONObject();
        try {
            //{1}发起申请
            reimburseService.startProccess( reimburse );
            jsObj.put("result", "success");
            jsObj.put("reimburse", reimburse);
            respTxt = jsObj.toString();
        }catch (Exception e){
            respTxt = setErrorMsg(e);
        }
        return respTxt;
    }
    /*
        {1}查询待我审批的任务
        {a}映射地址：/Reimburse/jsonWaitMyApprove
     */
    @RequestMapping(
            value = "jsonWaitMyApprove",
            produces= "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String jsonWaitMyApprove(
            @RequestParam("assigneeId")String assigneeId,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit){
        PageBean pBean = null;
        try{
            System.out.println("当前人是谁："+assigneeId);
            pBean = reimburseService.queryWaitMyApprove(assigneeId, page, limit);
            System.out.println("控制层查询出来的数据："+ pBean.getList());
            return packJSON( pBean );
        }catch (Exception e){
            e.printStackTrace();
            return setErrorMsg(e);
        }
    }

    /*
        {1}查询我发起的任务
        {a}映射地址：/Reimburse/jsonMyInitiate
     */
    @RequestMapping(
            value = "jsonMyInitiate",
            produces= "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String jsonMyInitiate(
            @RequestParam("initiator")String initiator ){
        //{1}以后要做分页，上面增加参数
        PageBean pBean = null;
        String respTxt = "";
        try{
            pBean= reimburseService.queryMyInitiate( initiator );
            respTxt = packJSON(pBean);
        }catch (Exception e){
            e.printStackTrace();
            //{2}打包出错信息
            respTxt = setErrorMsg(e);
        }
        return respTxt;
    }
    //映射地址：/Reimburse/taskDetail
    //{ps}查询我的任务详情
    @RequestMapping("taskDetail")
    public String taskDetail(
            @RequestParam("taskId")String taskId,
            Model model
    ){
        Reimburse reimburse = null;
        try {
            //{1}查询任务详情
            reimburse = reimburseService.queryMyTaskByTaskId(taskId);
            //{2}设置到模型域
            model.addAttribute("reimburse", reimburse);
            //{3}内部转发到：taskDetail.jsp
            return "reimburse/taskDetail";
        }catch (Exception e){
            e.printStackTrace();
            //{4}这里暂时不去处理
            return "redirect:/error.jsp?msg=x";
        }
    }
    /*+----------------[提交我审批的任务]------------------+*/
    /*
        {1}提交我审批的任务
        映射：/Reimburse/doApprove
        客户端提交一下参数：
            {a}deptId:_deptId,
		    {b}appFlag:_flag,   true(同意)/false(不同意)
		    {c}refuseReason:_reason, 拒绝原因
		    {d}taskId:_taskId  任务ID
    */
    @RequestMapping(
            value = "doApprove",
            produces = "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String doApprove(
            @RequestParam Map map){
        JSONObject jsObj = new JSONObject();
        String respTxt = "";
        try{
            //{1}提交我的审批
            reimburseService.completeMyApprove(map);
            //{2}存入成功的信息
            jsObj.put("result", "success");
            jsObj.put("operation", "doApprove");
            respTxt = jsObj.toString();
        } catch (Exception e){
            e.printStackTrace();
            respTxt = setErrorMsg(e);
        }
        return respTxt;
    }


}
