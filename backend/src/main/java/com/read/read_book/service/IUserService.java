package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.read.read_book.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService<User> {
    public User  getbyemail(String email);
    public Map<String, String> updatepwd(String password, String newpassword, String confirmedPassword);

    public Map<String, String> login(String email, String password, HttpServletRequest request);

    public Map<String, String> updateuser(User user);//修改用户信息


    public int checkadmin(String email);//验证用户是否是管理员

    public List<User> seleuser(User user);//管理员查询用户，可以根据用户名和邮箱查询

    public int freezeuser(int userid);//冻结

    public int thaw(int userid);//解冻


//    int thaw(int userid);
}


