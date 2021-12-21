package com.gec.controller;

import com.alibaba.fastjson.JSONObject;
import com.gec.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 用户类型转换器
 *
 * @author LW
 * @version V1.0.0
 * @date 2021/12/14
 * @since 1.0
 */
@Component
public class UserConvertor implements Converter<String, User> {

    @Override
    public User convert(String s) {
        User user = new User();
        System.out.println(s);
        if (s != null) {
            String[] data = s.split(",");
            //{1}添加用户时
            if ("add".equals(data[0])){
                user.setUsername(data[1]);
                user.setPassword(data[2]);
                user.setNickName(data[3]);
                user.setDeptId(data[4]);
                user.setRole_id(data[5]);
                user.setEmail(data[6]);
            }
            //{2}修改用户时
            if ("update".equals(data[0])){
                user.setUsername(data[1]);
                user.setNickName(data[2]);
                user.setPassword(data[3]);
                user.setDeptId(data[4]);
                user.setRole_id(data[5]);
                user.setEmail(data[6]);
                user.setId(data[7]);
            }
            return user;
        }
        return null;
    }
}
