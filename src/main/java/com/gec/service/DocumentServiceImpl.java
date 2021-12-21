package com.gec.service;

import com.gec.dao.DocumentMapper;
import com.gec.dao.RoleMapper;
import com.gec.domain.Document;
import com.gec.domain.DocumentExample;
import com.gec.exception.FileException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author : JingJie
 * @Date : 2021/12/15 13:23
 * @Description : com.gec.service
 * @Version : 1.0
 */
@Service
public class DocumentServiceImpl extends BaseService
        implements DocumentService {
    @Autowired
    private DocumentMapper documentMapper;
    @Autowired
    private RoleMapper roleMapper;

    /*+----------------[搜索功能]------------------------+*/
    @Override
    public Page searchDocumentList(String page, String count, Map<String,String> searchValue) {
        //{ps}创建查询样本对象
        DocumentExample docEmp = new DocumentExample();
        //{ps}创建条件对象
        DocumentExample.Criteria cri = docEmp.createCriteria();
        //{1}开启分页
        Page p = PageHelper.startPage(
                        Integer.valueOf(page),
                        Integer.valueOf(count),
                        true);
        if (searchValue.get("filename") != null){        //文件名
            cri.andFilenameLike("%"+ searchValue.get("filename") +"%");
        }
        if (searchValue.get("documentname") != null){    //文档公告名
            cri.andDocumentnameLike("%"+ searchValue.get("documentname") +"%");
        }
        if (searchValue.get("uploader") != null){        //上传者
            cri.andUploaderLike("%" + searchValue.get("uploader") +"%");
        }
        if (searchValue.get("note") != null){            //文档说明
            cri.andNoteLike("%"+ searchValue.get("note") +"%");
        }
        List<Document> list = documentMapper.selectByExample(docEmp);
        return p;
    }
    /*+----------------[保存更新功能]------------------------+*/
    @Override
    public void saveOneDocumentInfo(MultipartFile file,Document document)
            throws FileException {
        //{ps}创建查询样本对象
        DocumentExample docEmp = new DocumentExample();
        //{ps}创建条件对象
        DocumentExample.Criteria cri = docEmp.createCriteria();
        if (document.getId() != null ){
            //{1}查询当前id的下载次数，在此基础上加1
            Document key = documentMapper.selectByPrimaryKey(document.getId());
            document.setDowntimes( key.getDowntimes() + 1 );
            //{1}此时进行修改操作
            documentMapper.updateByPrimaryKeySelective(document);
        }
        if(document.getId() == null) {
            //{1}上传文件
            UpdateFile(file);
            //{2}自动生成id值
            String id = createUUID(6);
            document.setId(id);
            document.setDowntimes(0);
            //{3}设置文件大小
            float size = Float.parseFloat(String.valueOf(file.getSize()))/1024/1024;
            //设置保留两位小数
            BigDecimal b = new BigDecimal(size);
            size = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
            document.setFilesize(String.valueOf(size)+"M");
            //{3}设置文件名称
            document.setFilename(  file.getOriginalFilename() );
            cri.andDocumentnameEqualTo( document.getDocumentname() );
            List<Document> list = documentMapper.selectByExample(docEmp);
            System.out.println(list);
            if (!documentMapper.selectByExample(docEmp).isEmpty()){
                throw new FileException("该文件已经存在");
            }
            //{3}保存一条记录
            documentMapper.insert(document);
        }
    }
    //上传文件{保存路径}
    private static String uploadPath = "D:\\project\\UploadFileAddress\\";
    private void UpdateFile(MultipartFile file){
        InputStream is = null;
        OutputStream os = null;
        //{1}拿到上传文件的输入流
        String outPath = uploadPath + file.getOriginalFilename();
        byte[] buff = new byte[4096];
        int count = 0;
        try {
            is = file.getInputStream();
            os = new FileOutputStream(outPath);
            //如果还有数据可读，继续获取
            while ( is.available()>0 ){
                count =is.read(buff);
                //{ps}读多少，写多少
                os.write(buff,0,count);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            closeStream(is,os);
        }
    }
    //关闭连接
    private void closeStream(
            InputStream is , OutputStream os ){
        if (is != null){
            try { is.close(); }catch (Exception e){ }
        }
        if ( os !=null ){
            try { os.close(); }catch (Exception e){ }
        }
    }

    /*+----------------[删除功能]------------------------+*/
    @Override
    public void delOneDocumentById(String id, String fileName) {
        //{1}先删除文件
        File file = new File(uploadPath + fileName);
        file.delete();
        //{2}再删除表
        documentMapper.deleteByPrimaryKey(id);
    }

    /*+----------------[文件下载功能]------------------------+*/
    @Override
    public byte[] downloadByFileName(HttpServletResponse response, Document document) {
        String path = uploadPath + document.getFilename();
        System.out.println(path);
        byte[] data = null;
        try{
            data = getFile(path);
            //{ps}更新数据+1
            saveOneDocumentInfo(null,document);
        }catch (Exception e){ }
        return data;
    }
    //文件下载方法{仅限于小文件}
    private byte[] getFile(String path)
            throws IOException {
        File file = new File(path);
        InputStream is = new FileInputStream(file);
        byte[] buff = new byte[is.available()];
        is.read( buff );
        is.close();
        return buff;
    }
    public Document getDocById(String id){
        Document document = documentMapper.selectByPrimaryKey(id);
        //--------------[异常待处理]------------
        return document;
    }



    @Override
    public Boolean getBooleanByRoleId(String role_id, String s) {
        Set<String> rolePermission = roleMapper.getRolePermission(role_id);
        for(String per : rolePermission){
           if (s.equals(per) || s.split(",")[0].equals(per)){
               return true;
           }
        }
        return false;
    }


}
