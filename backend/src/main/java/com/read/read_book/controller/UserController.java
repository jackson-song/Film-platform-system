package com.read.read_book.controller;


import com.read.read_book.mapper.UserMapper;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.User;
import com.read.read_book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

//    @Autowired
//    UserMapper userMapper;

    @Autowired
    private UserService userService;

//    数据库连接测试
//    @RequestMapping("/user/all/")
////  可以具体请求方式 例如GetMapping("/user/all)
//    public List<User> getAll() {
//        return userMapper.selectList(null);//null为查询所有用户
//    }

//    @RequestMapping("/login/{uid}")
//    public List<String> getUserbyId(@PathVariable int uid) {
//        return userMapper.findallcomment(uid);
//    }

    // 前端请求测试 (已完成,已能够正常插入数据,插入语句uid为null,数据库中需将uid设为自增,前端能够接收到所有信息,但无法显示变量erro_message)
    @PostMapping("/api/user/account/register/")
    public Map<String, String> register(@RequestParam Map<String, String> map) {
//        System.out.println("接收到map" + map);
        String username = map.get("username");
        String password = map.get("password");
        String confirmedPassword = map.get("password_confirm");
//        System.out.println(username + " " + password + confirmedPassword);
        return userService.register(username, password, confirmedPassword);
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
