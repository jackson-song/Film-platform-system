package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {
    public User  getbyemail(String email);//用户查看个人信息
    public Map<String, String> updatepwd(String password, String newpassword, String confirmedPassword,String email,String pwd);//修改密码

    public Map<String, Object> login(String email, String password);//登录

    public Map<String, String> updateuser(User user);//修改用户信息


    public User checkadmin(String email);//验证用户是否是管理员

    public Page<User> seleuser(int page, int size, Object text);//管理员查询用户，可以根据用户名和邮箱查询

    public int freezeuser(int userid);//冻结

    public int thaw(int userid);//解冻


//    int thaw(int userid);
}


