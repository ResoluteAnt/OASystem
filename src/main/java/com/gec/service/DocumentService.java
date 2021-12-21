package com.gec.service;


import com.gec.domain.Document;
import com.gec.exception.FileException;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author : JingJie
 * @Date : 2021/12/15 13:16
 * @Description : com.gec.service
 * @Version : 1.0
 */
public interface DocumentService {
    //{1}模糊查询文件列表
    Page searchDocumentList(
            String page,       //页数
            String count,       //每页数量
            Map<String,String> searchValue //搜索值
    );
    //{2}保存或者修改文件下载次数文件信息(上传文件)
    void saveOneDocumentInfo(MultipartFile file, Document document) throws FileException;
    //{3}删除一个文件信息以及存放在电脑中文件
    void delOneDocumentById(String id , String fileName);

    //{4}判断角色权限
    Boolean getBooleanByRoleId(String role_id, String s);
    //{5}下载功能
    byte[] downloadByFileName(HttpServletResponse response,Document document);
    //{6}根据id获得文件信息
    Document getDocById(String id);
}
