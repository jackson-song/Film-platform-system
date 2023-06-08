package com.read.read_book.controller;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.dto.logindto;
import com.read.read_book.pojo.User;
import com.read.read_book.dto.Userpass;
import com.read.read_book.service.IUserService;
import com.read.read_book.session.GetSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class IUserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/get")//查看个人信息,根据邮箱查看个人信息，只能查询到username, gender ,age ,email ,introduction, state
    public User getbyemail(){
        String email="2971387095@qq.com";//登录之后用session存储好登录人的邮箱和密码，在这里使用
        return iUserService.getbyemail(email);
    }

    @GetMapping()//查看全部信息,查询所有用户
    public List<User> getbyid(){
        return userMapper.getall();
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
    @PostMapping("/login")//登录
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

    @PostMapping("/upuser")//修改个人信息

    public Map<String, String> updateuser(@RequestBody User user) {
            getbyemail();
            return iUserService.updateuser(user);
    }


    @GetMapping("/check")//验证用户是否是管理员
    public Map<String, String> checkadmin(){
        Map<String, String> map = new HashMap<>();
        String email="2971387095@qq.com";
        int m= iUserService.checkadmin(email);
        if(m==1){
            map.put("message","success");
            return map;
        }else{
            map.put("error","failed");
            return map;
        }
    }

    @PostMapping("/selectuser")//管理员查询用户
    public List<User> selectuser(@RequestBody User user){
        System.out.println(user);
        return iUserService.seleuser(user);
    }
    @PostMapping("/{userid}")//冻结
    public Map<String, String> freezeuser(@PathVariable int userid) {
        Map<String, String> map = new HashMap<>();
       int i= iUserService.freezeuser(userid);
        if(i!=0){
            map.put("message","success");
            return map;
        }else {
            map.put("message","failed");
            return map;
        }
    }

    @PostMapping("/thaw/{userid}")//冻结
    public Map<String, String> thaw(@PathVariable int userid) {
        Map<String, String> map = new HashMap<>();
        int i= iUserService.thaw(userid);
        if(i!=0){
            map.put("message","success");
            return map;
        }else {
            map.put("message","failed");
            return map;
        }
    }
}


