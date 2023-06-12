package com.read.read_book.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.pojo.User;
import com.read.read_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //注册
    @Override
    public Map<String, String> register(String email, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (email == null) {
            map.put("error_message", "邮箱不能为空");
            System.out.println(map);
            System.out.println("来过SeriviceImpl");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            System.out.println("密码为null");
            System.out.println(email + password + confirmedPassword);
            map.put("error_message", "密码不能为空");
            System.out.println("来过SeriviceImpl");
            return map;
        }

        //trim 删除头尾空白符
        email = email.trim();
        if (email.length() == 0) {
            System.out.println("去掉头尾空白后 email:" + email);
            map.put("error_message", "邮箱不能为空");
            return map;
        }

        //邮箱合法性判断


        if (password.length() == 0 || confirmedPassword.length() == 0) {
            System.out.println("密码length = 0");
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (email.length() > 100) {
            map.put("error_message", "邮箱长度不能大于100");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "密码长度不能大于100");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "两次输入的密码不一致");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "邮箱已被使用");
            return map;
        }

//        System.out.println("一切正常,准备插入数据");

        //生成随机数用于用户名
        Random r = new Random();
        int rnum = r.nextInt(99999) + 100000;
        String username = "游客" + rnum;

        // 用户注册,username随机生成(可能重复),upower非管理员为0, state:0正常,1冻结;
        User user = new User(null,username,password,null,null,email,null,0,0);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;

    }

}
