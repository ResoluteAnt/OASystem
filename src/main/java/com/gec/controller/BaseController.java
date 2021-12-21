package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.PageBean;
import com.gec.domain.User;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author : JingJie
 * @Date : 2021/12/7 17:36
 * @Description : 用于封装数据
 * @Version : 1.0
 */
public abstract class BaseController {

	protected String packJSON( Page p ){
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		long count = p.getTotal();
		jsObj.put("count",count);
		List<User> result = p.getResult();
		System.out.println("count="+ count);
		jsObj.put("data", result);
		return jsObj.toJSONString();
	}

	protected String packJSON( PageBean pBean ){
		JSONObject jsObj = new JSONObject();
		jsObj.put("code", "0");
		jsObj.put("msg", "");
		jsObj.put("count", pBean.getCount());
		jsObj.put("data", pBean.getList());
		return jsObj.toJSONString();
	}

	protected String setErrorMsg( Exception e ) {
		e.printStackTrace();
		JSONObject jsObj = new JSONObject();
		jsObj.put("result", "failed");
		jsObj.put("cause", e.getMessage());
		return jsObj.toJSONString();
	}

	//{3} now 方法
	//功能: 获取当前时间。
	protected String now(){
		SimpleDateFormat sdf =
				new SimpleDateFormat("HH:mm:ss");
		Date d = new Date();
		return sdf.format( d );
	}

	//设置响应头的方法
	protected void setHeader(HttpServletResponse resp,
						   String fileName)
			throws Exception {
		//{1}对文件名进行url编码
		fileName = URLEncoder.encode(fileName, "UTF-8");
		//{2}设置ContentType类型
		resp.setContentType("application/x-msdownload");
		//{3}设置Content-Disposition
		resp.addHeader("Content-Disposition",
				"attachment;filename=\""
						+fileName +"\"");
	}
}
