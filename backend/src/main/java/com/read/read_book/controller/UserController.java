package com.read.read_book.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class UserController {

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
