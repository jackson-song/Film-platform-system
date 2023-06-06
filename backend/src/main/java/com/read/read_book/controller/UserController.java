package com.read.read_book.controller;


import com.read.read_book.mapper.UserMapper;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

//    数据库连接测试
    @RequestMapping("/user/all/")
//  可以具体请求方式 例如GetMapping("/user/all)
    public List<User> getAll() {
        return userMapper.selectList(null);//null为查询所有用户
    }

    @RequestMapping("/login/{uid}")
    public List<String> getUserbyId(@PathVariable int uid) {
        return userMapper.findallcomment(uid);
    }



    //    注意访问路径最后的/必须要
    @RequestMapping("/user/")
    public String user() {
        return "/hello.html";
    }

    @RequestMapping("/user2/")
    public String user2() {
        return "hello user2";
    }


}
