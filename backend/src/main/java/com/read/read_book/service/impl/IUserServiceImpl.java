package com.read.read_book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.common.Result;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.User;
import com.read.read_book.service.IUserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
//查看个人信息
@Autowired
    UserMapper userMapper;

    @Override//查看个人信息
    public User getbyemail(String email) {

        return userMapper.getbyemail(email);
    }

    @Override
    public Result updatepwd(String password, String newpassword, String confirmedPassword, String email, String pwd) {
//        HttpSession session=request.getSession();
//        String sessionValue=(String)session.getAttribute("pwd");//登录后使用session存储数据
        Map<String, String> map = new HashMap<>();
        Result result=new Result();
//        String pwd = "123";
//        String email = "2971387095@qq.com";
        if (StringUtils.isBlank(password)||StringUtils.isBlank(newpassword)||StringUtils.isBlank(confirmedPassword)) {
//            map.put("error_message", "密码或新密码或再次确认密码" +
//                    "输入为空");
//            System.out.println(map);
            return result.error("400","密码或新密码或再次确认密码输入为空");
//            return map;
        } else {
            if (pwd.equals(password)) {
                if (newpassword.equals(password)) {
                   return result.error("400","新密码与原密码相同");
//                    map.put("error_message", "新密码与原密码相同");
//                    System.out.println(map);
//                    return map;
                }
                if (newpassword.equals(confirmedPassword)) {
                    if(userMapper.updatepwd(confirmedPassword, email)!=0){
                    return result.success();
                    }else {return result.error("500","系统错误");}
//                    map.put("error_message", "success");
//                    return map;
                } else {
                   return result.error("400","两次输入的新密码不一致");
//                    map.put("error_message", "两次输入的新密码不一致");
//                    System.out.println(map);
//                    return map;
                }
            } else if(!pwd.equals(password)) {
               return result.error("400","原密码输入有误");
//                map.put("error_message", "原密码输入有误");
//                System.out.println(map);
//                return map;
            }
        }
        return result.error("500","参数错误");
    }

//    @Autowired
//    ServletWebServerFactory servletWebServerFactory;
//    HttpServletRequest request;
    @Override
    public Result login(String email, String pwd) {
        Map<String, Object> map = new HashMap<>();
        Result result=new Result();
        if (StringUtils.isBlank(email)) {
            map.put("error_message", "邮箱不能为空");
            System.out.println(map);
            return result.error("400","邮箱为空");
//            return map;
        }
        else if (StringUtils.isBlank(pwd)) {
            map.put("error_message", "密码不能为空");
            return  result.error("400","密码为空");
//            System.out.println(map);
//            return map;
        }else{
//         userMapper.check(email,pwd);
         if(userMapper.check(email,pwd)!=null){
             int state=userMapper.check(email, pwd).getState();
             if(state!=0){
             map.put("User",userMapper.check(email,pwd));
             map.put("message", "success");
             return result.success();}
//             return map;}
             else {map.put("error_message", "你的账号已经被冻结");
                 return result.error("400","你的账号已经被冻结");
             }
         }else {
             map.put("error_message", "输入信息有误");
             System.out.println(map);
             return result.error("400","输入信息有误");
//             return map;
         }
        }
    }

    @Override//修改个人信息
    public Result updateuser(User user) {
        Map<String, String> map = new HashMap<>();
        int i=userMapper.updateuser(user);
        Result result=new Result();
        if(i!=0){
            map.put("error_message", "success");
            System.out.println(map);
            return result.success();
        }else{
            map.put("error_message", "failed");
            System.out.println(map);
            return result.error("400","failed");
        }
    }

    @Override//确认是否是管理员
    public User checkadmin(String email) {
        Result result=new Result();
        User user=userMapper.checkadmin(email);
        return user;
    }

    @Override
    public Result seleuser(int page, int size, Object text) {
//        int pagenum=(page-1)*size;
        Page<User> page1=new Page<>(page,size);
        Result result=new Result();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username",text).or().like("email", text);
        return result.success(userMapper.selectPage(page1,wrapper));
//        return userMapper.selectPage(page1,wrapper);
    }

    @Override
    public int freezeuser(int userid) {
        return userMapper.freezeuser(0,userid);//冻结
    }

    @Override
    public int thaw(int userid) {
        return userMapper.freezeuser(1,userid);//解冻
    }

}

