package com.read.read_book.controller;


import com.read.read_book.dto.logindto;
import com.read.read_book.pojo.User;
import com.read.read_book.dto.Userpass;
import com.read.read_book.service.IUserService;
import com.read.read_book.session.GetSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class IUserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/get")//查看个人信息,根据邮箱查看个人信息，只能查询到username, gender ,age ,email ,introduction, state
    public User getbyemail(){
        String email="2971387095@qq.com";//登录之后用session存储好登录人的邮箱和密码，在这里使用
        return iUserService.getbyemail(email);
    }

    @GetMapping()//查看全部信息
    public List<User> getbyid(){
        return iUserService.list();
    }

//    @PostMapping("/up")
//    public Map<String, String> updatepwd(@RequestParam Map<String, String> map){
//        System.out.println("map"+map);
//        String password=map.get("password");
//        String newpassword=map.get("newpassword");
//        String confirmedPassword=map.get("confirmedPassword");
//        System.out.println(password+newpassword+confirmedPassword);
//        return iUserService.updatepwd("13","567","567");
//    }
    @PostMapping("/up")//修改密码
    public Map<String, String> updatepwd(@RequestBody Userpass userpass){
        String password=userpass.getPassword();
        String newpassword=userpass.getNewpassword();
        String confirmedPassword=userpass.getConfirmedPassword();
        System.out.println(password+newpassword+confirmedPassword);
        System.out.println(userpass);
        return iUserService.updatepwd(password,newpassword,confirmedPassword);
    }

//    @Autowired
//    HttpServletRequest request;
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody logindto logindto,HttpServletRequest request) {
    String email=logindto.getEmail();
    String pwd=logindto.getPwd();
    System.out.println(email+pwd);
//    String u = (String) request.getSession().getAttribute("email");
//        System.out.println(u);

//        request.getSession().setAttribute("email",email);
//        request.getSession().setAttribute("pwd",pwd);
        return iUserService.login(email,pwd,request);
    }

    @PostMapping("/upuser")
    public Map<String, String> updateuser(@RequestBody User user) {
        return iUserService.updateuser(user);
    }
}
