package com.read.read_book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.read.read_book.common.Result;
import com.read.read_book.dto.Checkcodedto;
import com.read.read_book.dto.Recoverpwddto;
import com.read.read_book.dto.Userdto;
import com.read.read_book.pojo.User;

public interface IUserService extends IService<User> {
    public User  getbyemail(String email);//用户查看个人信息

    public Result updatepwd(String password, String newpassword, String confirmedPassword, String email, String pwd);//修改密码

    public Result login(String email, String password);//登录

    public Result register(String username, String password, String confirmedPassword);

    public Result updateuser(User user);//修改用户信息

    public User checkadmin(String email);//验证用户是否是管理员

    public Result seleuser(int page, int size, Object text);//管理员查询用户，可以根据用户名和邮箱查询

    public int freezeuser(int userid);//冻结

    public int thaw(int userid);//解冻

    Result sendmail(String email);
    Result checkcode(Checkcodedto checkcodedto);

    Result recoverpwd(Recoverpwddto recoverpwddto);

    //注册
    Result registertest(String email);

    Result register3(String email, String password, String confirmedPassword);
//    int thaw(int userid);
}


