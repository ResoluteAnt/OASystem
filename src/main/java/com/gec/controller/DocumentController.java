package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.Document;
import com.gec.domain.User;
import com.gec.exception.FileException;
import com.gec.service.BaseService;
import com.gec.service.DocumentService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/12 15:35
 * @Description : com.gec.controller
 * @Version : 1.0
 */
@Controller
@RequestMapping("/Document")
public class DocumentController extends BaseController {
    @Autowired
    private DocumentService documentService;

    /*+-----------------[文档展示模块]----------------------+*/
    @RequestMapping("/viewList")
    public String viewList(){
        return "/document/list";
    }
    @RequestMapping("/viewUpload")
    public String viewUpload(){
        return  "/document/addNotify";
    }
    /*+-----------------[负责传输数据]----------------------+*/
    //文件列表				映射地址：/Document/jsonList		角色权限：document:jsonList
    @RequestMapping(
            value = "/jsonList",
            produces = "text/html;charset=UTF-8"
    )
    @ResponseBody
    public String jsonList(
            @RequestParam( value = "page", defaultValue = "1") String page,
            @RequestParam( value = "count", defaultValue = "8") String count,
            @RequestParam Map<String,String> searchValue,
            HttpSession session){
//        User user = (User) session.getAttribute("user");
//        String role_id = user.getRole_id();
//        Boolean perssions = documentService.getBooleanByRoleId(role_id,"document:delete");
//        session.setAttribute("per", perssions);
        Page p = null;
        try{
            p = documentService.searchDocumentList(page, count, searchValue);
            return packJSON( p );
        }catch (Exception e){
            e.printStackTrace();
            return setErrorMsg(e);
        }
    }

    //上传文件		映射地址：/Document/upload			角色权限：document:upload
    @RequestMapping("/upload")
    public String upload(
            @RequestParam("multipartFile") MultipartFile multipartFile,
            Document document)  {
        System.out.println("+---------[到达控制层]----------+");
        try{
            documentService.saveOneDocumentInfo(multipartFile, document);
            //{ps}跳转回文件的列表
            return "redirect:/Document/viewList";
        }catch (Exception e){
            e.printStackTrace();
            if ("该文件已经存在".equals(e.getMessage())){
                return "redirect:/Document/viewUpload?cause=The file already exists";
            }
            return "redirect:/Document/viewUpload?cause=Server internal error";
        }
    }

    //删除文件	映射地址：/Document/delete			角色权限：document:delete
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(
            @RequestParam("id") String id )
    {
        JSONObject jsObj = new JSONObject();
        try {
            //{1}根据id查询文件信息
            Document doc = documentService.getDocById(id);
            System.out.println(doc);
            documentService.delOneDocumentById(id, doc.getFilename());
            jsObj.put("result", "success");
            return jsObj.toString();
        }catch (Exception e){
            return setErrorMsg(e);
        }
    }

    //下载文件	映射地址：/Document/download		角色权限：document:download
    @RequestMapping("/download")
    public void download(
            String id,
            HttpServletResponse resp ){
        try{
            //{1}根据id查询文件信息
            Document doc = documentService.getDocById(id);
            System.out.println(doc);
            //{1}得到数据
            byte[] data = documentService.downloadByFileName(resp, doc);
            //{2}设置相应头
            setHeader(resp,doc.getFilename());
            //{3}输出数据
            resp.getOutputStream().write(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
