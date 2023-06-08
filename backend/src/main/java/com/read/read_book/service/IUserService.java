package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.read.read_book.pojo.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface IUserService extends IService<User> {
    public User  getbyemail(String email);
    public Map<String, String> updatepwd(String password, String newpassword, String confirmedPassword);

    public Map<String, String> login(String email, String password, HttpServletRequest request);

    public Map<String, String> updateuser(User user);





}


