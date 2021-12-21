package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.Leave;
import com.gec.domain.PageBean;
import com.gec.domain.User;
import com.gec.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/7 19:39
 * @Description : com.gec.controller
 * @Version : 1.0
 */
@Controller
@RequestMapping("/Leave")
public class LeaveController extends BaseController{
    //自动装配
    @Autowired
    private LeaveService leaveService;
    private Map<String,User> userMap = null;
    public LeaveController(){
        userMap = UserData.initData();
    }


    /*+--------------------[用户申请请假的]--------------------+*/
     /*
        {2}展示用户申请页
            {a}映射地址：/showLeaveForm[入口]
            {b}内部转发：leaveForm.jsp
     */
    @RequestMapping("/showLeaveForm")
    public String showLeaveForm(){
        //内部转发：leave/leaveForm.jsp
        return "leave/leaveForm";
    }
    /*
        {3}提交请假申请
        {a}映射地址：/Leave/submitLeave
        initiator:$('#initiator').val(),
		startDate:$('#startDate').val(),
		endDate:$('#endDate').val(),
		leaveType:$('#leaveType').val(),
		reason:$('#reason').val()
    */
    @RequestMapping(
            value = "/submitLeave",
            produces = "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String submitLeave(Leave leave){
        String respTxt = "";
        JSONObject jsObj = new JSONObject();
        try {
            //{1}发起申请
            leaveService.startProccess( leave );
            jsObj.put("result", "success");
            jsObj.put("leave", leave);
            respTxt = jsObj.toString();
        }catch (Exception e){
            respTxt = setErrorMsg(e);
        }
        return respTxt;
    }
    /*+----------------------[查询我发起的任务]-------------------------+*/
    @RequestMapping("/myInitiate")
    public String myInitiate(){
        //{ps}内部转发到：myInitiate.jsp
        return "leave/myInitiate";
    }


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
            pBean= leaveService.queryMyInitiate( initiator );
            respTxt = packJSON(pBean);
        }catch (Exception e){
            e.printStackTrace();
            //{2}打包出错信息
            respTxt = setErrorMsg(e);
        }
        return respTxt;
    }
    //+------------------[查询待我审批的业务]------------------+
    @RequestMapping("waitMyApprove")
    public String waitMyApprove(){
        //{ps}内部转发到：myInitiate.jsp
        return "leave/waitMyApprove";
    }
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
            pBean = leaveService.queryWaitMyApprove(assigneeId, page, limit);
            return packJSON( pBean );
        }catch (Exception e){
            e.printStackTrace();
            return setErrorMsg(e);
        }
    }
    //{ps}查询我的任务详情
    @RequestMapping("taskDetail")
    public String taskDetail(
            @RequestParam("taskId")String taskId,
            Model model
    ){
        Leave leave = null;
        try {
            //{1}查询任务详情
            leave = leaveService.queryMyTaskByTaskId(taskId);
            //{2}设置到模型域
            model.addAttribute("leave", leave);
            //{3}内部转发到：taskDetail.jsp
            return "leave/taskDetail";
        }catch (Exception e){
            e.printStackTrace();
            //{4}这里暂时不去处理
            return "redirect:/error.jsp?msg=x";
        }
    }
    /*+----------------[提交我审批的任务]------------------+*/
    /*
        {1}提交我审批的任务
        映射：/Leave/doApprove
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
            leaveService.completeMyApprove(map);
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
