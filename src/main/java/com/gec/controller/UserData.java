package com.gec.controller;

import com.gec.domain.Dept;
import com.gec.domain.Role;
import com.gec.domain.User;

import java.util.HashMap;
import java.util.Map;
/**
 * @Author : JingJie
 * @Date : 2021/12/7 17:37
 * @Description : 模拟登录功能
 * @Version : 1.0
 */
public class UserData {

	//{ps}把使用到的用户存入 Map 中。
	public static Map<String,User> initData(){
		Map users = new HashMap();
		Dept top = new Dept("d01","总经办");   //OK
		Dept dept = new Dept("d02","财务部");  //OK

		Role rBoss = new Role("r02","leader");    //OK
		Role rLeader = new Role("r02","leader");  //OK
		Role rEmp = new Role("r05","employee");   //OK

		User uBoss = new User("u03","boss",top,rBoss);
		User uLeader = new User("u06","kailen",dept,rLeader);
		User uEmp1 = new User("u07","terry",dept,rEmp);
		User uEmp2 = new User("u08","yuanFang",dept,rEmp);

		users.put("boss", uBoss);
		users.put("kailen", uLeader);
		users.put("terry", uEmp1);
		users.put("yuanFang", uEmp2);

		return users;
	}

}
