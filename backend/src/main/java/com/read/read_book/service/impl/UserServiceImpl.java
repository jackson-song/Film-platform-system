package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.read.read_book.mapper.UserMapper;
import com.read.read_book.pojo.User;
import com.read.read_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //注册
    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            System.out.println(map);
            System.out.println("来过SeriviceImpl");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            System.out.println("密码为null");
            System.out.println(username + password + confirmedPassword);
            map.put("error_message", "密码不能为空");
            System.out.println("来过SeriviceImpl");
            return map;
        }

        //trim 删除头尾空白符
        username = username.trim();
        if (username.length() == 0) {
            System.out.println("去掉头尾空白后 username:" + username);
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if (password.length() == 0 || confirmedPassword.length() == 0) {
            System.out.println("密码length = 0");
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能大于100");
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
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "用户名已存在");
            return map;
        }

//        System.out.println("一切正常,准备插入数据");

        User user = new User(null, username, password);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;

    }


}
