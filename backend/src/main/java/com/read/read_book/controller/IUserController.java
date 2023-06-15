package com.read.read_book.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.common.Result;
import com.read.read_book.dto.logindto;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.User;
import com.read.read_book.dto.Userpass;
import com.read.read_book.pojo.register;
import com.read.read_book.service.IUserService;
import com.read.read_book.session.GetSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.Calendar;
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

    @GetMapping("/get")
    //前端需要传用户邮箱email,查看个人信息,根据邮箱查看个人信息，
    // 用户只能查询到username, gender ,age ,email ,introduction, state(是否冻结)
    public Result getbyemail(String email){
        Result result=new Result();
        User user1=iUserService.getbyemail(email);
        System.out.println(user1);
        return result.success(user1);
//        return iUserService.getbyemail(email)
    }

    @GetMapping()
    //查看全部信息分页,查询所有用户,前端传入当前页面page和页面大小size
    public Result getbyid(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size){
        int pagenum=(page-1)*size;
        System.out.println(pagenum);
        System.out.println(page);
        Page<User> page1=new Page<>(page,size);
//        IPage page2 = new Page(page,size);
        System.out.println(page1);
        Result result=new Result();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dateFormat.format(calendar.getTime()));
        return result.success(userMapper.selectPage(page1,null));
    }

    @PostMapping("/up")
    //修改密码,前端需要传用户邮箱email,密码pwd,用户输入的原密码password,新密码newpassword,再次确认密码confirmedPassword
    public Result updatepwd(@RequestBody Userpass userpass){
        String password=userpass.getPassword();
        String newpassword=userpass.getNewpassword();
        String confirmedPassword=userpass.getConfirmedPassword();
        String email=userpass.getEmail();
        String pwd=userpass.getPwd();
        System.out.println(password+newpassword+confirmedPassword);
        System.out.println(userpass);
        return iUserService.updatepwd(password,newpassword,confirmedPassword,email,pwd);
    }

    @PostMapping("/login")
    //登录,前端传的数据为email和pwd,后端会将此用户的信息传回前端，请注意接受保存
    public Result login(@RequestBody logindto logindto) {
    String email=logindto.getEmail();
    String pwd=logindto.getPwd();
    System.out.println(email+pwd);
        return iUserService.login(email,pwd);
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody register register) {
//        System.out.println("接收到map" + map);
        String email = register.getEmail();
        String password = register.getPwd();
        String confirmedPassword = register.getConfirmedPassword();
        Result result=new Result();
//        System.out.println(username + " " + password + confirmedPassword);
        return result.success(iUserService.register(email, password, confirmedPassword));
    }

    @PostMapping("/upuser")
    //修改个人信息,修改什么数据传什么数据,必须传的数据为用户的email,这是修改的条件
    public Result updateuser(@RequestBody User user) {
            return iUserService.updateuser(user);
    }

    @GetMapping("/check")
    //前端传入email验证用户是否是管理员,使用email对权限进行一个判定
    public Result checkadmin(String email){
        Map<String, String> map = new HashMap<>();
        Result result=new Result();
//        email="2971387095@qq.com";
        User user= iUserService.checkadmin(email);
        if(user!=null){
            map.put("message","success");
            return result.success(user);
        }else{
            map.put("error","failed");
            return result.error("400","failed");
        }
    }

    @GetMapping("/selectuser")
    //管理员查询用户,管理员根据username和email来进行查询用户,前端可以传username和email
    public Result selectuser( @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam Object text){
        System.out.println(page);
        Object text1=text;
        System.out.println(size);
        System.out.println(text);
        return iUserService.seleuser(page,size,text);
    }

    @PostMapping("/{userid}")//冻结
    //冻结,前端传入的数据为路径数据,userid为用户id,传入/users/1就是对userid对1的用户进行冻结
    public Result freezeuser(@PathVariable int userid) {
        Map<String, String> map = new HashMap<>();
        Result result=new Result();
       int i= iUserService.freezeuser(userid);
        if(i!=0){
            return  result.success();
//            map.put("message","success");
//            return map;
        }else {
            map.put("message","failed");
           return result.error("400","failed");
//            return map;
        }
    }

    @PostMapping("/thaw/{userid}")
    //解冻,前端传入的数据为路径数据,userid为用户id,传入users/thaw/1就是对userid对1的用户进行解冻
    public Result thaw(@PathVariable int userid) {
        Map<String, String> map = new HashMap<>();
        int i= iUserService.thaw(userid);
        Result result=new Result();
        if(i!=0){
            map.put("message","success");
           return result.success();
//            return map;
        }else {
            map.put("message","failed");
            return result.error("400","failed");
        }
    }
}


