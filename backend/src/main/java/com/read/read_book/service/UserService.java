package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface UserService  {

    // 注册
    public Map<String, String> register(String username, String password, String confirmedPassword);

    //登录


    //修改信息


    //查询用户,后台功能


    //修改用户状态(冻结/解冻),后台功能

}
