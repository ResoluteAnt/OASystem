package com.gec.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BaseService {	
	protected String createUUID(int len){
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "").substring(0, len);
	}
	protected int calDays(Date start, Date end){
		long _end = end.getTime();
		long _start = start.getTime();
		long _len = _end -_start;
		int day = (int)(_len / 1000 / 3600 / 24);
		return day;
	}
	protected String dateToStr(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd");
		return sdf.format(date);
	}
	protected void print(Object obj){
		System.out.println(obj);
	}
	protected void print(String funcName,Object obj){
		System.out.println("+-------------------[开始"+funcName+"]----------------------+");
		System.out.println(obj);
		System.out.println("+-------------------[结束"+funcName+"]----------------------+");
	}

}
